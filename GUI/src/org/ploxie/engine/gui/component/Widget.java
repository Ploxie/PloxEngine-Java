package org.ploxie.engine.gui.component;

import java.util.List;

import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public interface Widget {

	public void setSize(float x, float y);

	public default void setSize(Vector2f size) {
		setSize(size.x, size.y);
	}

	public Vector2f getSize();
	
	public void setAbsoluteSize(int x, int y);
	
	public default void setAbsoluteSize(Vector2i size) {
		setAbsoluteSize(size.x, size.y);
	}
	
	public Vector2i getAbsoluteSize();

	public void setPosition(float x, float y);

	public default void setPosition(Vector2f position) {
		setPosition(position.x, position.y);
	}
	
	public Vector2f getPosition();	

	public void setAbsolutePosition(int x, int y);
	
	public default void setAbsolutePosition(Vector2i position) {
		setPosition(position.x, position.y);
	}
	
	public Vector2i getAbsolutePosition();

	public void setDynamic(boolean flag);

	public boolean isDynamic();

	public void setPivot(float x, float y);

	public default void setPivot(Vector2f pivot) {
		setPivot(pivot.x, pivot.y);
	}

	public Vector2f getPivot();

	public void setParent(Widget parent);

	public Widget getParent();

	public void addChild(Widget child);
	
	public List<Widget> getChildren();

	public float getTop();

	public float getBottom();

	public float getLeft();

	public float getRight();
	
	public void handleEvent(WidgetEvent event);

}
