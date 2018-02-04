package org.ploxie.engine.scene.components;

import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.camera.Camera3D;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.shaders.Shader;

public class Renderer extends Component implements Renderable{

	private VBO vbo;
	private Shader shader;
	
	private Transform transform;
	
	public Renderer(VBO vbo, Shader shader) {		
		this.vbo = vbo;		
		this.shader = shader;
	}
	
	@Override
	public void awake() {
		transform = getGameObject().getComponent(Transform.class);
	}
	
		
	@Override
	public void render() {
		shader.bind();
		Camera camera = Engine.getCamera2D();
		
		shader.setUniform("projectionMatrix", camera.getProjectionMatrix());
		shader.setUniform("viewMatrix", camera.getViewMatrix());
		shader.setUniform("modelMatrix", transform.getLocalMatrix());
		vbo.draw();
	}

}
