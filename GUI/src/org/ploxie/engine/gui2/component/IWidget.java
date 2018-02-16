package org.ploxie.engine.gui2.component;

import java.util.List;

import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.engine.gui2.WidgetManager;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public interface IWidget {

	public default void initialize() {
		getChildren().clear();
	}
	
	public void setParent(IWidget parent);

	public IWidget getParent();	
	
	public List<IWidget> getChildren();
			
	public void setTranslation(float x, float y);
	

	public void setPosition(Vector2f position);
	
	public default void setPosition(float x, float y) {
		setPosition(new Vector2f(x,y));
	}
	
	public default float getTop() {
		return getPosition().y - getPivot().y;		
	}
	
	public Vector2f getPosition();
	
	public default void setTranslation(Vector2f position) {
		setTranslation(position.x, position.y);
	}
	
	public Vector2f getTranslation();

	public default void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}
		
	public void setWidth(float width);
	
	public float getWidth();
	
	public void setHeight(float height);
	
	public float getHeight();
	
	public void setScale(float x, float y);	
	
	public default void setScale(Vector2f scale) {
		setScale(scale.x, scale.y);
	}
	
	public Vector2f getScale();	
	
	public default Vector2f getWorldPosition() {
		return getWorldMatrix().getTranslation().xy();
	}
		
	public default Vector2f getWorldScale() {
		return getWorldMatrix().getScale().xy();
	}

	public void setAnchorPosition(Vector2f position);
	
	public default void setAnchorPosition(float x, float y) {
		setAnchorPosition(new Vector2f(x,y));
	}
	
	public void setAnchorTranslation(Vector2f position);
	
	public default void setAnchorTranslation(float x, float y) {
		setAnchorTranslation(new Vector2f(x,y));
	}
	
	public void setAnchorScale(Vector2f scale);
	
	public default void setAnchorScale(float x, float y) {
		setAnchorScale(new Vector2f(x,y));
	}
	
	public void setPivot(Vector2f pivot);
	
	public default void setPivot(float x, float y) {
		setPivot(new Vector2f(x,y));
	}	
	
	public Vector2f getPivot();
	
	public default void addChild(IWidget child) {
		getChildren().add(child);
		child.setParent(this);
		child.initialize();
	}	
	
	public default void handleEvent(WidgetEvent event) {
		for (IWidget child : getChildren()) {
			child.handleEvent(event);
		}
	}
	
	public default void render(Shader shader) {
		for (IWidget child : getChildren()) {
			child.render(shader);
		}
	}
	
	public Matrix4f getLocalMatrix();
	
	public Matrix4f getAnchorMatrix();
	
	public Matrix4f getWorldMatrix();
		
	public default Vector2f getRelativePoint(Vector2f point) {
		return point.clone().subtract(getTranslation());
	}
	
	public default WidgetManager getManager() {
		return WidgetManager.getInstance();
	}
}
