package org.ploxie.engine.gui.component;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.font.Bitmap;
import org.ploxie.engine.font.Texture2D;
import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.engine.gui.shader.Shader;
import org.ploxie.engine.gui.utils.BufferType;
import org.ploxie.engine.gui.utils.BufferUtils;
import org.ploxie.engine.gui.utils.VBO;
import org.ploxie.utils.Color;
import org.ploxie.utils.VertexStream;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetLabel extends WidgetBase implements Renderable {

	private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 16);

	private VBO vbo;
	private String text;
	private Font font;
	private boolean antiAliased;
	private boolean update = true;

	private Color textColor;
	private Color backgroundColor;
	private Color borderColor;

	private Vector2f borderThickness;

	private Bitmap bitmap;

	public WidgetLabel() {
		this.text = "";

		textColor = new Color(1, 1, 1, 1);
		backgroundColor = new Color(0, 0, 0, 0);
		borderColor = new Color(0, 0, 0, 0);
		borderThickness = new Vector2f(0, 0);
		setFont(DEFAULT_FONT);
	}

	public WidgetLabel(String text) {
		this.text = text;

		textColor = new Color(1, 1, 1, 1);
		backgroundColor = new Color(0, 0, 0, 0);
		borderColor = new Color(0, 0, 0, 0);
		borderThickness = new Vector2f(0, 0);
		setFont(DEFAULT_FONT);
	}

	@Override
	public void render(Shader shader) {
		if (update) {
			create(text, true);
			update = false;
		}

		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		shader.setUniform("size", getSize());
		shader.setUniform("color", textColor);
		shader.setUniform("backgroundColor", backgroundColor);
		shader.setUniform("borderColor", borderColor);
		shader.setUniform("borderThickness", borderThickness);

		glActiveTexture(GL_TEXTURE0);
		Texture2D texture = bitmap.getTexture2D();
		texture.bind();
		texture.bilinearFilter();
		shader.setUniform("texture", 0);

		
		vbo.draw();

		texture.unbind();
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

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			currentPixelWidth = bitmap.getFontMetrics().stringWidth(String.valueOf(character));
			currentWidth = currentPixelWidth / (float) screenDimensions.x;
			if (currentPixelWidth >= getSize().x) {
				break;
			}
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

	private VertexStream addQuad(VertexStream stream, final float x, final float y, final float width,
			final float height) {
		Vector2f position = getPosition();
		stream.append(position.clone().add(new Vector2f(x, y)));
		stream.append(position.clone().add(new Vector2f(x, y + height)));
		stream.append(position.clone().add(new Vector2f(x + width, y)));
		stream.append(position.clone().add(new Vector2f(x + width, y + height)));
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
		update = true;
	}

	public String getText() {
		return text;
	}

	public void setFont(Font font) {
		this.font = font;
		bitmap = Bitmap.create(font, antiAliased);
	}

	public Font getFont() {
		return font;
	}

	@Override
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	@Override
	public void setBorderColor(Color color) {
		borderColor = color;
	}

	@Override
	public void setBorderThickness(float thickness) {
		borderThickness = new Vector2f(thickness, thickness);
	}

	@Override
	public void setBorderThickness(int pixels) {
		Vector2i dim = WidgetManager.getInstance().getViewport().getDimensions();
		borderThickness = new Vector2f((float) pixels / (float) dim.x, (float) pixels / (float) dim.y);
	}

}
