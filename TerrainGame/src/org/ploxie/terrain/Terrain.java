package org.ploxie.terrain;

import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.decorations.Locatable;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.math.matrix.Matrix4f;

public class Terrain extends Component implements Renderable, Locatable{

	private final VBO vbo;
	private final int vertexCount;
	private TerrainShader shader;
	private Matrix4f transformation;
	
	public Terrain(VBO vbo, int vertexCount) {
		this.vbo = vbo;
		this.vertexCount = vertexCount;
		shader = new TerrainShader();
		transformation = new Matrix4f();
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public VBO getVBO() {
		return vbo;
	}

	@Override
	public void render(Camera camera) {
		shader.bind();
		shader.setUniforms(camera,transformation);
		vbo.draw();
	}

	@Override
	public Matrix4f getTransformation() {
		return transformation;
	}

	
	
	
}
