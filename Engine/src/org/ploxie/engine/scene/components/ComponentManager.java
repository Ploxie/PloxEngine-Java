package org.ploxie.engine.scene.components;

import java.util.ArrayList;
import java.util.List;

import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.scene.decorations.Updatable;

public class ComponentManager {

	private static ComponentManager instance;

	private List<Component> components;
	private List<Component> renderableComponents;

	private ComponentManager() {
		components = new ArrayList<Component>();
		renderableComponents = new ArrayList<Component>();
	}

	public Component addComponent(Component component) {
		if (component instanceof Renderable) {
			renderableComponents.add(component);
		} 
		
		components.add(component);

		return component;
	}

	public void removeComponent(Component component) {
		if (component instanceof Renderable) {
			renderableComponents.remove(component);
		} else {
			components.remove(component);
		}
	}

	public void update() {
		for(Component c : components) {
			if(c instanceof Updatable) {				
				((Updatable)c).update();
			}
		}
	}

	public void render() {
		for(Component c : renderableComponents) {
			((Renderable)c).render();
		}
	}

	public static ComponentManager getInstance() {
		if (instance == null) {
			instance = new ComponentManager();
		}

		return instance;
	}

}
