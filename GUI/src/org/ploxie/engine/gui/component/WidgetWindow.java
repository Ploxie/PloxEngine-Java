package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.engine.gui.event.WidgetEvent;
import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.engine.gui.shader.Shader;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetWindow extends WidgetPanel {

	protected ClickableWidget top = new ClickableWidget();

	public WidgetWindow() {
		setDynamic(true);

		Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();

		addChild(top);
		top.setPivot(0, -0.5f);
		//top.setSize(1.0f, 1.0f);
		top.setSize(size.x, (float)40  / (float)screenDimensions.y);

		
		top.addOnMouseDownAction(new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f pos = getPosition();
				pos.y = point.y;
				setPosition(pos);
				
			}

		});
	}
	
	@Override
	public void render(Shader shader) {
		//top.render(shader);
		super.render(shader);
		
		System.out.println(getPosition());
	}
}
