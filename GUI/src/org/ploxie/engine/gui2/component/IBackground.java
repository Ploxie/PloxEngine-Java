package org.ploxie.engine.gui2.component;

import org.ploxie.utils.Color;

public interface IBackground {

	public void setBackgroundColor(Color color);
	
	public Color getBackgroundColor();
	
	public void setBorderColor(Color color);
	
	public Color getBorderColor();
	
	public void setBorderThickness(int thickness);
	
	public int getBorderThickness();
	
}
