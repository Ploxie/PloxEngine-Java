package org.ploxie.engine.gui.component;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.opengl.buffer.VBO;
import org.ploxie.opengl.buffer.VBO.BufferType;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.opengl.texture.Texture2D;
import org.ploxie.opengl.texture.TextureLoader;
import org.ploxie.opengl.utilities.BufferUtils;
import org.ploxie.utils.Color;
import org.ploxie.utils.VertexStream;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;
import org.ploxie.utils.texture.Bitmap;

public class WidgetLabel extends WidgetBase implements Renderable {

	private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 16);

	private VBO vbo;
	private String text;
	private Font font;
	private boolean antiAliased = true;
	private boolean needsToUpdate = true;

	private WidgetBackground background;
	private Color textColor;
	private Bitmap bitmap;
	private Texture2D bitmapTexture;

	private WidgetViewport viewport = new WidgetViewport() {
		@Override
		public Vector2f getSize() {
			return parent.getSize();
		}
		
		@Override
		public Vector2f getPivot() {
			
			return parent.getPivot();
		}
		
		@Override
		public Vector2f getPosition() {		
			return parent.getPosition();
		}
	};

	public WidgetLabel() {
		this.text = "";

		background = new WidgetBackground(this);
		textColor = new Color(1, 1, 1, 1);
		setBorderThickness(1);
		setBorderColor(Color.NO_COLOR);
		setBackgroundColor(Color.NO_COLOR);

		setFont(DEFAULT_FONT);

		addChild(viewport);
		addChild(background);
	}

	public WidgetLabel(String text) {
		this.text = text;

		background = new WidgetBackground(this);
		textColor = new Color(1, 1, 1, 1);
		setBorderThickness(1);
		setBorderColor(Color.NO_COLOR);
		setBackgroundColor(Color.NO_COLOR);
		setFont(DEFAULT_FONT);

		addChild(viewport);
		addChild(background);
	}

	@Override
	public void render(Shader shader) {
		if (needsToUpdate) {
			create(text, true);
			needsToUpdate = false;
		}

		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		background.render(shader);

		shader.setUniform("size", getSize());
		shader.setUniform("color", textColor);
		shader.setUniform("backgroundColor", Color.NO_COLOR);
		shader.setUniform("borderColor", Color.NO_COLOR);
		shader.setUniform("borderThickness", new Vector2f(0, 0));

		bitmapTexture.bind();
		bitmapTexture.bilinearFilter();

		shader.setUniform("texture", 0);
		viewport.enable();

		vbo.draw();

		viewport.disable();
		bitmapTexture.unbind();

	}

	private void create(final CharSequence text, boolean drawStatic) {
		VertexStream vertexStream = new VertexStream(text.length() * 4 * 3);
		VertexStream uvStream = new VertexStream(text.length() * 4 * 2);
		List<Integer> indices = new ArrayList<Integer>();

		Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
		float fontPixelHeight = bitmap.getFontMetrics().getHeight();
		float fontHeight = fontPixelHeight / (float) screenDimensions.y;

		float currentPixelWidth = 0;
		float currentWidth = 0;
		float currentX = 0;
		float currentY = 0;

		size.x = 0.0f;
		size.y = fontHeight;
		
		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			size.x += bitmap.getFontMetrics().stringWidth(String.valueOf(character)) / (float) screenDimensions.x;
		}

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			currentPixelWidth = bitmap.getFontMetrics().stringWidth(String.valueOf(character));
			currentWidth = currentPixelWidth / (float) screenDimensions.x;
			addQuad(vertexStream, currentX, currentY, currentWidth, fontHeight);
			addQuadIndices(i, indices);
			addUV(uvStream, character, currentPixelWidth, fontPixelHeight);
			currentX += currentWidth;
		}

		int usage = drawStatic ? GL_STATIC_DRAW : GL_DYNAMIC_DRAW;

		if (vbo == null) {
			vbo = new VBO();
		}
		vbo.setIndexBufferData(indices.stream().mapToInt(i -> i).toArray(), usage);
		vbo.setBufferData(BufferType.VERTEX, BufferUtils.createFlippedBuffer(vertexStream.getBuffer()), 2, usage);
		vbo.setBufferData(BufferType.UV, BufferUtils.createFlippedBuffer(uvStream.getBuffer()), 2, usage);
	}

	private VertexStream addQuad(VertexStream stream, final float x, final float y, final float width, final float height) {
		float top = getTop();
		float left = getLeft();
		stream.append(new Vector2f(x + left, y + top));
		stream.append(new Vector2f(x + left, y + top + height));
		stream.append(new Vector2f(x + left + width, y + top));
		stream.append(new Vector2f(x + left + width, y + top + height));

		return stream;
	}

	private List<Integer> addQuadIndices(int quadIndex, List<Integer> currentList) {
		quadIndex *= 4;
		currentList.add(quadIndex);
		currentList.add(quadIndex + 1);
		currentList.add(quadIndex + 2);

		currentList.add(quadIndex + 2);
		currentList.add(quadIndex + 1);
		currentList.add(quadIndex + 3);

		return currentList;
	}

	private VertexStream addUV(VertexStream stream, final char character, float width, float height) {
		Vector2f texCoords = bitmap.getCharacterTextureCoordinates(character);
		Vector2f texDimensions = bitmap.getCharacterTextureDimensions(new Vector2f(width, height));

		stream.append(new Vector2f(texCoords.x, texCoords.y));
		stream.append(new Vector2f(texCoords.x, texCoords.y + texDimensions.y));
		stream.append(new Vector2f(texCoords.x + texDimensions.x, texCoords.y));
		stream.append(new Vector2f(texCoords.x + texDimensions.x, texCoords.y + texDimensions.y));

		return stream;
	}

	public void setText(String text) {
		this.text = text;
		needsToUpdate = true;		
	}

	public String getText() {
		return text;
	}

	public void setFont(Font font) {
		this.font = font;
		bitmap = Bitmap.create(font, antiAliased);
		bitmapTexture = TextureLoader.loadTexture(bitmap.getImage());
	}

	public Font getFont() {
		return font;
	}

	@Override
	public void setBackgroundColor(Color color) {
		background.setBackgroundColor(color);
	}

	@Override
	public void setBorderColor(Color color) {
		background.setBorderColor(color);
	}

	@Override
	public void setBorderThickness(float thickness) {
		background.setBorderThickness(thickness);
	}

	@Override
	public void setBorderThickness(int pixels) {
		background.setBorderThickness(pixels);
	}

	@Override
	public Vector2f getSize() {
		return size;
	}
	
	public void setAntiAliased(boolean antiAliased) {
		this.antiAliased = antiAliased;
	}

	@Override
	public void setNeedsToUpdate(boolean flag) {
		needsToUpdate = flag;		
	}

	@Override
	public boolean needsToUpdate() {
		return needsToUpdate;
	}
	

}
