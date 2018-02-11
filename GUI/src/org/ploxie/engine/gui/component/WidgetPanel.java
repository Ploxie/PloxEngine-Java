package org.ploxie.engine.gui.component;

import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.awt.image.BufferedImage;
import java.util.List;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.engine.gui.event.RenderEvent;
import org.ploxie.engine.gui.event.ResizeEvent;
import org.ploxie.engine.gui.event.WidgetEvent;
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

public class WidgetPanel extends WidgetBase implements  Renderable {

	protected VBO vbo = null;

	protected Vector2f borderThickness = new Vector2f(0, 0);
	protected Color backgroundColor = new Color(0, 0, 0, 0.0f);
	protected Color borderColor = new Color(0, 0, 0, 1);
	protected boolean needsToUpdate;
	private Texture2D texture;

	public WidgetPanel() {
		
		BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, 0);
		texture = TextureLoader.loadTexture(image);
	}
	
	@Override
	public void handleEvent(WidgetEvent event) {	
		if(event instanceof ResizeEvent) {
			createVBO();
		}
		if(event instanceof RenderEvent) {
			render(((RenderEvent) event).getShader());
		}
		
		
		
		super.handleEvent(event);		
	}
	
	protected void createVBO() {
		needsToUpdate = false;

		float left = getLeft();
		float right = getRight();
		float top = getTop();
		float bottom = getBottom();

		VertexStream vertexStream = new VertexStream(6 * 2);
		vertexStream.append(new Vector2f(left, top));
		vertexStream.append(new Vector2f(left, bottom));
		vertexStream.append(new Vector2f(right, top));
		vertexStream.append(new Vector2f(right, bottom));
		
		VertexStream uvStream = new VertexStream(6 * 2);
		uvStream.append(new Vector2f(0, 0));
		uvStream.append(new Vector2f(0, 1));
		uvStream.append(new Vector2f(1, 0));
		uvStream.append(new Vector2f(1, 1));
				

		int[] indices = new int[] { 0, 1, 2, 2, 1, 3 };

		int usage = isDynamic() ? GL_DYNAMIC_DRAW : GL_STATIC_DRAW;

		if (vbo == null) {
			vbo = new VBO();
		}
	
		vbo.setIndexBufferData(indices, usage);
		vbo.setBufferData(BufferType.VERTEX, BufferUtils.createFlippedBuffer(vertexStream.getBuffer()), 2, usage);
		vbo.setBufferData(BufferType.UV, BufferUtils.createFlippedBuffer(uvStream.getBuffer()), 2, usage);
	}

	@Override
	public void render(Shader shader) {
		if (vbo == null || needsToUpdate) {
			createVBO();
		}

		shader.setUniform("size", getSize());
		shader.setUniform("backgroundColor", backgroundColor);
		shader.setUniform("color", Color.NO_COLOR);
		shader.setUniform("borderColor", borderColor);
		shader.setUniform("borderThickness", borderThickness);
		
		texture.bind();
		shader.setUniform("texture", 0);
		vbo.draw();
		texture.unbind();
		
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
