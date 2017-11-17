package org.ploxie.engine.shaders;

import org.ploxie.math.vector.Vector3f;
import org.ploxie.utils.ResourceLoader;

public class DefaultShader extends Shader {

	@Override
	protected void initialize() {


		addVertexShader(ResourceLoader.loadShader("Default_vertex.glsl"));
		addFragmentShader(ResourceLoader.loadShader("Default_fragment.glsl"));

		compileShader();

		addUniform("color");
	}

	@Override
	protected void setDefaultUniforms() {
		setUniform("color", new Vector3f(1, 0, 1));
	}
	
}
