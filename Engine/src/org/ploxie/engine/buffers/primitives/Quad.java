package org.ploxie.engine.buffers.primitives;

import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.opengl.buffer.VBO;
import org.ploxie.utils.VertexStream;
import org.ploxie.utils.math.vector.Vector2f;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.util.Random;

public class Quad extends VBO{

	public Quad() {
		VertexStream stream = new VertexStream(6 * 2);
		
		stream.append(new Vector2f(-0.5f,-0.5f));
		stream.append(new Vector2f(-0.5f,0.5f));
		stream.append(new Vector2f(0.5f,-0.5f));
		stream.append(new Vector2f(0.5f,0.5f));
		
		int[] indices = new int[] {0,1,2, 2,1,3};
		
		Random rand = new Random();
		
		float[] colors = new float[indices.length];
		for(int i= 0 ; i < colors.length;i++) {
			colors[i] = Math.max(rand.nextFloat(),0.5f);
		}
		
		setIndexBufferData(indices, GL_STATIC_DRAW);
		setBufferData(BufferType.VERTEX, BufferUtils.createFlippedBuffer(stream.getBuffer()), 2, GL_STATIC_DRAW);
		setBufferData(BufferType.COLOR, BufferUtils.createFlippedBuffer(colors), 3, GL_STATIC_DRAW);
	}
	
}
