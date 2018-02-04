package org.ploxie.engine.font;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;

import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.BufferType;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.engine.utils.VertexStream;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector3f;

public class TextMesh implements Renderable{

	private VBO vbo;
	private final Bitmap bitmap;
	private Vector2f location;
	private TextAlignment alignment;
	private TextShader shader;
	private Matrix4f transform;

	public TextMesh(final Bitmap bitmap, final CharSequence text, Vector2f location, final TextAlignment alignment,
			final TextShader shader) {
		this.bitmap = bitmap;
		this.location = location;
		this.alignment = alignment;
		this.shader = shader;
		transform = new Matrix4f();
	//	setPosition(location);

		create(text, true);
	}
	
	public TextMesh(final Bitmap bitmap, Vector2f location, final TextAlignment alignment, final TextShader shader) {
		this.bitmap = bitmap;
		this.location = location;
		this.alignment = alignment;
		this.shader = shader;
		transform = new Matrix4f();
		//setPosition(location);
		
		create("", false);
	}
	

	private void create(final CharSequence text, boolean drawStatic) {
		VertexStream vertexStream = new VertexStream(text.length() * 4 * 3);
		VertexStream uvStream = new VertexStream(text.length() * 4 * 2);
		List<Integer> indices = new ArrayList<Integer>();

		float fontHeight = bitmap.getFontMetrics().getHeight();

		float currentWidth = 0;
		float currentX = 0;
		float currentY = 0;

		for (int i = 0; i < text.length(); i++) {
			char character = text.charAt(i);
			currentWidth = bitmap.getFontMetrics().stringWidth(String.valueOf(character));
			addQuad(vertexStream, currentX, currentY, currentWidth, fontHeight);
			addQuadIndices(i, indices);
			addUV(uvStream, character, currentWidth, fontHeight);
			currentX += currentWidth;
		}

		int usage = drawStatic ? GL_STATIC_DRAW : GL_DYNAMIC_DRAW;
		
		vbo = new VBO();
		vbo.setIndexBufferData(indices.stream().mapToInt(i -> i).toArray(), usage);
		vbo.setBufferData(BufferType.VERTEX, BufferUtils.createFlippedBuffer(vertexStream.getBuffer()), 3, usage);
		vbo.setBufferData(BufferType.UV, BufferUtils.createFlippedBuffer(uvStream.getBuffer()), 2, usage);
	}

	private VertexStream addQuad(VertexStream stream, final float x, final float y, final float width,
			final float height) {
		stream.append(new Vector3f(x, y, 0));
		stream.append(new Vector3f(x, y + height, 0));
		stream.append(new Vector3f(x + width, y, 0));
		stream.append(new Vector3f(x + width, y + height, 0));
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
	
	public void render(final CharSequence text) {
		create(text, false);
		render();
	}

	@Override
	public void render() {
				
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		shader.bind();
		//shader.setUniforms(camera, transform, bitmap.getBitmap());
		
		vbo.draw();
	}


}
