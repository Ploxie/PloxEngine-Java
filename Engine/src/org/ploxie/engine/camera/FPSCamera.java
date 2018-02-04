package org.ploxie.engine.camera;

import org.ploxie.engine.Engine;
import org.ploxie.engine.event.events.KeyEvent;
import org.ploxie.engine.event.events.KeyboardListener;
import org.ploxie.engine.event.events.MouseKeyEvent;
import org.ploxie.engine.event.events.MouseListener;
import org.ploxie.engine.event.events.MouseMoveEvent;
import org.ploxie.engine.input.Keyboard;
import org.ploxie.engine.input.Mouse;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.decorations.Locatable;
import org.ploxie.utils.math.matrix.Matrix4f;
import org.ploxie.utils.math.vector.Vector2i;
import org.ploxie.utils.math.vector.Vector3f;

public class FPSCamera extends Component implements Locatable, KeyboardListener, MouseListener {

	private Camera3D camera;
	private Vector3f speed;

	private boolean mouseDown;

	private Vector3f position;
	private float pitch;
	private float yaw;

	public FPSCamera(Camera3D camera) {
		this.camera = camera;
		this.speed = new Vector3f(1, 1, 1);

		Engine.getEventManager().register((KeyboardListener) this);
		Engine.getEventManager().register((MouseListener) this);

		 //pitch -= 90;
	}

	@Override
	public void keyHeld(KeyEvent event) {

		float delta = Engine.getFPSCounter().getDelta();
		Vector3f forward =  camera.getViewMatrix().getDirection();
		Vector3f left =  camera.getViewMatrix().getLeft();

		if (event.getKey() == (Keyboard.KEY_W)) {
			position.add(forward.copy().multiply(-speed.z * delta));
		}

		if (event.getKey() == (Keyboard.KEY_S)) {
			position.add(forward.copy().multiply(speed.z * delta));
		}

		if (event.getKey() == (Keyboard.KEY_A)) {
			position.add(left.copy().multiply(-speed.x * delta));
		}

		if (event.getKey() == (Keyboard.KEY_D)) {
			position.add(left.copy().multiply(speed.x * delta));
		}

		if (event.getKey() == (Keyboard.KEY_SPACE)) {
			position.add(new Vector3f(0,1,0).multiply(speed.x * delta));
		}
		
		if (event.getKey() == (Keyboard.KEY_LEFT_SHIFT)) {
			position.add(new Vector3f(0,-1,0).multiply(speed.x * delta));
		}
		
		if (event.getKey() == (Keyboard.KEY_ENTER)) {
			camera.viewMatrix.makeLookAtViewMatrix(position, new Vector3f(0,0,0), new Vector3f(0,1,0));
			position = camera.viewMatrix.getTranslation();
		}else {
		}
		
		setPosition(position);

		
		

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		// TODO Auto-generated method stub

		if (!mouseDown) {
			return;
		}

		float delta = Engine.getFPSCounter().getDelta();
		Vector2i mouseDelta = event.getDelta();

		yaw -= mouseDelta.x * 360 * delta;
		pitch -= mouseDelta.y * 360 * delta;

		setPosition(position);
	}

	@Override
	public void setPosition(Vector3f position) {
		this.position = position;
		camera.viewMatrix.makeFPSViewMatrix(position, pitch, yaw);
	}


	@Override
	public void keyPressed(KeyEvent event) {

	}

	@Override
	public Matrix4f getTransformation() {
		return camera.viewMatrix;
	}

	@Override
	public void onMouseHeld(MouseKeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getButton() == Mouse.BUTTON_1) {
			mouseDown = true;
		}

	}

	@Override
	public void onMouseRelease(MouseKeyEvent event) {
		if (event.getButton() == Mouse.BUTTON_1) {
			mouseDown = false;
		}
	}

	@Override
	public void onMouseDown(MouseKeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub

	}

}
