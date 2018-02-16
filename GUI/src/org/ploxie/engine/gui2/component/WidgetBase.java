package org.ploxie.engine.gui2.component;

import java.util.ArrayList;
import java.util.List;

import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;

public class WidgetBase implements IWidget {

	private IWidget parent;
	protected List<IWidget> children = new ArrayList<IWidget>();

	protected Matrix4f transformationMatrix = new Matrix4f();
	protected Vector2f anchorPosition = new Vector2f();
	protected Vector2f anchorSize = new Vector2f(1,1);
	protected Vector2f pivot = new Vector2f();

	@Override
	public void setParent(IWidget parent) {
		this.parent = parent;
	}
	
	@Override
	public IWidget getParent() {
		return parent;
	}
	
	@Override
	public List<IWidget> getChildren() {
		return children;
	}
	
	public void setTop(float y) {		
		float height = getHeight();		
				
		setPosition(getPosition().x, y);
		setHeight(height-y);
	}
	
	@Override
	public void setPosition(Vector2f position) {
		Vector2f parentScale = parent.getScale().clone();
		Vector2f newPos = position.clone().divide(getManager().getViewport().getDimensions().toFloat()).divide(parentScale);
		transformationMatrix.setTranslation(newPos);
	}
	
	@Override
	public Vector2f getPosition() {
		return transformationMatrix.getTranslation().xy().multiply(getManager().getViewport().getDimensions().toFloat());
	}
	
	@Override
	public void setTranslation(float x, float y) {
		transformationMatrix.setTranslation(x, y, 0);
	}

	@Override
	public Vector2f getTranslation() {
		return transformationMatrix.getTranslation().xy();
	}

	@Override
	public void setWidth(float width) {
		Vector2f scale = getScale().clone();
		float anchorXScale = getAnchorMatrix().getScale().x;
		if(anchorXScale == 0) {
			anchorXScale = 1;
		}
		scale.x = width / (float)getManager().getViewport().getDimensions().x / anchorXScale;
		transformationMatrix.setScale(scale.x, scale.y, 0);
	}
	
	@Override
	public float getWidth() {		
		return transformationMatrix.getScale().x * (float)getManager().getViewport().getDimensions().x;
	}
	
	@Override
	public void setHeight(float height) {
		Vector2f worldScale = getWorldScale().clone();
		transformationMatrix.setScale(getScale().x, height / (float)getManager().getViewport().getDimensions().y / worldScale.y, 0);
	}
	
	@Override
	public float getHeight() {
		return transformationMatrix.getScale().y * (float)getManager().getViewport().getDimensions().y * getWorldScale().y;
	}
	
	@Override
	public void setScale(float x, float y) {
		transformationMatrix.setScale(x, y, 1.0f);
	}

	@Override
	public Vector2f getScale() {
		return transformationMatrix.getScale().xy();
	}
	
	public void setAnchorPosition(Vector2f position) {		
		Vector2f parentScale = parent != null ? parent.getScale().clone() : new Vector2f(1,1);
		position.divide(getManager().getViewport().getDimensions().toFloat().clone().multiply(parentScale));
		setAnchorTranslation(position);
	}
	
	@Override
	public void setAnchorTranslation(Vector2f position) {
		anchorPosition = position;
	}
	
	@Override
	public void setAnchorScale(Vector2f scale) {
		anchorSize = scale;
		Vector2f diff = getAnchorMatrix().getScale().xy().subtract(getAnchorMatrix().getTranslation().xy());
		if(diff.x == 0) {
			diff.x = 1;
		}
		if(diff.y == 0) {
			diff.y = 1;
		}
		transformationMatrix.setScale(getScale().clone().multiply(getAnchorMatrix().getScale().xy()));
	}	

	@Override
	public void setPivot(Vector2f pivot) {
		this.pivot = pivot;
	}	
	
	@Override
	public Vector2f getPivot() {
		return pivot;
	}
	
	@Override
	public Matrix4f getLocalMatrix() {
		return transformationMatrix;
	}	
	
	@Override
	public Matrix4f getWorldMatrix() {
		Matrix4f local = getLocalMatrix().clone();
		if (parent == null) {
			return local;
		}
		
		Matrix4f anchor = getAnchorMatrix().clone();		
		local.multiply(anchor);
		
		Vector2f scale = local.getScale().xy();
		Vector2f translation = local.getTranslation().xy();
		local.setTranslation(translation.subtract(getPivot().clone().multiply(scale)));
		return local;
	}

	@Override
	public Matrix4f getAnchorMatrix() {
		if(parent == null) {
			return new Matrix4f();
		}
		
		Matrix4f anchor = parent.getWorldMatrix();
		Vector2f parentScale = anchor.getScale().xy(); 
		Vector2f anchorScale = anchorSize.clone().subtract(anchorPosition);		
		Vector2f worldScale = parentScale.clone().multiply(anchorScale);		
		Vector2f scale = worldScale;
		if(anchorScale.x == 0) {
			scale.x = 1;
		}
		if(anchorScale.y == 0) {
			scale.y = 1;
		}
		anchor.setTranslation(anchor.getTranslation().xy().add(anchorPosition.clone().multiply(parentScale)));
		anchor.setScale(scale);
		return anchor;
	}

}
