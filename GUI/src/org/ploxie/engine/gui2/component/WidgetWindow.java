package org.ploxie.engine.gui2.component;

import org.ploxie.engine.gui.event.actions.MouseAction;
import org.ploxie.opengl.shader.Shader;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;

public class WidgetWindow extends WidgetPanel{
	
	private static final int TOP_BAR_SIZE = 25;
	
	private Vector2f relativePoint;
	
	private WidgetViewport topBarViewport = new WidgetViewport();
	private WidgetLabel title = new WidgetLabel("");
	
	private ResizablePanel resizable = new ResizablePanel();
	
	@Override
	public void initialize() {	
		super.initialize();		
				
		setBackgroundColor(new Color(0.5f, 0.5f, 0.5f, 1.0f));						
		
		//setHeight(getHeight()-TOP_BAR_SIZE);
		
		WidgetButton topBar = new WidgetButton();
		topBar.setBackgroundColor(new Color(0.2f, 0.2f, 0.2f, 0.8f));
		topBar.setBorderThickness(1);
		topBar.setBorderColor(Color.BLACK);
		topBar.setAnchorScale(1.0f, 0.0f);
		topBar.setPivot(0.0f, 1.0f);
		topBar.setHeight(TOP_BAR_SIZE);	
		topBar.addOnMousePressAction(getPressAction());
		topBar.addOnMouseDownAction(getMoveAction());
		topBar.addOnMouseReleaseAction(getReleaseAction());
		//addChild(topBar);
		
		addChild(resizable);
		resizable.setBackgroundColor(new Color(1,0,0,0.5f));
		//System.out.println(resizable.getTop());
		resizable.setTop(-TOP_BAR_SIZE);
		//System.out.println(resizable.getTop());
		//resizable.setPosition(0, -TOP_BAR_SIZE);
		//resizable.setHeight(getHeight()+TOP_BAR_SIZE);
		//resizable.setHeight(getHeight()+TOP_BAR_SIZE);
		
		
		resizable.setTarget(this);
		
		//panel.setBackgroundColor(Color.RED);
		//addChild(panel);
		
		//panel.setBackgroundColor(new Color(0.5f, 0.5f, 0.5f, 1.0f));
		//addChild(panel);
		
		/*internalAddChild(panel);
		panel.setBackgroundColor(Color.BLUE);
		panel.setAnchorPosition(0.0f, TOP_BAR_SIZE);
		panel.setAnchorScale(1.0f, 1.0f);*/
		
		
		/*title.setPivot(0.5f, 0.5f);
		title.setAnchorPosition(0.5f, 0.5f);
		title.setAnchorScale(0.5f, 0.5f);
		topBar.addChild(title);	*/	
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
				setTranslation(point.clone().subtract(relativePoint.clone()));
			}			
		};
	}
	
	protected MouseAction getPressAction() {
		return new MouseAction() {
			@Override
			public void execute(Vector2f point) {
				relativePoint = getRelativePoint(point);
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
	
	
}
