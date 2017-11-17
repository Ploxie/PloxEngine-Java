package org.ploxie.engine.scene;

import org.ploxie.engine.buffers.primitives.Quad;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.decorations.Locatable;
import org.ploxie.engine.scene.decorations.Renderable;

public class Sprite2D implements Renderable{

	private Quad quad;
	
	public Sprite2D() {
		quad = new Quad(50, 50);
	}
	
	@Override
	public void render(Camera camera) {
		quad.draw();
	}


}
