package org.ploxie.engine.scene.decorations;

import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector3f;

public interface Locatable {

	public Matrix4f getTransformation();
	
	public default void setPosition(Vector2f position) {
		getTransformation().setTranslation(position);
	}
	
	public default void setPosition(Vector3f position) {
		getTransformation().setTranslation(position);
	}
	
}
