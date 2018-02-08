package org.ploxie.engine.gui;

import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.buffers.primitives.Quad;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class GUIComponent extends Component implements Renderable {

	private Matrix4f transform;
	private GUIShader shader;

	private Color color;
	private VBO vbo;
	
	private GUIComponent parent;

	public GUIComponent(Vector2f location) {
		transform = new Matrix4f();
		shader = new GUIShader();
		color = new Color(1, 0, 1, 0.5f);
		vbo = new Quad();
		setBounds(new Vector2f(0.5f, 0.5f));

		transform.translate(location);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setBounds(Vector2f bounds) {
		setBounds(bounds.x, bounds.y);
	}

	public void setBounds(float width, float height) {
		if (width < 1 || height < 1) {
			Vector2i screen = Engine.getViewport().getDimensions();
			width *= screen.x;
			height *= screen.y;
		}

		this.transform.set(0, 0, width);
		this.transform.set(1, 1, height);
	}

	@Override
	public void render() {

		shader.bind();
		//shader.setUniforms(camera, getTransformation(), color);
		vbo.draw();
	}

		


}
