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
	protected Vector2f anchorPoint = new Vector2f(0,0);
	protected Vector2i absoluteSize = null;

	protected boolean isDynamic = false;

	public void addChild(Widget child) {
		children.add(child);
		child.setParent(this);
	}

	@Override
	public void setSize(float x, float y) {
		size.x = x;
		size.y = y;
		absoluteSize = null;
	}

	@Override
	public Vector2f getSize() {
		if (parent == null) {
			return size;
		}
		if(absoluteSize != null) {
			Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
			return new Vector2f((float)absoluteSize.x / (float)screenDimensions.x, (float)absoluteSize.y / (float)screenDimensions.y);
		}
		return size.clone().multiply(parent.getSize());
	}

	public void setAbsoluteSize(int x, int y) {		
		absoluteSize = new Vector2i(x, y);
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
		
		return position.clone().multiply(parent.getSize()).add(parent.getPosition());
	}

	public void setAbsolutePosition(int x, int y) {
		Vector2i screenDim = WidgetManager.getInstance().getViewport().getDimensions();
		setPosition(x / (float)screenDim.x, y / (float)screenDim.y);
	}
	
	public Vector2i getAbsolutePosition() {		
		Vector2i screenDim = WidgetManager.getInstance().getViewport().getDimensions();
		Vector2i res = new Vector2i(0,0);
		if (parent == null) {
			res.x = (int) (position.x * screenDim.x);
			res.y = (int) (position.y * screenDim.y);
			return res;
		}
		
		Vector2f pos = getPosition();
		res.x = (int) (pos.x * screenDim.x);
		res.y = (int) (pos.y * screenDim.y);
		return res;
	}
	
	public void setAnchorPoint(float x, float y) {
		anchorPoint.x = x;
		anchorPoint.y = y;
	}
	
	public Vector2f getAnchorPoint() {
		return anchorPoint;
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

		return pos.y - (getPivot().y * dim.y) + dim.y;
	}

	@Override
	public float getTop() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();
		return (pos.y - (getPivot().y * dim.y));
	}

	@Override
	public float getLeft() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();
		return pos.x - (getPivot().x * dim.x);
	}

	@Override
	public float getRight() {
		Vector2f pos = getPosition();
		Vector2f dim = getSize();
		return pos.x - (getPivot().x * dim.x) + dim.x;
	}	
	
	@Override
	public boolean isInside(Vector2f point) {
		return point.x >= getLeft() && point.x <= getRight() && point.y >= getTop() && point.y <= getBottom();
	}

	@Override
	public Vector2f getRelativePointOn(Vector2f point) {	
		return point.clone().subtract(getPosition()).divide(getSize());
	}
	
	public void handleEvent(WidgetEvent event) {
		for(Widget child : children) {
			child.handleEvent(event);
		}
	}


}
