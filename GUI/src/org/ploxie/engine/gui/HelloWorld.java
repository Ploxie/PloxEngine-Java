package org.ploxie.engine.gui;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.ploxie.utils.Viewport;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.engine.gui.event.ClickEvent;
import org.ploxie.engine.gui.event.MouseDownEvent;
import org.ploxie.engine.gui2.WidgetManager;
import org.ploxie.engine.gui2.component.WidgetPanel;
import org.ploxie.engine.gui2.component.WidgetWindow;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private long window;

	private WidgetManager gui;
	
	private Vector2f mousePos;
	
	private boolean mouseDown = false;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
		
		glfwSetCursorPosCallback(window,  new GLFWCursorPosCallback() {

            @Override
            public void invoke(long window, double xpos, double ypos) {
            	mousePos = new Vector2f((float)xpos / WIDTH, (float)ypos / HEIGHT);
            	if(mouseDown) {
            		MouseDownEvent event = new MouseDownEvent(mousePos);
                	gui.handleEvent(event);
            	}
            	
            }

		});
		
		glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {

            @Override
            public void invoke(long window, int button, int action, int mods) {
            	ClickEvent event = new ClickEvent(mousePos, action == GLFW_PRESS);
            	gui.handleEvent(event);
            	
            	if(action == GLFW_RELEASE) {
            		mouseDown = false;
            	}
            	if(action == GLFW_PRESS) {
            		mouseDown = true;
            	}
            	            	
            	
            }
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);
			

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
	
	}

	
	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.5f, 0.5f, 0.55f, 0f);
		
		
		gui = WidgetManager.create(new Viewport(0, WIDTH, 0, HEIGHT));
		


		WidgetWindow panel = new WidgetWindow();
		panel.setPosition(0.25f, 0.25f);
		panel.setScale(0.5f,0.5f);
		
		//panel.setBackgroundColor(new Color(0.5f,0.5f,0.5f,1f));
		
		
		//WidgetCheckbox checkbox = new WidgetCheckbox();
		//checkbox.setPosition(0.5f,0.5f);
		
		
		//WidgetLabel label = new WidgetLabel("Hallasdasdasdasd");
		//label.setPosition(0.5f, 0.5f);
		
		gui.addChild(panel);
		
		//panel.addChild(checkbox);
		//panel.addChild(panel2);
		//System.out.println(panel2.getPosition());
		
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			gui.render();
			
			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new HelloWorld().run();
	}

}