package org.ploxie.engine.scene.debug;

import java.awt.Font;

import org.ploxie.engine.Engine;
import org.ploxie.engine.camera.Camera;
import org.ploxie.engine.event.events.KeyEvent;
import org.ploxie.engine.event.events.KeyboardListener;
import org.ploxie.engine.event.events.MouseKeyEvent;
import org.ploxie.engine.event.events.MouseListener;
import org.ploxie.engine.event.events.MouseMoveEvent;
import org.ploxie.engine.event.events.ObjectRenderedEvent;
import org.ploxie.engine.event.events.ObjectRenderedListener;
import org.ploxie.engine.font.Bitmap;
import org.ploxie.engine.font.TextMesh;
import org.ploxie.engine.font.TextShader;
import org.ploxie.engine.input.Input;
import org.ploxie.engine.input.Keyboard;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.decorations.Renderable;
import org.ploxie.engine.scene.decorations.Updatable;
import org.ploxie.math.vector.Vector2f;
import org.ploxie.math.vector.Vector2i;

public class DebugInfo extends Component implements Updatable, Renderable, KeyboardListener, ObjectRenderedListener{

	private TextMesh fpsText;
	private TextMesh position;
	private TextMesh forwardVectorText;
	private TextMesh polygonText;

	private long polygonsRendered;
	private boolean toggle = true;

	public DebugInfo() {

		Engine.getEventManager().register((KeyboardListener)this);
		Engine.getEventManager().register((ObjectRenderedListener)this);
		
		fpsText = new TextMesh(Bitmap.create(new Font("Arial", Font.BOLD, 16), false), new Vector2f(0, 0), null, new TextShader());
		position = new TextMesh(Bitmap.create(new Font("Arial", Font.BOLD, 16), false), new Vector2f(0, 20), null, new TextShader());
		forwardVectorText = new TextMesh(Bitmap.create(new Font("Arial", Font.BOLD, 16), false), new Vector2f(0, 40), null, new TextShader());
		polygonText = new TextMesh(Bitmap.create(new Font("Arial", Font.BOLD, 16), false), new Vector2f(0, 60), null, new TextShader());

	}

	@Override
	public void render(Camera camera) {
		//System.out.println(polygonsRendered);
		if (!toggle) {
			return;
		}
		

		fpsText.render("FPS: " + Engine.getFPSCounter().getFPS());
		position.render("Position: " + Engine.getCamera3D().getViewMatrix().getTranslation().toFormatedString());
		forwardVectorText.render("Forward: " + Engine.getCamera3D().getViewMatrix().getDirection().toFormatedString());
		polygonText.render("Polygons: " + polygonsRendered);
	}

	@Override
	public void keyPressed(KeyEvent event) {	
		if(event.getKey() == Keyboard.KEY_F1) {
			toggle = !toggle;
			System.out.println(toggle);
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
	}

	@Override
	public void keyHeld(KeyEvent event) {
		
	}
	
	

	@Override
	public void onObjectRender(ObjectRenderedEvent event) {
		// TODO Auto-generated method stub
		polygonsRendered += event.getVertexCount();
	}

	@Override
	public void update() {
		polygonsRendered = 0;
	}

}
