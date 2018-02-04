package org.ploxie.engine.scene.components;

import org.ploxie.engine.scene.decorations.Updatable;
import org.ploxie.utils.math.matrix.Matrix4f;

public class Transform extends Component implements Updatable{

	private Matrix4f local;
	private Matrix4f world;	
	
	@Override
	public void awake() {
		local = new Matrix4f();
		world = new Matrix4f();
	}
	
	@Override
	public void update() {		
		world = local;
		
		if(gameObject.getParent() != null) {
			world = gameObject.getParent().getTransform().world.clone().multiply(world);
		}
	}
	
	public Matrix4f getLocalMatrix() {
		return local;
	}

}
