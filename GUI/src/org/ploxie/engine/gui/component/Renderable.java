package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.shader.Shader;
import org.ploxie.utils.Color;

public interface Renderable {

	public void render(Shader shader);

	public void setBackgroundColor(Color color);

	public void setBorderColor(Color color);

	public void setBorderThickness(float thickness);

	public void setBorderThickness(int pixels);

}
