package org.ploxie.engine.gui2.component;

import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;

public class ResizablePanel extends WidgetPanel{

	private static final int BAR_SIZE = 10;
	
	@Override
	public void initialize() {	
		super.initialize();			
		
		ButtonBase top = new ButtonBase();
		top.setScale(1.0f, 0.0f);
		top.setHeight(BAR_SIZE);
		top.setPivot(0.0f,1.0f);
		top.addOnMouseDownAction(getMoveTopAction());
		addChild(top);
		
		ButtonBase bottom = new ButtonBase();
		bottom.setPosition(0.0f, 1.0f);
		bottom.setScale(1.0f, 0.0f);
		bottom.setHeight(BAR_SIZE);	
		bottom.addOnMouseDownAction(getMoveBottomAction());
		addChild(bottom);
		
		ButtonBase left = new ButtonBase();
		left.setScale(0.0f, 1.0f);
		left.setWidth(BAR_SIZE);
		left.setPivot(1.0f,0.0f);	
		left.addOnMouseDownAction(getMoveLeftAction());
		addChild(left);
		
		ButtonBase right = new ButtonBase();
		right.setPosition(1.0f, 0.0f);
		right.setScale(0.0f, 1.0f);
		right.setWidth(BAR_SIZE);	
		right.addOnMouseDownAction(getMoveRightAction());
		addChild(right);
		
		ButtonBase topLeft = new ButtonBase();
		topLeft.setPivot(1.0f, 1.0f);
		topLeft.setSize(BAR_SIZE,BAR_SIZE);
		topLeft.addOnMouseDownAction(getMoveTopAction());
		topLeft.addOnMouseDownAction(getMoveLeftAction());
		addChild(topLeft);
		
		ButtonBase topRight = new ButtonBase();
		topRight.setPosition(1.0f, 0.0f);
		topRight.setPivot(0.0f, 1.0f);
		topRight.setSize(BAR_SIZE,BAR_SIZE);	
		topRight.addOnMouseDownAction(getMoveTopAction());
		topRight.addOnMouseDownAction(getMoveRightAction());
		addChild(topRight);
		
		ButtonBase bottomLeft = new ButtonBase();
		bottomLeft.setPosition(0.0f, 1.0f);
		bottomLeft.setPivot(1.0f, 0.0f);
		bottomLeft.setSize(BAR_SIZE,BAR_SIZE);	
		bottomLeft.addOnMouseDownAction(getMoveBottomAction());
		bottomLeft.addOnMouseDownAction(getMoveLeftAction());
		addChild(bottomLeft);
		
		ButtonBase bottomRight = new ButtonBase();
		bottomRight.setPosition(1.0f, 1.0f);
		bottomRight.setPivot(0.0f, 0.0f);
		bottomRight.setSize(BAR_SIZE,BAR_SIZE);
		bottomRight.addOnMouseDownAction(getMoveBottomAction());
		bottomRight.addOnMouseDownAction(getMoveRightAction());
		addChild(bottomRight);
	}
	
	protected MouseAction getMoveTopAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = getPosition();
				Vector2f scale = getScale();
				
				scale.y += position.y - point.y;
				position.y = point.y;
				
				setScale(scale);
				setPosition(position);
			}
			
		};
	}
	
	protected MouseAction getMoveLeftAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = getPosition();
				Vector2f scale = getScale();
				
				scale.x += position.x - point.x;
				position.x = point.x;
				
				setScale(scale);
				setPosition(position);
			}
			
		};
	}
	
	protected MouseAction getMoveBottomAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = getPosition();
				Vector2f scale = getScale();
				
				scale.y = point.y - position.y;
				
				setScale(scale);
			}
			
		};
	}
	
	protected MouseAction getMoveRightAction() {
		return new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				Vector2f position = getPosition();
				Vector2f scale = getScale();
				
				scale.x = point.x - position.x;
				
				setScale(scale);
			}
			
		};
	}
	
}
