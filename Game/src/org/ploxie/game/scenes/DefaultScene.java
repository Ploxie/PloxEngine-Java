package org.ploxie.game.scenes;

import java.awt.Font;

import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.primitives.Cube;
import org.ploxie.engine.buffers.primitives.Quad;
import org.ploxie.engine.font.Bitmap;
import org.ploxie.engine.font.TextMesh;
import org.ploxie.engine.font.TextShader;
import org.ploxie.engine.gui.GUIComponent;
import org.ploxie.engine.model.Model;
import org.ploxie.engine.model.materials.Material;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.scene.components.GUIRenderer;
import org.ploxie.engine.scene.components.Renderer;
import org.ploxie.engine.scene.components.Transform;
import org.ploxie.engine.scene.debug.DebugInfo;
import org.ploxie.engine.shaders.DefaultShader;
import org.ploxie.engine.shaders.PrimitiveShader;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.utils.Color;
import org.ploxie.utils.math.vector.Vector2f;
import org.ploxie.utils.math.vector.Vector3f;

public class DefaultScene extends Scene {

	@Override
	public void initialize() {

		GameObject text = new GameObject();
		text.addComponent(new DebugInfo());

		GameObject a = new GameObject();

		Model cube = new Model(new Cube(), new Material() {

			@Override
			public void applyShader(Shader shader) {
				shader.setUniform("bounds", new Vector3f(1, 1, 1));
			}

		});

		a.addComponent(new Renderer(cube, new PrimitiveShader()));
		// t1.setTranslation(500,0,0);
	}

}
