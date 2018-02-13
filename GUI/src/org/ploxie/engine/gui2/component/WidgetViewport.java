package org.ploxie.engine.gui2.component;

import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glScissor;

import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetViewport extends WidgetBase{

	@Override
	public void render(Shader shader) {
	
		Vector2i vp = getManager().getViewport().getDimensions();		
		Vector2f screenDimensions = new Vector2f(vp.x, vp.y);
		
		Matrix4f world = getWorldMatrix();
		Vector2f position = world.getTranslation().xy().clone().multiply(screenDimensions);
		Vector2f scale =  world.getScale().xy().clone().multiply(screenDimensions);
		
		glEnable(GL_SCISSOR_TEST);

		int x = (int) ((position.x));
		int y = (int) ((position.y));
		int width = (int) ((scale.x));
		int height = (int) ((scale.y) );

		//glScissor(x, vp.y - (y + height), width, height);
		
		super.render(shader);
		
		glDisable(GL_SCISSOR_TEST);
	}
	
}
