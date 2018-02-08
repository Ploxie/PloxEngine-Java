package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.shader.Shader;
import org.ploxie.utils.Color;

public class WidgetButton extends ClickableWidget implements Renderable{

	protected WidgetBackground background = new WidgetBackground(this);
	
	@Override
	public void render(Shader shader) {
		background.render(shader);
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

}
