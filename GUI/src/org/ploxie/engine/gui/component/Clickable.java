package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.math.vector.Vector2f;

public interface Clickable {

	public void addOnMousePressAction(MouseAction action);
	public void addOnMouseDownAction(MouseAction action);
	public void addOnMouseReleaseAction(MouseAction action);
	public void addOnMouseEnterAction(MouseAction action);
	public void addOnMouseExitAction(MouseAction action);
	
	public boolean onMousePress(Vector2f point);
	public boolean onMouseDown(Vector2f point);
	public boolean onMouseRelease(Vector2f point);
	public boolean onMouseEnter(Vector2f point);
	public boolean onMouseExit(Vector2f point);
	
	public boolean isInside(Vector2f point);
	
}
