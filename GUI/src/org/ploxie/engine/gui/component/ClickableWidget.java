package org.ploxie.engine.gui.component;

import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.gui.event.ClickEvent;
import org.ploxie.engine.gui.event.MouseDownEvent;
import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.math.vector.Vector2f;

public class ClickableWidget extends WidgetBase implements Clickable {

	protected boolean mouseEntered = false;
	protected boolean mouseDown = false;
	protected List<MouseAction> onMousePressActions = new ArrayList<MouseAction>();
	protected List<MouseAction> onMouseDownActions = new ArrayList<MouseAction>();
	protected List<MouseAction> onMouseReleaseActions = new ArrayList<MouseAction>();
	protected List<MouseAction> onMouseEnterActions = new ArrayList<MouseAction>();
	protected List<MouseAction> onMouseExitActions = new ArrayList<MouseAction>();
	
	
	@Override
	public void addOnMousePressAction(MouseAction action) {
		onMousePressActions.add(action);
	}

	@Override
	public void addOnMouseDownAction(MouseAction action) {
		onMouseDownActions.add(action);
	}

	@Override
	public void addOnMouseReleaseAction(MouseAction action) {
		onMouseReleaseActions.add(action);
	}

	@Override
	public void addOnMouseEnterAction(MouseAction action) {
		onMouseEnterActions.add(action);
	}

	@Override
	public void addOnMouseExitAction(MouseAction action) {
		onMouseExitActions.add(action);
	}

	@Override
	public boolean onMousePress(Vector2f point) {
		if (!isInside(point) ) {
			return false;
		}		
		mouseDown = true;
		for (MouseAction action : onMousePressActions) {
			action.execute(point);
		}
		return true;
	}

	@Override
	public boolean onMouseDown(Vector2f point) {
		if (!mouseDown) {
			return false;
		}
		
		for (MouseAction action : onMouseDownActions) {
			action.execute(point);
		}
		return true;
	}
	
	@Override
	public boolean onMouseRelease(Vector2f point) {
		mouseDown = false;
		if (!isInside(point)) {
			return false;
		}
		for (MouseAction action : onMouseReleaseActions) {
			action.execute(point);
		}

		return true;
	}

	@Override
	public boolean onMouseEnter(Vector2f point) {
		if (!isInside(point)) {
			return false;
		}

		if (!mouseEntered) {
			mouseEntered = true;

			for (MouseAction action : onMouseEnterActions) {
				action.execute(point);
			}
		}
		return true;
	}

	@Override
	public boolean onMouseExit(Vector2f point) {

		if (isInside(point)) {
			return false;
		}

		if (mouseEntered) {
			mouseEntered = false;

			for (MouseAction action : onMouseExitActions) {
				action.execute(point);
			}

		}
		return false;
	}

	@Override
	public void handleEvent(WidgetEvent event) {
		super.handleEvent(event);
		if (event instanceof ClickEvent) {
			ClickEvent click = (ClickEvent) event;
			if (click.isPressed()) {
				onMousePress(click.getPoint());
			} else {
				onMouseRelease(click.getPoint());
			}
		}else if(event instanceof MouseDownEvent) {
			onMouseDown(((MouseDownEvent)event).getPoint());
		}
		
	}

	
	



}
