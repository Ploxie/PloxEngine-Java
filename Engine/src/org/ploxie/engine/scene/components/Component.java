package org.ploxie.engine.scene.components;

import org.ploxie.engine.scene.GameObject;

public abstract class Component {

	protected GameObject parent;
		
	public GameObject getParent() {
		return parent;
	}
	
	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	
	
}
