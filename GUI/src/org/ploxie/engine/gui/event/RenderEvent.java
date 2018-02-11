package org.ploxie.engine.gui.event;

import org.ploxie.opengl.shader.Shader;

public class RenderEvent extends WidgetEvent {

	private Shader shader;

	public RenderEvent(Shader shader) {
		this.shader = shader;
	}

	public Shader getShader() {
		return shader;
	}

}
