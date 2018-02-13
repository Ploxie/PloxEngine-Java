package org.ploxie.engine.gui2.component;

import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;

public class WidgetWindow extends ResizablePanel{
	
	private static final int TOP_BAR_SIZE = 25;
	
	private Vector2f relativePoint;
	
	private WidgetViewport topBarViewport = new WidgetViewport();
	private WidgetLabel title = new WidgetLabel("");
	
	private WidgetPanel panel = new WidgetPanel();
	
	@Override
	public void initialize() {	
		super.initialize();
		
		
		setBackgroundColor(new Color(0.5f, 0.5f, 0.5f, 1.0f));
		setBorderColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		
		
		
		internalAddChild(panel);
		internalAddChild(topBarViewport);
		
		WidgetButton topBar = new WidgetButton();
		topBar.setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, 0.8f));
		topBar.setBorderThickness(1);
		topBar.setScale(1,0);
		topBar.setHeight(TOP_BAR_SIZE);	
		topBar.setLockedHeight(true);
		topBar.addOnMousePressAction(getPressAction());
		topBar.addOnMouseDownAction(getMoveAction());
		topBar.addOnMouseReleaseAction(getReleaseAction());
		topBarViewport.addChild(topBar);
		
		panel.setBackgroundColor(Color.BLUE);
		panel.setPosition(0, TOP_BAR_SIZE);
		panel.setLockedY(true);
		panel.setScale(1.0f, (getWorldScale().y - topBar.getWorldScale().y));
		
		title.setPosition(0.5f, 0.5f);
		title.setPivot(0.5f, 0.5f);
		title.setScale(1.0f ,1.0f);
		topBar.addChild(title);
		
	}
	
	@Override
	public void render(Shader shader) {
		title.setText(""+System.currentTimeMillis());
		super.render(shader);
	}
	
	protected MouseAction getMoveAction() {
		return new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				setPosition(point.clone().subtract(relativePoint.clone()));
			}			
		};
	}
	
	protected MouseAction getPressAction() {
		return new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				relativePoint = getRelativePoint(point);
				System.out.println(relativePoint);
			}			
		};		
	}
	
	protected MouseAction getReleaseAction() {
		return new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				relativePoint = null;
			}			
		};		
	}
	
	private void internalAddChild(IWidget child) {
		super.addChild(child);
	}
	
}
