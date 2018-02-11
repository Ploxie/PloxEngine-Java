package org.ploxie.engine.gui.component;

import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Color;

public class WidgetPanel extends WidgetBase implements Renderable{

	protected WidgetBackground background = new WidgetBackground(this);

	@Override
	public void render(Shader shader) {
		background.render(shader);
		
		for (Widget child : getChildren()) {
			if (child instanceof Renderable) {
				((Renderable) child).render(shader);
			}
		}
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
	public void setSize(float x, float y) {	
		super.setSize(x, y);
		setNeedsToUpdate(true);
	}
	
	@Override
	public void setPosition(float x, float y) {	
		super.setPosition(x, y);	
		setNeedsToUpdate(true);
	}

	@Override
	public void setNeedsToUpdate(boolean flag) {
		background.needsToUpdate = flag;
		for(Widget child : getChildren()) {
			if(child instanceof Renderable) {
				((Renderable)child).setNeedsToUpdate(flag);
			}
		}
	}

	@Override
	public boolean needsToUpdate() {
		return background.needsToUpdate;
	}
	

	
	
	
}
