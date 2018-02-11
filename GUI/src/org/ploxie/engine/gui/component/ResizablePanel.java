package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.WidgetManager;
import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector2i;

public class ResizablePanel extends WidgetPanel{

	private static final float EDGE_SIZE_IN_PIXELS = 5.0f;

	private static final MouseAction EMPTY_MOUSE_ACTION = new MouseAction() {
		@Override
		public void execute(Vector2f point) {
		}
	};

	protected boolean isResizable = true;

	protected ClickableWidget top = new ClickableWidget() {
		@Override
		public Vector2f getSize() {
			Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
			Vector2f s = size.clone().multiply(parent.getSize());
			return new Vector2f(s.x, EDGE_SIZE_IN_PIXELS / (float) screenDimensions.y);
		}
	};
	
	protected ClickableWidget bottom = new ClickableWidget() {
		@Override
		public Vector2f getSize() {
			Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
			Vector2f s = size.clone().multiply(parent.getSize());
			return new Vector2f(s.x, EDGE_SIZE_IN_PIXELS / (float) screenDimensions.y);
		}
	};
	protected ClickableWidget left = new ClickableWidget() {
		@Override
		public Vector2f getSize() {
			Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
			Vector2f s = size.clone().multiply(parent.getSize());
			return new Vector2f(EDGE_SIZE_IN_PIXELS / (float) screenDimensions.x, s.y);
		}
	};
	protected ClickableWidget right = new ClickableWidget() {
		@Override
		public Vector2f getSize() {
			Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
			Vector2f s = size.clone().multiply(parent.getSize());
			return new Vector2f(EDGE_SIZE_IN_PIXELS / (float) screenDimensions.x, s.y);
		}
	};
	
	protected ClickableWidget topLeft = new ClickableWidget();
	protected ClickableWidget topRight = new ClickableWidget();
	protected ClickableWidget bottomLeft = new ClickableWidget();
	protected ClickableWidget bottomRight = new ClickableWidget();
	

	public ResizablePanel() {

		setDynamic(true);

		Vector2i screenDimensions = WidgetManager.getInstance().getViewport().getDimensions();
		
		addChild(top);
		top.setPivot(0, -1.0f);
		top.setSize(size.x, (float) 10 / (float) screenDimensions.y);
		top.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		top.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		top.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.y += pos.y - point.y;
				pos.y = point.y;
				setPosition(pos);
				setSize(size);
			}
		});

		addChild(bottom);
		bottom.setPosition(0, 1.0f);
		bottom.setSize(size.x, (float) 10 / (float) screenDimensions.y);
		bottom.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		bottom.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		bottom.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.y = point.y - pos.y;
				setSize(size);
			}
		});

		addChild(left);
		left.setPivot(-1.0f, 0.0f);
		left.setSize((float) 10 / (float) screenDimensions.x, size.y);
		left.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		left.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		left.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.x += pos.x - point.x;
				pos.x = point.x;
				setPosition(pos);
				setSize(size);
			}
		});

		addChild(right);
		right.setPosition(1.0f, 0);
		right.setSize((float) 10 / (float) screenDimensions.x, size.y);
		right.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		right.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		right.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.x = point.x - pos.x;
				setSize(size);
			}
		});

		addChild(topLeft);
		topLeft.setPosition(0.0f, 0);
		topLeft.setPivot(-1.0f, -1.0f);
		topLeft.setAbsoluteSize((int) EDGE_SIZE_IN_PIXELS, (int) EDGE_SIZE_IN_PIXELS);
		topLeft.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		topLeft.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		topLeft.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();

				size.y += pos.y - point.y;
				pos.y = point.y;

				size.x += pos.x - point.x;
				pos.x = point.x;
				setPosition(pos);
				setSize(size);
			}
		});

		addChild(topRight);
		topRight.setPosition(1.0f, 0);
		topRight.setPivot(0.0f, -1.0f);
		topRight.setAbsoluteSize((int) EDGE_SIZE_IN_PIXELS, (int) EDGE_SIZE_IN_PIXELS);
		topRight.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		topRight.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		topRight.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.x = point.x - pos.x;
				size.y += pos.y - point.y;
				pos.y = point.y;
				setPosition(pos);
				setSize(size);
			}
		});

		addChild(bottomLeft);
		bottomLeft.setPosition(0.0f, 1);
		bottomLeft.setPivot(-1.0f, 0.0f);
		bottomLeft.setAbsoluteSize((int) EDGE_SIZE_IN_PIXELS, (int) EDGE_SIZE_IN_PIXELS);
		bottomLeft.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		bottomLeft.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		bottomLeft.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.x += pos.x - point.x;
				pos.x = point.x;
				size.y = point.y - pos.y;
				setSize(size);
			}
		});

		addChild(bottomRight);
		bottomRight.setPosition(1.0f, 1);
		bottomRight.setPivot(0.0f, 0.0f);
		bottomRight.setAbsoluteSize((int) EDGE_SIZE_IN_PIXELS, (int) EDGE_SIZE_IN_PIXELS);
		bottomRight.addOnMousePressAction(EMPTY_MOUSE_ACTION);
		bottomRight.addOnMouseReleaseAction(EMPTY_MOUSE_ACTION);
		bottomRight.addOnMouseDownAction(new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				if (!isResizable()) {
					return;
				}
				Vector2f pos = getPosition();
				Vector2f size = getSize();
				size.x = point.x - pos.x;
				size.y = point.y - pos.y;
				setSize(size);
			}
		});

	}

	public boolean isResizable() {
		return isResizable;
	}

	public void setResizable(boolean resizable) {
		isResizable = resizable;
	}

	
}
