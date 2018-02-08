package org.ploxie.scenes;
import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.primitives.Cube;
import org.ploxie.engine.camera.FPSCamera;
import org.ploxie.engine.model.Model;
import org.ploxie.engine.model.materials.Material;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.scene.components.Renderer;
import org.ploxie.engine.shaders.Shader;
import org.ploxie.terrain.Terrain;
import org.ploxie.terrain.TerrainShader;
import org.ploxie.terrain.Terrain;

public class TerrainScene extends Scene{

	
	@Override
	public void initialize() {
		
		
		
		Terrain terrain = new Terrain();
		terrain.generateTerrain(2, 2);
		
		Model terrainModel = new Model(terrain, new Material() {

			@Override
			public void applyShader(Shader shader) {
				
			}
			
		});
		
		GameObject object = new GameObject();
		object.addComponent(new Renderer(terrainModel, new TerrainShader()));
		
		
		
				
	}

}
