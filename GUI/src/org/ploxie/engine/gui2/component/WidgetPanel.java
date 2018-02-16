package org.ploxie.engine.gui2.component;

import java.awt.image.BufferedImage;

import org.ploxie.opengl.buffer.VBO;
import org.ploxie.opengl.buffer.primitive.Quad;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.opengl.texture.TextureLoader;
import org.ploxie.utils.Color;
import org.ploxie.utils.texture.Texture;

public class WidgetPanel extends WidgetBase implements IBackground {

	protected VBO vbo;

	protected Color backgroundColor = new Color(0, 0, 0, 0);
	protected Color borderColor = new Color(0, 0, 0, 0);

	protected int borderThickness = 0;
	protected Texture texture;

	@Override
	public void initialize() {
		vbo = new Quad();

		if (texture == null) {
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, 0);
			texture = TextureLoader.loadTexture(image);
		}
	}

	@Override
	public void render(Shader shader) {

		shader.setUniform("transformMatrix", getWorldMatrix());
		shader.setUniform("backgroundColor", getBackgroundColor());
		shader.setUniform("borderColor", getBorderColor());
		shader.setUniform("borderThickness", borderThickness);
		shader.setUniform("scale", getWorldMatrix().getScale().xy());

		texture.bind();

		vbo.draw();

		texture.unbind();

		super.render(shader);

	}

	@Override
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public void setBorderColor(Color color) {
		borderColor = color;
	}

	@Override
	public Color getBorderColor() {
		return borderColor;
	}

	@Override
	public void setBorderThickness(int thickness) {
		borderThickness = thickness;
	}

	@Override
	public int getBorderThickness() {
		return borderThickness;
	}
}
