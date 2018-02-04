package org.ploxie.terrain;

import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.utils.ResourceLoader;
import org.ploxie.utils.math.matrix.Matrix4f;

public class TerrainShader extends Shader{

	@Override
	protected void initialize() {
		
		addVertexShader(ResourceLoader.loadShader("terrain_vert.glsl"));
		addFragmentShader(ResourceLoader.loadShader("terrain_frag.glsl"));

		compileShader();

		//addUniform("lightDirection");
		//addUniform("lightColour");
		//addUniform("lightBias");
		addUniform("modelMatrix");	
		addUniform("viewMatrix");	
		addUniform("projectionMatrix");	
	}

	public void setUniforms(Camera camera, Matrix4f transformation) {
		setUniform("modelMatrix", transformation);
		setUniform("viewMatrix", camera.getViewMatrix());
		setUniform("projectionMatrix", camera.getProjectionMatrix());
	}
	
}
