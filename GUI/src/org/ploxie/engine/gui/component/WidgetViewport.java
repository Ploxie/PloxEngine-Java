package org.ploxie.engine.gui.component;

import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetViewport extends WidgetBase {

	public void enable() {
		Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
		float top = getTop();
		float left = getLeft();
		float bottom = getBottom();
		float right = getRight();

		glEnable(GL_SCISSOR_TEST);

		int x = (int) ((left) * screenDimensions.x);
		int y = (int) ((top) * screenDimensions.y);
		int width = (int) ((right - left) * screenDimensions.x)+1;
		int height = (int) ((bottom - top) * screenDimensions.y)+1;

		glScissor(x, screenDimensions.y - (y + height), width, height);
	}

	public void disable() {
		glDisable(GL_SCISSOR_TEST);
	}

}
