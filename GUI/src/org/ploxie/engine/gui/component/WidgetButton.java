package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.event.RenderEvent;
import org.ploxie.engine.gui.event.ResizeEvent;
import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Color;

public class WidgetButton extends ClickableWidget implements Renderable{

	private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0,0,0,0.1f);
	
	protected WidgetPanel background = new WidgetPanel();
	
	public WidgetButton() {
		setDynamic(true);
		
		setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
		setBorderThickness(1);
		
		background.setSize(1,1);
		addChild(background);
	}
			
	@Override
	public void render(Shader shader) {	
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
