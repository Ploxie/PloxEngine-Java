package org.ploxie.engine.model;

import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.model.materials.Material;

public class Model {

	protected VBO vbo;
	protected Material material;
		
	public Model(VBO vbo, Material material) {
		this.vbo = vbo;
		this.material = material;
	}
	
	public VBO getVBO() {
		return vbo;
	}
	
	public Material getMaterial() {
		return material;
	}
}
