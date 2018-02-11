package org.ploxie.engine.font;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import org.ploxie.engine.Engine;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.ResourceLoader;

public class TextShader extends Shader {

	@Override
	protected void initialize() {
		String vert = ResourceLoader.loadShader("text_vert.glsl");
		String frag = ResourceLoader.loadShader("text_frag.glsl");

		addVertexShader(vert);
		addFragmentShader(frag);

		compileShader();		

	}
	
	
	public void setUniforms(Camera camera, Matrix4f transformation, Texture2D texture) {
		setUniform("modelMatrix", transformation);
		setUniform("projectionMatrix", camera.getProjectionMatrix());
		glActiveTexture(GL_TEXTURE0 );
		texture.bind();
		texture.bilinearFilter();
		setUniform("bitmapSampler", 0);
	}
	

}
