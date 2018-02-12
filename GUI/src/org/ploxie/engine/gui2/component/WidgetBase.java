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
	
	protected boolean pixelBasedX;
	protected boolean pixelBasedY;
	protected boolean pixelBasedWidth;
	protected boolean pixelBasedHeight;

	@Override
	public void initialize() {
		children.clear();
	}	

	@Override
	public void setPosition(int x, int y) {	
		IWidget.super.setPosition(x, y);
		pixelBasedX = true;
		pixelBasedY = true;
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
	public void setSize(int x, int y) {
		IWidget.super.setSize(x, y);
		pixelBasedWidth = true;
		pixelBasedHeight = true;
	}
	
	@Override
	public void setWidth(int width) {
		IWidget.super.setWidth(width);
		pixelBasedWidth = true;
	}
	
	@Override
	public void setHeight(int height) {
		IWidget.super.setHeight(height);
		pixelBasedHeight = true;
	}	
	
	@Override
	public void setScale(float x, float y) {
		transformationMatrix.setScale(x, y, 1.0f);
	}

	@Override
	public Vector2f getScale() {
		return transformationMatrix.getScale().xy();
	}

	public void setPivot(Vector2f pivot){
		this.pivot = pivot;
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
		Matrix4f trans = transformationMatrix.clone();
		//trans.translate(pivot.clone().multiply(-1.0f));
		return trans;
	}

	@Override
	public Matrix4f getWorldMatrix() {
		Matrix4f local = getLocalMatrix();
		if (parent == null) {
			local.translate(pivot.clone().multiply(-1.0f));
			return local;
		}
		
		Matrix4f world = local.clone().multiply(parent.getWorldMatrix());
		Vector2f worldTrans = world.getTranslation().xy();
		Vector2f worldScale = world.getScale().xy();
		Vector2f localScale = local.getScale().xy();
		Vector2f localTrans = worldTrans.clone().add(local.getTranslation().xy());
		world.setTranslation(pixelBasedX ? localTrans.x : worldTrans.x, pixelBasedY ? localTrans.y : worldTrans.y, 0);
		world.setScale(pixelBasedWidth ? localScale.x : worldScale.x, pixelBasedHeight ? localScale.y : worldScale.y, 1);
		return world.translate(pivot.clone().multiply(world.getScale().xy()).multiply(-1f));
	}




}
