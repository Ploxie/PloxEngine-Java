package org.ploxie.game.scenes;

import org.ploxie.engine.buffers.primitives.Quad;
import org.ploxie.engine.gui.GUIComponent;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.scene.components.Renderer;
import org.ploxie.engine.scene.components.Transform;
import org.ploxie.engine.shaders.DefaultShader;
import org.ploxie.engine.shaders.PrimitiveShader;
import org.ploxie.engine.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;

public class DefaultScene extends Scene{

	
	
	@Override
	public void initialize() {
		
		GameObject gui = new GameObject();
		gui.addComponent(new Renderer(new Quad(), new PrimitiveShader()));
		
		addGUIObject(gui);
			
	}

}
