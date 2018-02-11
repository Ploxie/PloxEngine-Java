package org.ploxie.engine.gui2.component;

import org.ploxie.opengl.buffer.VBO;
import org.ploxie.opengl.buffer.primitive.Quad;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetPanel extends WidgetBase{

	protected VBO vbo;	
	
	protected Color backgroundColor = new Color(0,0,0,0);
	protected Color borderColor = new Color(0,0,0,0);
	
	protected int borderThickness = 1;
	
	@Override
	public void initialize() {	
		vbo = new Quad();
	}
	
	@Override
	public void render(Shader shader) {
		
		shader.setUniform("transformMatrix", getWorldMatrix());
		shader.setUniform("backgroundColor", getBackgroundColor());
		shader.setUniform("borderColor", getBorderColor());
		shader.setUniform("borderThickness", borderThickness);
		shader.setUniform("scale", getWorldMatrix().getScale().xy());
		vbo.draw();
		super.render(shader);		
	}
	
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBorderColor(Color color) {
		borderColor = color;
	}
	
	public Color getBorderColor() {
		return borderColor;
	}
}
