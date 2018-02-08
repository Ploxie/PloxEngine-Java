package org.ploxie.engine.gui.component;

import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public abstract class WidgetBase implements Widget{
	
	protected Widget parent = null;
	protected List<Widget> children = new ArrayList<Widget>();
	
	protected Vector2f size = new Vector2f(1, 1);
	protected Vector2f position = new Vector2f(0, 0);
	protected Vector2f pivot = new Vector2f(0.0f, 0.0f);

	protected boolean isDynamic = false;

	public void addChild(Widget child) {
		children.add(child);
		child.setParent(this);
	}

	@Override
	public void setSize(float x, float y) {
		size.x = x;
		size.y = y;
	}

	@Override
	public Vector2f getSize() {
		if (parent == null) {
			return size;
		}
		return size.clone().multiply(parent.getSize());
	}

	public void setAbsoluteSize(int x, int y) {
		Vector2i screenDim = WidgetManager.getInstance().getViewport().getDimensions();
		setSize(x / screenDim.x, y / screenDim.y);
	}
		
	public Vector2i getAbsoluteSize() {
		Vector2i screenDim = WidgetManager.getInstance().getViewport().getDimensions();
		Vector2i res = new Vector2i(0,0);
		if (parent == null) {
			res.x = (int) (size.x * screenDim.x);
			res.y = (int) (size.y * screenDim.y);
			return res;
		}
		
		Vector2f pos = size.clone().multiply(parent.getSize());
		res.x = (int) (pos.x * screenDim.x);
		res.y = (int) (pos.y * screenDim.y);
		return res;
	}
	
	@Override
	public void setPosition(float x, float y) {		
		position.x = x;
		position.y = y;
	}
	
	@Override
	public Vector2f getPosition() {
		if (parent == null) {
			return position;
		}
		return position.clone().add(parent.getPosition());
	}

	public void setAbsolutePosition(int x, int y) {
		Vector2i screenDim = WidgetManager.getInstance().getViewport().getDimensions();
		setPosition(x / screenDim.x, y / screenDim.y);
	}
	
	public Vector2i getAbsolutePosition() {
		Vector2i screenDim = WidgetManager.getInstance().getViewport().getDimensions();
		Vector2i res = new Vector2i(0,0);
		if (parent == null) {
			res.x = (int) (position.x * screenDim.x);
			res.y = (int) (position.y * screenDim.y);
			return res;
		}
		
		Vector2f pos = position.clone().multiply(parent.getPosition());
		res.x = (int) (pos.x * screenDim.x);
		res.y = (int) (pos.y * screenDim.y);
		return res;
	}
	
	@Override
	public void setDynamic(boolean flag) {
		isDynamic = flag;
	}

	@Override
	public boolean isDynamic() {
		return isDynamic;
	}

	@Override
	public void setPivot(float x, float y) {
		pivot.x = x;
		pivot.y = y;
	}

	@Override
	public Vector2f getPivot() {
		return pivot;
	}

	@Override
	public void setParent(Widget parent) {
		this.parent = parent;
	}

	@Override
	public Widget getParent() {
		return parent;
	}

	@Override
	public List<Widget> getChildren() {
		return children;
	}

	@Override
	public float getBottom() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();

		return pos.y + (pivot.y * dim.y) + dim.y;
	}

	@Override
	public float getTop() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();
		return (pos.y + (pivot.y * dim.y));
	}

	@Override
	public float getLeft() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();
		return pos.x + (pivot.x * dim.x);
	}

	@Override
	public float getRight() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();
		return pos.x + (pivot.x * dim.x) + dim.x;
	}	
	
	public void handleEvent(WidgetEvent event) {
		for(Widget child : children) {
			child.handleEvent(event);
		}
	}


}