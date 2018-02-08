package org.ploxie.engine.gui.event;

import org.ploxie.utils.math.vector.Vector2f;

public class MouseDownEvent extends WidgetEvent{

	private Vector2f point;
	
	public MouseDownEvent (float x, float y) {
		point = new Vector2f(x,y);
	}
	
	public MouseDownEvent(Vector2f point) {
		this.point = point;
	}
		
	public Vector2f getPoint() {
		return point;
	}
	
}
