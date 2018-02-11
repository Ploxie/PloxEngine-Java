package org.ploxie.game;

import org.ploxie.engine.Engine;
import org.ploxie.engine.scene.Scene;
import org.ploxie.game.scenes.DefaultScene;
import org.ploxie.opengl.display.Window;
import org.ploxie.scenes.TerrainScene;

public class Game {

	private Engine engine;

	public void start() {
		engine = new Engine();
		
		Window window = Window.create(1024,  768, "PloxEngine");
		engine.init(window);
		
		Scene scene = new TerrainScene();
		engine.start(scene);
	}

}
