package org.ploxie.engine.gui2.component;

public class WidgetLabel extends WidgetPanel{

	private WidgetText text;
	
	public WidgetLabel() {
		this.text = new WidgetText();
		this.text.setText("");
	}
	
	public WidgetLabel(String text) {
		this.text = new WidgetText();
		this.text.setText(text);
	}
	
	@Override
	public void initialize() {	
		super.initialize();
		//setLockedScale(true);
		addChild(text);
	}
	
	public void setText(String text) {
		this.text.setText(text);
		setSize(this.text.getTextWidth(), this.text.getTextHeight());
	}
	
}
