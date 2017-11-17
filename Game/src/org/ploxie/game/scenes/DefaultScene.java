package org.ploxie.game.scenes;

import org.ploxie.engine.Engine;
import org.ploxie.engine.event.events.KeyEvent;
import org.ploxie.engine.event.events.KeyboardListener;
import org.ploxie.engine.event.events.MouseKeyEvent;
import org.ploxie.engine.event.events.MouseListener;
import org.ploxie.engine.event.events.MouseMoveEvent;
import org.ploxie.engine.gui.GUIPanel;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.utils.Color;
import org.ploxie.math.vector.Vector2f;
import org.ploxie.math.vector.Vector2i;
import org.ploxie.math.vector.Vector3f;

public class DefaultScene extends Scene{

	
	
	@Override
	public void initialize() {
		
		GameObject gui = new GameObject();
		gui.addComponent("GUIPanel", new GUIPanel(new Vector2f(50,50), new Color(1,0,1)));
		
		addGUIObject(gui);
			
	}

}
