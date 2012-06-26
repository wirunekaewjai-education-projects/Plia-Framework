package plia.framework.scene.group.geometry;

public class Quad
{
	private byte[] vertices = 
		{ 
			0, 0,
			0, 1,
			1, 1,
			1, 0,
		};
	
	private byte[] uv = 
		{ 
			0, 1,
			0, 0,
			1, 0,
			1, 1,
		};
	
	private byte[] indices = 
		{
			0,1,2,	0,2,3
		};
	
	private int UV_OFFSET = 8;
	
	private Quad()
	{
		
	}
	
	public byte[] getVertices()
	{
		return vertices;
	}
	
	public byte[] getUV()
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
	
	private static Quad quad = new Quad();
	public static Quad getQuad()
	{
		return quad;
	}
}
