package org.ploxie.terrain;

import org.ploxie.engine.buffers.BufferType;
import org.ploxie.engine.buffers.VBO;
import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.engine.utils.Color;

import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TerrainGenerator {

	private static final int VERTEX_SIZE_BYTES = 12 + 4 + 4;// position + normal


	public TerrainGenerator() {
		
	}

	public Terrain generateTerrain(int gridSize) {
		float[][] heights = new float[gridSize][gridSize];
		Color[][] colours = new Color[gridSize][gridSize];
		
		for(int y = 0; y < gridSize; y++) {
			for(int x = 0; x < gridSize; x++) {
				heights[x][y] = 0;
			}
		}
		
		for(int y = 0; y < gridSize; y++) {
			for(int x = 0; x < gridSize; x++) {
				colours[x][y] = new Color(1,0,1);
			}
		}
		
		return createTerrain(heights, colours);
	}
	
	protected Terrain createTerrain(float[][] heights, Color[][] colours) {
		int vertexCount = calculateVertexCount(heights.length);
		//byte[] terrainData = createMeshData(heights, colours, vertexCount);
		FloatBuffer[] data = createMeshData2(heights, colours, vertexCount);
		int[] indices = IndexGenerator.generateIndexBuffer(heights.length);
		VBO vbo = new VBO();
		vbo.setIndexBufferData(indices, GL_STATIC_DRAW);
		vbo.setBufferData(BufferType.VERTEX, data[0], 3, GL_STATIC_DRAW);
		vbo.setBufferData(BufferType.COLOR, data[1], 3, GL_STATIC_DRAW);
		return new Terrain(vbo, indices.length);
	}

	private int calculateVertexCount(int vertexLength) {
		int bottom2Rows = 2 * vertexLength;
		int remainingRowCount = vertexLength - 2;
		int topCount = remainingRowCount * (vertexLength - 1) * 2;
		return topCount + bottom2Rows;
	}

	
	private FloatBuffer[] createMeshData2(float[][] heights, Color[][] colours, int vertexCount) {
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexCount*3);
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(vertexCount*3);
		GridTile[] lastRow = new GridTile[heights.length - 1];
		for (int row = 0; row < heights.length - 1; row++) {
			for (int col = 0; col < heights[row].length - 1; col++) {
				GridTile square = new GridTile(row, col, heights, colours);
				square.storeTopVertexData(vertexBuffer);
				square.storeTopColorData(colorBuffer);
				if (row == heights.length - 2) {
					lastRow[col] = square;
				}
			}
		}
		for (int i = 0; i < lastRow.length; i++) {
			lastRow[i].storeBottomVertexData(vertexBuffer);
			lastRow[i].storeBottomColorData(colorBuffer);
		}
		return new FloatBuffer[] {vertexBuffer, colorBuffer};
	}
	
/*	private byte[] createMeshData(float[][] heights, Color[][] colours, int vertexCount) {
		int byteSize = VERTEX_SIZE_BYTES * vertexCount;
		ByteBuffer buffer = ByteBuffer.allocate(byteSize).order(ByteOrder.nativeOrder());
		GridTile[] lastRow = new GridTile[heights.length - 1];
		for (int row = 0; row < heights.length - 1; row++) {
			for (int col = 0; col < heights[row].length - 1; col++) {
				GridTile square = new GridTile(row, col, heights, colours);
				square.storeSquareData(buffer);
				if (row == heights.length - 2) {
					lastRow[col] = square;
				}
			}
		}
		for (int i = 0; i < lastRow.length; i++) {
			lastRow[i].storeBottomRowData(buffer);
		}
		return buffer.array();
	}*/
	
}
