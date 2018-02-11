package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class WidgetWindow extends ResizablePanel {

	private static final float TOP_BAR_SIZE_IN_PIXELS = 35.0f;
	private static final Color TOP_BAR_COLOR = new Color(0.2f, 0.2f, 0.2f, 0.8f);
	
	private Vector2f topBarRelativePoint = null;
	protected WidgetButton topBar = new WidgetButton() {

		@Override
		public Vector2f getSize() {
			Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
			Vector2f s = size.clone().multiply(parent.getSize());
			return new Vector2f(s.x, TOP_BAR_SIZE_IN_PIXELS / (float) screenDimensions.x);
		}
	};

	public WidgetWindow() {

		addChild(topBar);
		topBar.setAbsolutePosition(0, 1);
		topBar.setBackgroundColor(TOP_BAR_COLOR);
		topBar.addOnMousePressAction(new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				topBarRelativePoint = topBar.parent.getRelativePointOn(point);
			}

		});
		topBar.addOnMouseReleaseAction(new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				topBarRelativePoint = null;
			}

		});
		topBar.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				setPosition(point.clone().subtract(topBarRelativePoint.clone().multiply(topBar.parent.getSize())));
			}
		});
		

	}
}
