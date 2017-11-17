package org.ploxie.engine.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.scene.decorations.Updatable;

public class GameObject {

	protected GameObject parent;
	protected List<GameObject> children;
	protected HashMap<String, Component> components;

	public GameObject(Component...components) {
		setChildren(new ArrayList<GameObject>());
		this.components = new HashMap<String, Component>();
		for(Component c : components) {
			addComponent(c.toString(), c);
		}
	}

	public void addComponent(String identifier, Component component) {
		component.setParent(this);
		components.put(identifier, component);
	}

	public void addChild(GameObject child) {
		child.setParent(this);
		children.add(child);
	}

	protected void update(Camera camera) {
		for (Component component : components.values()) {
			if (component instanceof Updatable) {
				((Updatable) component).update();
			}
			if (component instanceof Renderable) {
				((Renderable) component).render(camera);
			}
		}

		for (GameObject child : children) {
			child.update(camera);
		}
	}

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}

	public List<GameObject> getChildren() {
		return children;
	}

	public void setChildren(List<GameObject> children) {
		this.children = children;
	}

}
