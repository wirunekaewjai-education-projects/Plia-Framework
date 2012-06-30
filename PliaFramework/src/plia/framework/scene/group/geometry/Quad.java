package plia.framework.scene.group.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public final class Quad
{
	private float[] vertices = 
		{ 
			0, 0,
			0, 1,
			1, 1,
			1, 0,
		};
	
	private float[] uv = 
		{ 
			0, 0,
			0, 1,
			1, 1,
			1, 0,
		};
	
	private byte[] indices = 
		{
			0,1,2,	0,2,3
		};
	
	private int UV_OFFSET = 8;
	
	private Quad()
	{
		
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public float[] getUV()
	{
		return uv;
	}
	
	public byte[] getIndices()
	{
		return indices;
	}
	
	public int getUVOffset()
	{
		return UV_OFFSET;
	}
	
	public int getIndicesCount()
	{
		return indices.length;
	}
	
	private static Quad instance = new Quad();
	public static Quad getInstance()
	{
		return instance;
	}
	
	private static FloatBuffer sb;
	private static FloatBuffer vb;
	private static FloatBuffer uvb;
	private static ByteBuffer ib;
	
	public static void createBuffer()
	{
		sb = ByteBuffer.allocateDirect( 32 ).order(ByteOrder.nativeOrder()).asFloatBuffer();
		sb.position(0);
		
		vb = ByteBuffer.allocateDirect( 32 ).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vb.put(instance.vertices).position(0);
		
		uvb = ByteBuffer.allocateDirect( 32 ).order(ByteOrder.nativeOrder()).asFloatBuffer();
		uvb.put(instance.uv).position(0);
		
		ib = ByteBuffer.allocateDirect( 6 ).order(ByteOrder.nativeOrder());
		ib.put(instance.indices).position(0);
	}
	
	public static FloatBuffer getSpriteUVBuffer()
	{
		return sb;
	}
	
	public static FloatBuffer getVertexBuffer()
	{
		return vb;
	}
	
	public static FloatBuffer getUVBuffer()
	{
		return uvb;
	}
	
	public static ByteBuffer getIndicesBuffer()
	{
		return ib;
	}
}
