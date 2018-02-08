package org.ploxie.engine.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.ploxie.engine.Engine;
import org.ploxie.engine.display.Window;
import org.ploxie.engine.event.events.MouseKeyEvent;
import org.ploxie.engine.event.events.MouseMoveEvent;
import org.ploxie.utils.math.vector.Vector2i;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

import java.util.ArrayList;
import java.util.List;

public class Mouse {

	private List<Integer> pushedButtons = new ArrayList<Integer>();
	private List<Integer> buttonsHolding = new ArrayList<Integer>();
	private List<Integer> releasedButtons = new ArrayList<Integer>();
	
	private Vector2i mousePosition;
	private Vector2i lastPosition;
	private Vector2i mouseDelta;
	private float scrollOffset;
	
	private Window window;
	
	protected Mouse(Window window) {
		mousePosition = new Vector2i();
		mouseDelta = new Vector2i();
		this.window = window;
		
		glfwSetMouseButtonCallback(window.getHandle(), getMouseButtonCallback());
		
		glfwSetCursorPosCallback(window.getHandle(), getCursorPosCallback());
		
		glfwSetScrollCallback(window.getHandle(), getScrollCallback());		
	}

	protected void update() {
		setScrollOffset(0.0f);
		mouseDelta.x = 0;
		mouseDelta.y = 0;
		
		pushedButtons.clear();
		releasedButtons.clear();
		
		for(Integer i : buttonsHolding) {
			MouseKeyEvent event = new MouseKeyEvent(mousePosition,i, true, false);
    		Engine.getEventManager().dispatch(event);
		}
		
		
	}
	
	private void setScrollOffset(float offset) {
		scrollOffset = offset;
	}
	
	public Vector2i getMousePosition() {
		return mousePosition;
	}
	
	public float getScrollOffset() {
		return scrollOffset;
	}
	
	public Vector2i getMouseDelta() {
		return mouseDelta;	
	}
	
	private GLFWMouseButtonCallback getMouseButtonCallback() {
		return new GLFWMouseButtonCallback() {

            @Override
            public void invoke(long window, int button, int action, int mods) {
            	if (action == GLFW_PRESS){
                	if (!pushedButtons.contains(button)){
                		pushedButtons.add(button);
                		buttonsHolding.add(button);
                		
                		MouseKeyEvent event = new MouseKeyEvent(mousePosition, button);
                		Engine.getEventManager().dispatch(event);
                	}
                }
            	
            	 if (action == GLFW_RELEASE){
                 	releasedButtons.add(button);
                 	buttonsHolding.remove(new Integer(button));
                 	MouseKeyEvent event = new MouseKeyEvent(mousePosition,button, false, true);
            		Engine.getEventManager().dispatch(event);
                 }
            }
		};
	}
	
	private GLFWCursorPosCallback getCursorPosCallback() {
		return new GLFWCursorPosCallback() {

            @Override
            public void invoke(long window, double xpos, double ypos) {
            	//lastPosition = new Vector2i(mousePosition.x, mousePosition.y);
            	
            	mouseDelta.x = (int)xpos - mousePosition.x;
            	mouseDelta.y =  (int)ypos - mousePosition.y;
            	
            	
            	mousePosition.x = ((int) xpos);
            	mousePosition.y = ((int) ypos);
            	
            	
            	MouseMoveEvent event = new MouseMoveEvent(mousePosition, mouseDelta);
        		Engine.getEventManager().dispatch(event);
        		//lastPosition = mousePosition.clone();
            }

		};
	}

	private GLFWScrollCallback getScrollCallback() {
		return  new GLFWScrollCallback() {
			
			@Override
			public void invoke(long window, double xoffset, double yoffset) {
				setScrollOffset((float) yoffset);
			}
		};
	}
	
	public static final int BUTTON_1 = 0;
	public static final int BUTTON_2 = 1;
	public static final int BUTTON_3 = 2;
	public static final int BUTTON_4 = 3;
	public static final int BUTTON_5 = 4;
	public static final int BUTTON_6 = 5;
	public static final int BUTTON_7 = 6;
	public static final int BUTTON_8 = 7;
	public static final int BUTTON_LAST = 7;
	public static final int BUTTON_LEFT = 0;
	public static final int BUTTON_RIGHT = 1;
	public static final int BUTTON_MIDDLE = 2;
	
}
