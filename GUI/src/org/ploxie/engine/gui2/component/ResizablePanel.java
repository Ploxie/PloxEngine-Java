package org.ploxie.engine.gui2.component;

import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;

public class ResizablePanel extends WidgetPanel {

	private static final int BAR_SIZE = 10;
	
	private IWidget target = this; 

	@Override
	public void initialize() {
		super.initialize();

		ButtonBase top = new ButtonBase();
		top.setAnchorScale(1.0f, 0.0f);
		top.setPivot(0.0f, 1.0f);
		top.setHeight(BAR_SIZE);
		top.addOnMouseDownAction(getMoveTopAction());
		addChild(top);

		ButtonBase bottom = new ButtonBase();
		bottom.setAnchorTranslation(0.0f, 1.0f);
		bottom.setAnchorScale(1.0f, 1.0f);
		bottom.setHeight(BAR_SIZE);
		bottom.addOnMouseDownAction(getMoveBottomAction());
		addChild(bottom);

		ButtonBase left = new ButtonBase();
		left.setAnchorScale(0.0f, 1.0f);
		left.setPivot(1.0f, 0.0f);
		left.setWidth(BAR_SIZE);
		left.addOnMouseDownAction(getMoveLeftAction());
		addChild(left);

		ButtonBase right = new ButtonBase();
		right.setAnchorTranslation(1.0f, 0.0f);
		right.setAnchorScale(1.0f, 1.0f);
		right.setWidth(BAR_SIZE);
		right.addOnMouseDownAction(getMoveRightAction());
		addChild(right);

		ButtonBase topLeft = new ButtonBase();
		topLeft.setAnchorScale(0.0f, 0.0f);
		topLeft.setPivot(1.0f, 1.0f);
		topLeft.setSize(BAR_SIZE, BAR_SIZE);		
		topLeft.addOnMouseDownAction(getMoveTopAction());
		topLeft.addOnMouseDownAction(getMoveLeftAction());
		addChild(topLeft);

		ButtonBase topRight = new ButtonBase();
		topRight.setAnchorTranslation(1.0f, 0.0f);
		topRight.setAnchorScale(1.0f, 0.0f);
		topRight.setPivot(0.0f, 1.0f);
		topRight.setSize(BAR_SIZE, BAR_SIZE);
		topRight.addOnMouseDownAction(getMoveTopAction());
		topRight.addOnMouseDownAction(getMoveRightAction());
		addChild(topRight);

		ButtonBase bottomLeft = new ButtonBase();
		bottomLeft.setAnchorTranslation(0.0f, 1.0f);
		bottomLeft.setAnchorScale(0.0f, 1.0f);
		bottomLeft.setPivot(1.0f, 0.0f);
		bottomLeft.setSize(BAR_SIZE, BAR_SIZE);
		bottomLeft.addOnMouseDownAction(getMoveBottomAction());
		bottomLeft.addOnMouseDownAction(getMoveLeftAction());
		addChild(bottomLeft);

		ButtonBase bottomRight = new ButtonBase();
		bottomRight.setAnchorTranslation(1.0f, 1.0f);
		bottomRight.setAnchorScale(1.0f, 1.0f);
		bottomRight.setSize(BAR_SIZE, BAR_SIZE);
		bottomRight.addOnMouseDownAction(getMoveBottomAction());
		bottomRight.addOnMouseDownAction(getMoveRightAction());
		addChild(bottomRight);
	}

	public void setTarget(IWidget target) {
		this.target = target;
	}
	
	protected MouseAction getMoveTopAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = target.getTranslation().clone();
				Vector2f scale = target.getScale().clone();
				Vector2f pivot = target.getPivot().clone();

				scale.y += (position.y - (pivot.y * scale.y)) - point.y;
				position.y = point.y+ (pivot.y * scale.y);
				
				target.setScale(scale);
				target.setTranslation(position);
			}

		};
	}

	protected MouseAction getMoveLeftAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = target.getTranslation().clone();
				Vector2f scale = target.getScale().clone();
				Vector2f pivot = target.getPivot().clone();
				
				scale.x += (position.x - (pivot.x * scale.x)) - point.x;
				position.x = point.x + (pivot.x * scale.x);	
				
				target.setScale(scale);
				target.setTranslation(position);
			}

		};
	}

	protected MouseAction getMoveBottomAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = target.getTranslation().clone();
				Vector2f scale = target.getScale().clone();
				Vector2f pivot = target.getPivot().clone();

				scale.y += point.y - (position.y + (pivot.y * scale.y));
				position.y = point.y - (pivot.y * scale.y);

				target.setScale(scale);
				target.setTranslation(position);
			}

		};
	}

	protected MouseAction getMoveRightAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = target.getTranslation().clone();
				Vector2f scale = target.getScale().clone();
				Vector2f pivot = target.getPivot().clone();

				scale.x += point.x - (position.x + (pivot.x * scale.x));
				position.x = point.x - (pivot.x * scale.x);

				target.setScale(scale);
				target.setTranslation(position);
			}

		};
	}

}
