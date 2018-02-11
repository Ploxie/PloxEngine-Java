package org.ploxie.game.scenes;

import org.ploxie.engine.buffers.primitives.Cube;
import org.ploxie.engine.model.Model;
import org.ploxie.engine.model.materials.Material;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.scene.components.Renderer;
import org.ploxie.engine.scene.debug.DebugInfo;
import org.ploxie.engine.shaders.PrimitiveShader;
import org.ploxie.engine.shaders.Shader;
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
	}

}
