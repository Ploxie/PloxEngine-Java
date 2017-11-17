package org.ploxie.engine.scene;

import org.ploxie.engine.camera.Camera2D;
import org.ploxie.engine.camera.Camera3D;

public abstract class Scene{

	public abstract void initialize();
	
	private GameObject gui;
	private GameObject rootObject;
	
	public Scene() {
		gui = new GameObject();
		rootObject = new GameObject();
	}
	
	public void update2D(Camera2D camera) {
		gui.update(camera);
	}
	
	public void update3D(Camera3D camera) {
		rootObject.update(camera);
	}
	
	public void addGUIObject(GameObject object) {
		gui.addChild(object);
	}
	
	public void addGameObject(GameObject object) {
		rootObject.addChild(object);
	}
			
}
