package org.ploxie.scenes;
import org.ploxie.engine.Engine;
import org.ploxie.engine.buffers.primitives.Cube;
import org.ploxie.engine.camera.FPSCamera;
import org.ploxie.engine.scene.GameObject;
import org.ploxie.engine.scene.Scene;
import org.ploxie.engine.scene.components.PrimitiveRenderer;
import org.ploxie.math.vector.Vector3f;
import org.ploxie.terrain.Terrain;
import org.ploxie.terrain.TerrainGenerator;

public class TerrainScene extends Scene{

	private GameObject cameraController;
	
	@Override
	public void initialize() {
		
		FPSCamera camera = new FPSCamera(Engine.getCamera3D());
		camera.setPosition(new Vector3f(0,0,0));
		cameraController = new GameObject(camera);
		
		
		TerrainGenerator terrainGenerator = new TerrainGenerator();
		Terrain terrain = terrainGenerator.generateTerrain(100);
		terrain.setPosition(new Vector3f(0,-50,0));
		GameObject terrainObject = new GameObject(terrain);
				
		PrimitiveRenderer renderer= new PrimitiveRenderer(new Cube(1));
		renderer.setPosition(new Vector3f(0,0,-2));
		GameObject cube = new GameObject(renderer);
		
		
		addGameObject(cube);
		//addGameObject(terrainObject);
		
	}

}
