package org.ploxie.terrain;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.ploxie.engine.utils.BufferUtils;
import org.ploxie.engine.utils.Color;
import org.ploxie.utils.DataStoring;
import org.ploxie.utils.math.vector.Vector3f;

public class GridTile {

	private final int row;
	private final int col;
	private final int lastIndex;
	private final Vector3f[] positions;
	private final Color[] colors;
	private final Vector3f normalLeft;
	private final Vector3f normalRight;
	
	public GridTile(int row, int col, float[][] heights, Color[][] colors) {
		this.positions = calculateCornerPositions(col, row, heights);
		this.colors = calculateCornerColors(col, row, colors);
		this.lastIndex = heights.length-2;
		this.row = row;
		this.col = col;
		boolean rightHanded = col% 2 != row % 2;
		this.normalLeft = calcNormal(positions[0], positions[1], positions[rightHanded  ? 3 : 2]);
		this.normalRight = calcNormal(positions[2], positions[rightHanded ? 0 : 1], positions[3]);
	}
	
	public void storeTopVertexData(FloatBuffer buffer) {
		buffer.put(positions[0].x);
		buffer.put(positions[0].y);
		buffer.put(positions[0].z);
		if (row != lastIndex || col == lastIndex) {
			buffer.put(positions[2].x);
			buffer.put(positions[2].y);
			buffer.put(positions[2].z);
		}
	}
	
	public void storeTopColorData(FloatBuffer buffer) {
		buffer.put(colors[0].getR());
		buffer.put(colors[0].getG());
		buffer.put(colors[0].getB());
		if (row != lastIndex || col == lastIndex) {
			buffer.put(colors[2].getR());
			buffer.put(colors[2].getG());
			buffer.put(colors[2].getB());
		}
	}
	
	public void storeBottomVertexData(FloatBuffer buffer) {
		if (col == 0) {
			buffer.put(positions[1].x);
			buffer.put(positions[1].y);
			buffer.put(positions[1].z);
		}
		buffer.put(positions[3].x);
		buffer.put(positions[3].y);
		buffer.put(positions[3].z);
	}
	
	public void storeBottomColorData(FloatBuffer buffer) {
		if (col == 0) {
			buffer.put(colors[1].getR());
			buffer.put(colors[1].getG());
			buffer.put(colors[1].getB());
		}
		buffer.put(colors[3].getR());
		buffer.put(colors[3].getG());
		buffer.put(colors[3].getB());
	}
	
	/*public void storeSquareData(ByteBuffer buffer) {
		storeTopLeftVertex(buffer);
		if (row != lastIndex || col == lastIndex) {
			storeTopRightVertex(buffer);
		}
	}
	
	public void storeBottomRowData(ByteBuffer buffer) {
		if (col == 0) {
			storeBottomLeftVertex(buffer);
		}
		storeBottomRightVertex(buffer);
	}*/
	
	private Color[] calculateCornerColors(int col, int row, Color[][] colors) {
		Color[] cornerCols = new Color[4];
		cornerCols[0] = colors[row][col];
		cornerCols[1] = colors[row + 1 ][col];
		cornerCols[2] = colors[row][col + 1];
		cornerCols[3] = colors[row+1][col+1];
		return cornerCols;
	}
	
	private Vector3f[] calculateCornerPositions(int col, int row, float[][] heights) {
		Vector3f[] vertices = new Vector3f[4];
		vertices[0] = new Vector3f(col, heights[row][col], row);
		vertices[1] = new Vector3f(col, heights[row+1][col], row+1);
		vertices[2] = new Vector3f(col+1, heights[row][col+1], row);
		vertices[3] = new Vector3f(col+1, heights[row+1][col+1], row+1);
		return vertices;		
	}
	
	private void storeTopLeftVertex(ByteBuffer buffer) {
		DataStoring.packVertexData(positions[0], normalLeft, colors[0], buffer);
	}
	
	private void storeTopRightVertex(ByteBuffer buffer) {
		DataStoring.packVertexData(positions[2], normalRight, colors[2], buffer);
	}
	
	private void storeBottomLeftVertex(ByteBuffer buffer) {
		DataStoring.packVertexData(positions[1], normalLeft, colors[1], buffer);
	}
	
	private void storeBottomRightVertex(ByteBuffer buffer) {
		DataStoring.packVertexData(positions[3], normalLeft, colors[3], buffer);
	}
	
	private Vector3f calcNormal(Vector3f vertex0, Vector3f vertex1, Vector3f vertex2) {
		Vector3f tangentA = vertex1.subtract(vertex0);
		Vector3f tangentB = vertex2.subtract(vertex0);
		Vector3f normal = tangentA.cross(tangentB);
		normal.normalize();
		return normal;
	}
	
	
}
