package org.ploxie.engine.gui;

import org.ploxie.engine.Engine;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.camera.Camera2D;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.engine.utils.Color;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.ResourceLoader;

public class GUIShader extends Shader{

	@Override
	protected void initialize() {
		String vert = ResourceLoader.loadShader("gui_vert.glsl");
		String frag = ResourceLoader.loadShader("gui_frag.glsl");

		addVertexShader(vert);
		addFragmentShader(frag);

		compileShader();
		
		getUniform("transformMatrix");
		getUniform("orthoMatrix");
		getUniform("color");
	}

	public void setUniforms(Camera camera, Matrix4f transformation, Color color) {
		setUniform("transformMatrix", transformation);
		setUniform("orthoMatrix", Engine.getCamera2D().getProjectionMatrix());
		setUniform("color", color);
	}

}
