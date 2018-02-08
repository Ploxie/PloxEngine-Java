package org.ploxie.engine.gui.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.ploxie.utils.Color;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;
import org.ploxie.utils.math.vector.Vector3f;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private static int currentProgram = -1;

	private int program;
	private HashMap<String, Integer> uniforms;

	public Shader(String fileName) {
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();

		if (program == 0) {
			throw new RuntimeException("Failed to create Shader");
		}

		addVertexShader(loadShader(fileName+"_vert.glsl"));
		addFragmentShader(loadShader(fileName+"_frag.glsl"));
		compileShader();
	}
	
	private String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try {
			shaderReader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
			
		}catch(Exception e) {
			e.printStackTrace();	
			System.exit(1);
		}
		
		return shaderSource.toString();
	}

	public void bind() {
		if (currentProgram != program) {
			glUseProgram(program);
		}
	}

	protected int getUniform(String uniform) {
		int uniformLocation = glGetUniformLocation(program, uniform);

		if (uniformLocation == 0xFFFFFFFF) {
			System.err.println("Failed to find uniform: " + uniform);
			// throw new RuntimeException("Failed to find uniform: " + uniform);
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

	public void setUniform(String uniformName, int value) {
		glUniform1i(getUniform(uniformName), value);
	}

	public void setUniform(String uniformName, float value) {
		glUniform1f(getUniform(uniformName), value);
	}

	public void setUniform(String uniformName, Vector2f value) {
		glUniform2f(getUniform(uniformName), value.x, value.y);
	}
	
	public void setUniform(String uniformName, Vector2i value) {
		glUniform2i(getUniform(uniformName), value.x, value.y);
	}

	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(getUniform(uniformName), value.x, value.y, value.z);
	}

	public void setUniform(String uniformName, Color value) {
		glUniform4f(getUniform(uniformName), value.getR(), value.getG(), value.getB(), value.getA());
	}

	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4fv(getUniform(uniformName), false, createFlippedBuffer(value));
	}
	
	private static FloatBuffer createFloatBuffer(int size) {
		return org.lwjgl.BufferUtils.createFloatBuffer(size);
	}
	
	private static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
		FloatBuffer buffer = createFloatBuffer(16);
		matrix.fillBuffer(buffer);
		buffer.flip();

		return buffer;
	}

}
