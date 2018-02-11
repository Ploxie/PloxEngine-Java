package org.ploxie.engine.gui2;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

import org.ploxie.engine.gui2.component.IWidget;
import org.ploxie.engine.gui2.component.WidgetBase;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Viewport;
import org.ploxie.utils.math.matrix.Matrix4f;

public class WidgetManager{
	
	private static WidgetManager instance;
	
	private IWidget root = new WidgetBase();
	private Shader shader;
	private Viewport viewport;
	
	protected WidgetManager(Viewport viewport) {
		shader = new Shader("../GUI/res/gui2");
		this.viewport = viewport;
	}
	
	public void render() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		shader.bind();		
		shader.setUniform("screenDimensions", viewport.getDimensions());
		root.render(shader);
	}
	
	public void addChild(IWidget widget) {
		root.addChild(widget);
	}
	
	public Viewport getViewport() {
		return viewport;
	}
	
	public static WidgetManager create(Viewport viewport) {
		if(instance == null) {
			instance = new WidgetManager(viewport);
		}
		return instance;
	}
	
	public static WidgetManager getInstance() {
		return instance;
	}
}
