package org.ploxie.engine.gui2.component;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.ploxie.opengl.buffer.VBO;
import org.ploxie.opengl.buffer.VBO.BufferType;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.opengl.texture.TextureLoader;
import org.ploxie.opengl.utilities.BufferUtils;
import org.ploxie.utils.Color;
import org.ploxie.utils.VertexStream;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;
import org.ploxie.utils.texture.Bitmap;
import org.ploxie.utils.texture.Texture;

public class WidgetText extends WidgetBase {

	private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

	private VBO vbo;
	private Bitmap bitmap;
	private Texture texture;

	protected String text = "";
	protected Font font;
	
	protected int textWidth;
	protected int textHeight;
	
	public WidgetText() {
		this.font = DEFAULT_FONT;
		this.bitmap = Bitmap.create(font, true);
		this.texture = TextureLoader.loadTexture(bitmap.getImage());
	}

	@Override
	public void initialize() {
		super.initialize();
	}

	private void create(final CharSequence text, boolean drawStatic) {
		VertexStream vertexStream = new VertexStream(text.length() * 4 * 3);
		VertexStream uvStream = new VertexStream(text.length() * 4 * 2);
		List<Integer> indices = new ArrayList<Integer>();

		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		float fontPixelHeight = bitmap.getFontMetrics().getHeight();
		float fontHeight = fontPixelHeight / (float) screenDimensions.y;

		float currentPixelWidth = 0;
		float currentWidth = 0;
		float currentX = 0;
		float currentY = 0;

		int width = 0;
		int height = (int) fontPixelHeight;

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			currentPixelWidth = bitmap.getFontMetrics().stringWidth(String.valueOf(character));
			currentWidth = currentPixelWidth / (float) screenDimensions.x;
			addQuad(vertexStream, currentX, currentY, currentWidth, fontHeight);
			addQuadIndices(i, indices);
			addUV(uvStream, character, currentPixelWidth, fontPixelHeight);
			currentX += currentWidth;
			width += currentPixelWidth;
		}

		textWidth = width;
		textHeight = height;
		
		//lockedWidth = true;
		//lockedHeight = true;

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
		stream.append(new Vector2f(x, y));
		stream.append(new Vector2f(x, y + height));
		stream.append(new Vector2f(x + width, y));
		stream.append(new Vector2f(x + width, y + height));

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

	@Override
	public void render(Shader shader) {
		super.render(shader);

		texture.bind();
		texture.bilinearFilter();

		shader.setUniform("transformMatrix", getWorldMatrix());
		shader.setUniform("backgroundColor", Color.NO_COLOR);
		shader.setUniform("scale", getWorldMatrix().getScale().xy());
		shader.setUniform("texture", 0);

		vbo.draw();

		texture.unbind();

	}

	public void setText(String text) {
		create(text, true);
	}
	
	public int getTextWidth() {
		return textWidth;
	}

	public int getTextHeight() {
		return textHeight;
	}

}
