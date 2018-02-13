package org.ploxie.engine.gui2.component;

import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;

public class WidgetBase implements IWidget {

	private IWidget parent;
	protected List<IWidget> children = new ArrayList<IWidget>();

	protected Matrix4f transformationMatrix = new Matrix4f();
	protected Vector2f pivot = new Vector2f();

	protected boolean lockedX;
	protected boolean lockedY;
	protected boolean lockedWidth;
	protected boolean lockedHeight;

	@Override
	public void initialize() {
		children.clear();
	}

	@Override
	public void setPosition(float x, float y) {
		transformationMatrix.setTranslation(x, y, 0);
	}

	@Override
	public Vector2f getPosition() {
		return transformationMatrix.getTranslation().xy();
	}

	@Override
	public void setScale(float x, float y) {
		transformationMatrix.setScale(x, y, 1.0f);
	}

	@Override
	public Vector2f getScale() {
		return transformationMatrix.getScale().xy();
	}

	public void setPivot(Vector2f pivot) {
		this.pivot = pivot;
	}

	public void setLockedX(boolean locked) {
		lockedX = locked;
	}

	public void setLockedY(boolean locked) {
		lockedY = locked;
	}

	public void setLockedWidth(boolean locked) {
		lockedWidth = locked;
	}

	public void setLockedHeight(boolean locked) {
		lockedHeight = locked;
	}

	@Override
	public void addChild(IWidget child) {
		children.add(child);
		child.setParent(this);
		child.initialize();
	}

	@Override
	public List<IWidget> getChildren() {
		return children;
	}

	public void setParent(IWidget parent) {
		this.parent = parent;
	}

	public IWidget getParent() {
		return parent;
	}

	@Override
	public void handleEvent(WidgetEvent event) {
		for (IWidget child : children) {
			child.handleEvent(event);
		}
	}

	@Override
	public void render(Shader shader) {
		for (IWidget child : children) {
			child.render(shader);
		}
	}

	@Override
	public Matrix4f getLocalMatrix() {
		return transformationMatrix;
	}

	@Override
	public Matrix4f getWorldMatrix() {
		Matrix4f local = getLocalMatrix();
		if (parent == null) {
			local.translate(pivot.clone().multiply(-1.0f));
			return local;
		}

		Matrix4f parentWorld = parent.getWorldMatrix().clone();
		Matrix4f world = local.clone().multiply(parentWorld);
		Vector2f worldTrans = world.getTranslation().xy();
		Vector2f worldScale = world.getScale().xy();
		Vector2f localScale = local.getScale().xy();
		Vector2f localTrans = parentWorld.clone().getTranslation().xy().add(local.getTranslation().xy());
		world.setTranslation(lockedX ? localTrans.x : worldTrans.x, lockedY ? localTrans.y : worldTrans.y, 0);
		world.setScale(lockedWidth ? localScale.x : worldScale.x, lockedHeight ? localScale.y : worldScale.y, 1);
		return world.translate(pivot.clone().multiply(localScale).multiply(-1f));
	}

}
