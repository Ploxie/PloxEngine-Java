package org.ploxie.engine.buffers.primitives;

import org.ploxie.engine.buffers.BufferType;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.engine.utils.VertexStream;
import org.ploxie.utils.math.vector.Vector2f;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Quad extends VBO{

	public Quad() {
		VertexStream stream = new VertexStream(6 * 2);
		
		stream.append(new Vector2f(0,0));
		stream.append(new Vector2f(0,1));
		stream.append(new Vector2f(1,0));
		stream.append(new Vector2f(1,1));
		
		int[] indices = new int[] {0,1,2, 2,1,3};
		
		setIndexBufferData(indices, GL_STATIC_DRAW);
		setBufferData(BufferType.VERTEX, BufferUtils.createFlippedBuffer(stream.getBuffer()), 2, GL_STATIC_DRAW);
	}
	
}
