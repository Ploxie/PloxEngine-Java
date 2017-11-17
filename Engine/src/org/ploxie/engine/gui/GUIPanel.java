package org.ploxie.engine.gui;

import org.ploxie.engine.Engine;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.Sprite2D;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.decorations.Locatable;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.engine.utils.Color;
import org.ploxie.math.matrix.Matrix4f;
import org.ploxie.math.vector.Vector2f;
import org.ploxie.math.vector.Vector3f;

public class GUIPanel extends Component implements Renderable, Locatable {

	private Sprite2D background = new Sprite2D();
	private Matrix4f transform = new Matrix4f();

	private GUIShader shader = new GUIShader();
	
	private Color color;

	public GUIPanel(Vector2f location, Color color) {
		transform.translate(location);
		this.color = color;
	}
	
	@Override
	public void render(Camera camera) {

		shader.bind();
		shader.setUniforms(camera, transform, color);
		background.render(camera);
	}

	@Override
	public Matrix4f getTransformation() {
		return transform;
	}

}
