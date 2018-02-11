package org.ploxie.engine.gui2.component;

import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.gui2.WidgetManager;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetBase implements IWidget {
	
	private IWidget parent;
	protected Matrix4f transformationMatrix = new Matrix4f();
	protected List<IWidget> children = new ArrayList<IWidget>();

	@Override
	public void initialize() {

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
		if (parent == null) {
			return getLocalMatrix();
		}
		return transformationMatrix.clone().multiply(parent.getWorldMatrix());
	}



}
