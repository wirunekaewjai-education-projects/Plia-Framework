package plia.debug;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

class DebugLineBox
{
	private float[] vertices = 
		{
			-0.5f, -0.5f, -0.5f,
			-0.5f,  0.5f, -0.5f,
			 0.5f,  0.5f, -0.5f,
			 0.5f, -0.5f, -0.5f,
			 
			-0.5f, -0.5f,  0.5f,
			-0.5f,  0.5f,  0.5f,
			 0.5f,  0.5f,  0.5f,
			 0.5f, -0.5f,  0.5f,
		};
	
	private int[] indices = 
		{
			0, 1, 1, 2, 2, 3, 3, 0, 
			4, 5, 5, 6, 6, 7, 7, 4,
			0, 4, 3, 7, 1, 5, 2, 6
		};
	
	private FloatBuffer verticesBuffer;
	private IntBuffer indicesBuffer;
	
	private DebugLineBox()
	{
		verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		verticesBuffer.put(vertices).position(0);
		
		indicesBuffer = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		indicesBuffer.put(indices).position(0);
	}
	
//	public float[] getVertices()
//	{
//		return vertices;
//	}
//	
//	public int[] getIndices()
//	{
//		return indices;
//	}
//	
//	public int getIndicesCount()
//	{
//		return indices.length;
//	}
	
	public FloatBuffer getVerticesBuffer()
	{
		return verticesBuffer;
	}
	
	public IntBuffer getIndicesBuffer()
	{
		return indicesBuffer;
	}
	
	private static DebugLineBox instance = new DebugLineBox();
	static DebugLineBox getInstance()
	{
		return instance;
	}
}
