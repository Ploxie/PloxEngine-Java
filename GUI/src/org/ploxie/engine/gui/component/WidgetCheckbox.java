package org.ploxie.engine.gui.component;

import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;

public class WidgetCheckbox extends WidgetBase{

	private static final int DEFAULT_SIZE = 12;	
	private static final Color CHECKED_COLOR = new Color(1,1,1,1);
	private static final Color UNCHECKED_COLOR = new Color(0.2f,0.2f,0.2f,1);
	
	private boolean checked;
	private WidgetButton button;
	private WidgetLabel label;
	
	public WidgetCheckbox() {
		setAbsoluteSize(DEFAULT_SIZE,DEFAULT_SIZE);
		
		
		button = new WidgetButton();
		button.addOnMousePressAction(new MouseAction() {

			@Override
			public void execute(Vector2f point) {
				setChecked(!checked);
			}
			
		});
		
		label = new WidgetLabel("label");
		label.setBackgroundColor(Color.BLUE);
		
		setChecked(false);
		addChild(button);
		addChild(label);
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
		button.setBackgroundColor(checked == true ? CHECKED_COLOR : UNCHECKED_COLOR);
	}
	
	
	
}
