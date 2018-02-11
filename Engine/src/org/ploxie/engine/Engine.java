package org.ploxie.engine;

import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.camera.Camera2D;
import org.ploxie.engine.camera.Camera3D;
import org.ploxie.engine.display.Window;
import org.ploxie.engine.event.EventManager;
import org.ploxie.engine.input.Input;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.utils.FPSCounter;
import org.ploxie.utils.Viewport;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Engine {

	public static final boolean DEBUG = true;
	private static Engine instance;

	private Window window;

	private boolean initialized;
	private boolean isRunning;

	private Input input;
	private RenderingEngine renderingEngine;
	private FPSCounter fpsCounter;
	private Viewport viewport;
	private Camera2D camera2D;
	private Camera3D camera3D;
	private EventManager eventManager;

	public Engine() {
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		
		if(instance != null) {
			throw new RuntimeException("Engine is already instantiated");
		}
		
		instance = this;
	}

	public void init(Window window) {

		try {
			this.window = window;

			input = Input.create(window);

			renderingEngine = new RenderingEngine();
			renderingEngine.init(window);
			
			fpsCounter = new FPSCounter();
			viewport = new Viewport(0, window.getWidth(), 0, window.getHeight());
			camera2D = new Camera2D();
			camera3D = new Camera3D(45,0.01f, 2500, 1024.0f / 768.0f);
			eventManager = new EventManager();
		} catch (Exception e) {
			if (DEBUG) {
				e.printStackTrace();
				System.exit(1);
			}
		}

		initialized = true;
	}

	public void start(Scene scene) {
		if (!initialized) {
			throw new RuntimeException("Engine is not initialized");
		}
		if (isRunning) {
			return;
		}

		scene.initialize();
		renderingEngine.startScene(scene);

		isRunning = true;
		run();
	}

	private void stop() {
		if (!isRunning) {
			return;
		}

		isRunning = false;
	}

	private void run() {

		while (isRunning) {
			if (window.isCloseRequested()) {
				stop();
			}

				
				
				
				
				fpsCounter.update();
				

				renderingEngine.update();
				input.update();

		}
	}
		
	public static FPSCounter getFPSCounter() {
		return instance.fpsCounter;
	}
	
	public static Viewport getViewport() {
		return instance.viewport;
	}

	public static Camera2D getCamera2D() {
		return instance.camera2D;
	}
	
	public static Camera3D getCamera3D() {
		return instance.camera3D;
	}
	
	public static EventManager getEventManager() {
		return instance.eventManager;
	}
	
	public static RenderingEngine getRenderingEngine() {
		return instance.renderingEngine;
	}

}
