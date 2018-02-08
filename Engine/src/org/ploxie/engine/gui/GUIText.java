package org.ploxie.engine.gui;

import java.awt.Font;

import org.ploxie.engine.font.Bitmap;
import org.ploxie.engine.font.TextMesh;
import org.ploxie.engine.font.TextShader;
import org.ploxie.engine.scene.components.Component;
import org.ploxie.engine.scene.components.GUIRenderer;

public class GUIText extends Component {

	protected TextMesh textMesh;

	public GUIText(Font font) {
		textMesh = new TextMesh(Bitmap.create(font, false), null);
	}

	public void setText(String text) {
		textMesh.setText(text);
	}

	@Override
	public void awake() {

		getGameObject().addComponent(new GUIRenderer(textMesh, new TextShader()));

	}

}
