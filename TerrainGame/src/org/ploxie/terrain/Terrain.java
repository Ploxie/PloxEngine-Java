package org.ploxie.terrain;

import org.ploxie.engine.buffers.BufferType;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.utils.Color;
import org.ploxie.utils.VertexStream;
import org.ploxie.utils.math.vector.Vector3f;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.FloatBuffer;
import java.util.Random;

public class Terrain extends VBO{

		


	public void generateTerrain(int width, int height) {
		VertexStream stream = new VertexStream(width * height * 3 * 4);
		
		for(int y = 0; y < height+1;y++) {
			for(int x = 0; x < width+1;x++) {				
				stream.append(new Vector3f(x,0,y));	
				//stream.append(new Vector3f(x+1,0,y));	
				//stream.append(new Vector3f(x,0,y+1));	
			//	stream.append(new Vector3f(x+1,0,y+1));	
				
				//System.out.println(new Vector3f(x,0,y));
				//System.out.println(new Vector3f(x+1,0,y));
				//System.out.println(new Vector3f(x,0,y+1));
				//System.out.println(new Vector3f(x+1,0,y+1));
			}
		}
		
		int amountOfVertices = (width+1)*(height+1);
		
		int[] indices = new int[width * height * 6];
		int index = 0;
		for(int i = 0 ; i < indices.length;i+=6) {
			indices[i] = index;
			indices[i+1] = index+1;
			indices[i+2] = (index+width+1);
			
			indices[i+3] = (index+width+1);
			indices[i+4] = index+1;
			indices[i+5] = (index+width+2);
			System.out.println(index % (width+1));
			//System.out.println(index);
			//if(index % (width+1) == 2) {
				//index+=2;
			//}else {
				index++;
			//}
			
		}
		
		for(int i = 0; i < indices.length;i++) {
			//System.out.println(indices[i]);
		}
		
		Random rand = new Random();
		
		float[] colors = new float[indices.length];
		for(int i= 0 ; i < colors.length;i++) {
			colors[i] = Math.max(rand.nextFloat(),0.5f);
		}
		
		
		setIndexBufferData(indices, GL_STATIC_DRAW);
		setBufferData(BufferType.VERTEX, BufferUtils.createFlippedBuffer(stream.getBuffer()), 3, GL_STATIC_DRAW);
		setBufferData(BufferType.COLOR, BufferUtils.createFlippedBuffer(colors), 3, GL_STATIC_DRAW);
		
	
		
	}
	
	
	
}
