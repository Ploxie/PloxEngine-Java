package org.ploxie.engine.gui.event;

import org.ploxie.utils.math.vector.Vector2f;

public class ClickEvent extends WidgetEvent{

	private Vector2f point;
	private boolean isPressed;
	
	public ClickEvent (float x, float y, boolean isPressed) {
		point = new Vector2f(x,y);
		this.isPressed = isPressed;
	}
	
	public ClickEvent(Vector2f point, boolean isPressed) {
		this.point = point;
		this.isPressed = isPressed;
	}
	
	public boolean isPressed() {
		return isPressed;
	}
	
	public Vector2f getPoint() {
		return point;
	}
	
}
