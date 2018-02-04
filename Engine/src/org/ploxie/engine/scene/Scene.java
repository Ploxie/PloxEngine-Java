package org.ploxie.engine.scene;

import org.ploxie.engine.camera.Camera2D;
import org.ploxie.engine.camera.Camera3D;
import org.ploxie.engine.scene.components.ComponentManager;

public abstract class Scene{

	public abstract void initialize();
	
	private GameObject gui;
	private GameObject rootObject;
	
	public Scene() {
		gui = new GameObject();
		rootObject = new GameObject();
	}
	
	public void update() {
		ComponentManager.getInstance().update();
	}
	
	public void render() {
		ComponentManager.getInstance().render();
	}
	
	
	public void addGUIObject(GameObject object) {
		
	}
	
	public void addGameObject(GameObject object) {
		
	}
			
}
