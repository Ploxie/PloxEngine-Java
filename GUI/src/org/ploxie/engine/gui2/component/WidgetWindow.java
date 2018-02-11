package org.ploxie.engine.gui2.component;

import org.ploxie.utils.Color;

public class WidgetWindow extends WidgetPanel{

	private WidgetPanel topBar = new WidgetPanel();
	
	@Override
	public void initialize() {	
		super.initialize();
		
		setBackgroundColor(Color.RED);
		
		topBar.setBackgroundColor(Color.BLUE);
		topBar.setPosition(10,10);
		topBar.setSize(10, 10);
		addChild(topBar);
	}
}
