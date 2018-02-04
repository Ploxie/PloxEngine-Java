package org.ploxie.engine.scene.components;

import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.decorations.Locatable;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.scene.decorations.Updatable;
import org.ploxie.engine.shaders.PrimitiveShader;
import org.ploxie.utils.math.matrix.Matrix4f;

public class PrimitiveRenderer extends Component implements Renderable, Locatable, Updatable{

	private VBO primitive;
	private PrimitiveShader shader;
	private Matrix4f transform;
	
	public PrimitiveRenderer(VBO primitive) {
		this.primitive = primitive;
		this.shader = new PrimitiveShader();
		this.transform = new Matrix4f();
	}
	
	@Override
	public void render(Camera camera) {
		shader.bind();
		shader.setUniforms(camera,transform);
		primitive.draw();
	}

	@Override
	public Matrix4f getTransformation() {
		return transform;
	}

	@Override
	public void update() {
		//transform.rotate(45 * Engine.getFPSCounter().getDelta(), new Vector3f(0,1,0));
	}

}
