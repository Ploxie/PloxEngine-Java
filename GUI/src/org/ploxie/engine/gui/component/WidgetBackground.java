package org.ploxie.engine.gui.component;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.awt.image.BufferedImage;
import java.util.List;

import org.ploxie.engine.gui.WidgetManager;
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

public class WidgetBackground implements Widget, Renderable {

	private Widget parent;
	protected VBO vbo = null;

	protected Vector2f borderThickness = new Vector2f(0, 0);
	protected Color backgroundColor = new Color(0, 0, 0, 0.1f);
	protected Color borderColor = new Color(0, 0, 0, 1);
	protected boolean needsToUpdate;
	private Texture2D texture;

	public WidgetBackground(Widget parent) {
		this.parent = parent;

		setBorderThickness(1);
		
		BufferedImage image = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, 0);
		texture = TextureLoader.loadTexture(image);
	}

	@Override
	public void setSize(float x, float y) {
		parent.setSize(x, y);
		needsToUpdate = true;
	}

	@Override
	public Vector2f getSize() {
		return parent.getSize();
	}

	@Override
	public void setAbsoluteSize(int x, int y) {
		parent.setAbsoluteSize(x, y);
		needsToUpdate = true;
	}

	@Override
	public Vector2i getAbsoluteSize() {
		return parent.getAbsoluteSize();
	}

	@Override
	public void setPosition(float x, float y) {
		parent.setPosition(x, y);
		needsToUpdate = true;
	}

	@Override
	public Vector2f getPosition() {
		return parent.getPosition();
	}

	@Override
	public void setAbsolutePosition(int x, int y) {
		parent.setAbsolutePosition(x, y);
	}

	@Override
	public Vector2i getAbsolutePosition() {
		return parent.getAbsolutePosition();
	}

	@Override
	public void setAnchorPoint(float x, float y) {
		parent.setAnchorPoint(x,y);
	}

	@Override
	public Vector2f getAnchorPoint() {
		return parent.getAnchorPoint();
	}
	
	@Override
	public void setDynamic(boolean flag) {
		parent.setDynamic(flag);
	}

	@Override
	public boolean isDynamic() {
		return parent.isDynamic();
	}

	@Override
	public void setPivot(float x, float y) {
		parent.setPivot(x, y);
	}

	@Override
	public Vector2f getPivot() {

		return parent.getPivot();
	}

	@Override
	public void setParent(Widget parent) {
		this.parent = parent;
	}

	@Override
	public Widget getParent() {

		return parent;
	}

	@Override
	public List<Widget> getChildren() {

		return null;
	}

	@Override
	public float getTop() {
		return parent.getTop();
	}

	@Override
	public float getBottom() {

		return parent.getBottom();
	}

	@Override
	public float getLeft() {

		return parent.getLeft();
	}

	@Override
	public float getRight() {

		return parent.getRight();
	}

	@Override
	public boolean isInside(Vector2f point) {
		return parent.isInside(point);
	}

	@Override
	public Vector2f getRelativePointOn(Vector2f point) {	
		return parent.getRelativePointOn(point);
	}
	
	@Override
	public void handleEvent(WidgetEvent event) {
		
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
		if (vbo == null || (isDynamic() && needsToUpdate)) {
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

	@Override
	public void addChild(Widget child) {
		throw new RuntimeException("Not implemented");
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
