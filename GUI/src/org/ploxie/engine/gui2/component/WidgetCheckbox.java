package org.ploxie.engine.gui2.component;

import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Color;

public class WidgetCheckbox extends WidgetViewport implements IBackground{

	protected WidgetButton button = new WidgetButton();
	protected WidgetPanel panel = new WidgetPanel();
	protected WidgetLabel label = new WidgetLabel("Text");
		
	private int spacing = 0;
	
	@Override
	public void initialize() {	
		super.initialize();
		button.setBackgroundColor(Color.RED);
		button.setSize(15, 15);	
		//button.setLockedScale(true);
		
		//label.setBackgroundColor(Color.BLUE);
		//panel.setBackgroundColor(Color.BLUE);
		addChild(panel);
		addChild(button);
		addChild(label);
		
		label.setPosition(30, 0);
		
		//setHeight(15);
		//setWidth(500000);
	}
	
	@Override
	public void setBackgroundColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getBackgroundColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBorderColor(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getBorderColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBorderThickness(int thickness) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBorderThickness() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
