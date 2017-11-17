package org.ploxie.engine.shaders;

import java.util.HashMap;

import org.ploxie.engine.Engine;
import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.engine.utils.Color;
import org.ploxie.math.matrix.Matrix4f;
import org.ploxie.math.vector.Vector2f;
import org.ploxie.math.vector.Vector3f;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader {

	private static int currentProgram = -1;

	private int program;
	private HashMap<String, Integer> uniforms;
	
	private boolean defaulted;
	
	public Shader() {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();

		if (program == 0) {
			throw new RuntimeException("Failed to create Shader");
		}
		
		initialize();
	}
	
	protected abstract void initialize();
		
	protected void setDefaultUniforms() {
		
	}
	

	public void bind() {
		if(!defaulted) {
			setDefaultUniforms();
		}
		if (currentProgram != program) {
			glUseProgram(program);
		}
	}

	protected int addUniform(String uniform) {
		int uniformLocation = glGetUniformLocation(program, uniform);

		if (uniformLocation == 0xFFFFFFFF) {
			throw new RuntimeException("Failed to find uniform: " + uniform);
		}

		uniforms.put(uniform, uniformLocation);
		return uniformLocation;
	}

	public void addVertexShader(String source) {
		addProgram(source, GL_VERTEX_SHADER);
	}

	public void addFragmentShader(String source) {
		addProgram(source, GL_FRAGMENT_SHADER);
	}

	public void compileShader() {
		glLinkProgram(program);

		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			throw new RuntimeException("Failed to link Shader");
		}

		glValidateProgram(program);

		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			throw new RuntimeException("Failed to validate Shader");
		}
	}

	private void addProgram(String source, int type) {
		int shader = glCreateShader(type);

		if (shader == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			throw new RuntimeException("Failed to create Shader");
		}

		
		glShaderSource(shader, source);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			throw new RuntimeException("Failed to compile Shader");
		}

		glAttachShader(program, shader);
	}

	protected void setUniform(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}

	protected void setUniform(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}

	protected void setUniform(String uniformName, Vector2f value) {
		glUniform2f(uniforms.get(uniformName), value.x, value.y);
	}

	protected void setUniform(String uniformName, Vector3f value) {
		glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
	}
	
	protected void setUniform(String uniformName, Color value) {
		glUniform3f(uniforms.get(uniformName), value.getR(), value.getG(), value.getB());
	}

	protected void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4fv(uniforms.get(uniformName), false, BufferUtils.createFlippedBuffer(value));
	}

}
