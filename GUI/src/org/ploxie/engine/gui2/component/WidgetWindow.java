package org.ploxie.engine.gui2.component;

import org.ploxie.utils.Color;

public class WidgetWindow extends ResizablePanel{
	
	@Override
	public void initialize() {	
		super.initialize();
		
		setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, 0.8f));
		setBorderColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		
		WidgetPanel topBar = new WidgetPanel();
		topBar.setBackgroundColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
		topBar.setBorderThickness(1);
		topBar.setPosition(0.0f, 0.0f);
		topBar.setScale(1,0);
		topBar.setHeight(25);				
		//addChild(topBar);
		
	}
}
