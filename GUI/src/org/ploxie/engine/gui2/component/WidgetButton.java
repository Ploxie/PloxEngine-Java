package org.ploxie.engine.gui2.component;

import org.ploxie.utils.Color;

public class WidgetButton extends ButtonBase implements IButton, IBackground{

	private WidgetPanel background = new WidgetPanel();
	
	@Override
	public void initialize() {	
		super.initialize();
		
		
		addChild(background);
	}
	@Override
	public void setBackgroundColor(Color color) {
		background.setBackgroundColor(color);
	}
	@Override
	public Color getBackgroundColor() {
		return background.getBackgroundColor();
	}
	@Override
	public void setBorderColor(Color color) {
		background.setBorderColor(color);
	}
	@Override
	public Color getBorderColor() {
		return background.getBorderColor();
	}
	@Override
	public void setBorderThickness(int thickness) {
		background.setBorderThickness(thickness);
	}
	@Override
	public int getBorderThickness() {
		return background.getBorderThickness();
	}
}
