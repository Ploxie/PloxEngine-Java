package org.ploxie.engine.gui;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import org.ploxie.engine.gui.component.Widget;
import org.ploxie.engine.gui.component.WidgetBase;
import org.ploxie.engine.gui.component.WidgetPanel;
import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.engine.gui.component.Renderable;
import org.ploxie.engine.gui.shader.Shader;
import org.ploxie.utils.Viewport;

public class WidgetManager{

	private static WidgetManager instance = null;

	private Shader shader;
	private Widget rootObject;
	private Viewport viewport;

	protected WidgetManager(Viewport viewport) {
		setViewport(viewport);
		shader = new Shader("../GUI/res/gui");
			
	}
	
	public void addWidget(Widget object) {
		rootObject.addChild(object);
	}
	
	public void sendEvent(WidgetEvent event) {
		rootObject.handleEvent(event);
	}

	public void render() {
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		shader.bind();
		for (Widget child : rootObject.getChildren()) {
			if(child instanceof Renderable) {				
				((Renderable)child).render(shader);
			}
		}
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public static WidgetManager create(Viewport viewport) {
		instance = new WidgetManager(viewport);
		
		instance.rootObject = new WidgetBase() {
			
		};	
		
		return getInstance();
	}

	public static WidgetManager getInstance() {
		return instance;
	}
	

}
