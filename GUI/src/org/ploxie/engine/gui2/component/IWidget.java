package org.ploxie.engine.gui2.component;

import java.util.List;

import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.engine.gui2.WidgetManager;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public interface IWidget {

	public void initialize();
	
	public default void setPosition(int x, int y) {
		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		setPosition(x / (float)screenDimensions.x, y / (float) screenDimensions.y);
	}
	
	public void setPosition(float x, float y);

	public default void setPosition(Vector2f position) {
		setPosition(position.x, position.y);
	}
	
	public default void setPosition(Vector2i position) {
		setPosition(position.x, position.y);
	}

	public Vector2f getPosition();

	public default void setSize(int x, int y) {
		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		setScale(x / (float)screenDimensions.x, y / (float) screenDimensions.y);
	}
	
	public default void setWidth(int width) {
		Vector2f scale = getScale();
		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		setScale(width / (float)screenDimensions.x, scale.y);
	}
	
	public default void setHeight(int height) {
		Vector2f scale = getScale();
		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		setScale(scale.x, height / (float)screenDimensions.y);
	}	
	
	public void setScale(float x, float y);

	public default void setScale(Vector2f scale) {
		setScale(scale.x, scale.y);
	}
	
	public Vector2f getScale();
	
	public default void setPivot(float x, float y) {
		setPivot(new Vector2f(x,y));
	}
	
	public default Vector2f getWorldPosition() {
		return getWorldMatrix().getTranslation().xy();
	}
	
	public default Vector2i getAbsolutePosition() {
		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		Vector2f pos = getPosition();
		return new Vector2i((int)(pos.x * screenDimensions.x) , (int)(pos.y * screenDimensions.y));
	}
	
	public default Vector2f getWorldScale() {
		return getWorldMatrix().getScale().xy();
	}
	
	public default Vector2i getSize() {
		Vector2i screenDimensions = getManager().getViewport().getDimensions();
		Vector2f scale = getWorldScale();
		return new Vector2i((int)(scale.x * screenDimensions.x) , (int)(scale.y * screenDimensions.y));
	}
	
	public void setLockedX(boolean locked);
	public void setLockedY(boolean locked);
	public void setLockedWidth(boolean locked);
	public void setLockedHeight(boolean locked);
	
	public default void setLockedPosition(boolean locked) {
		setLockedX(locked);
		setLockedY(locked);
	}
	
	public default void setLockedScale(boolean locked) {
		setLockedWidth(locked);
		setLockedHeight(locked);
	}
	
	public void setPivot(Vector2f pivot);
	
	public void addChild(IWidget child);	
	
	public List<IWidget> getChildren();
	
	public void setParent(IWidget parent);

	public IWidget getParent();	
	
	public void handleEvent(WidgetEvent event);
	
	public void render(Shader shader);
	
	public Matrix4f getLocalMatrix();
	
	public Matrix4f getWorldMatrix();
		
	public default Vector2f getRelativePoint(Vector2f point) {
		return point.clone().subtract(getPosition());
	}
	
	public default WidgetManager getManager() {
		return WidgetManager.getInstance();
	}
}
