package org.ploxie.engine.utils;

public class FPSCounter {

	private int fpsCounter;
	private int fps = Integer.MAX_VALUE;
	private long lastFPS = System.currentTimeMillis();
	private long lastFrame = System.currentTimeMillis();

	/**
	 * Updates average FPS every 1000 MS
	 */
	public void update() {
		if ((System.currentTimeMillis() - lastFPS) > 1000) {
			fps = fpsCounter;
			fpsCounter = 0;
			lastFPS = System.currentTimeMillis();
		}
		fpsCounter++;
	}

	public float getDelta() {
		return 1.0f / fps;
	}

	public int getFPS() {
		return fps;
	}
	
}
