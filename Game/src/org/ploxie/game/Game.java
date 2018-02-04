package org.ploxie.game;

import org.ploxie.engine.Engine;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.display.Window;
import org.ploxie.game.scenes.DefaultScene;

public class Game {

	private Engine engine;

	public void start() {
		engine = new Engine();
		
		Window window = Window.create(1024,  768, "PloxEngine");
		engine.init(window);
		
		Scene scene = new DefaultScene();
		engine.start(scene);
	}

}
