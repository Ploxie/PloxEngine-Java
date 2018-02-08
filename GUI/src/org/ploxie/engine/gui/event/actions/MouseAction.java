package org.ploxie.engine.gui.event.actions;

import org.ploxie.utils.math.vector.Vector2f;

public abstract class MouseAction extends Action{

	public abstract void execute(Vector2f point);
	
}
