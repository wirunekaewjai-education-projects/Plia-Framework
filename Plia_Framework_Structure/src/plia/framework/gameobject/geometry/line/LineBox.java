package plia.framework.gameobject.geometry.line;

public class LineBox
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
	
	private byte[] indices = 
		{
			0, 1, 1, 2, 2, 3, 3, 0, 
			4, 5, 5, 6, 6, 7, 7, 4,
			0, 4, 3, 7, 1, 5, 2, 6
		};
	
	private LineBox()
	{
		
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public byte[] getIndices()
	{
		return indices;
	}
	
	public int getIndicesCount()
	{
		return indices.length;
	}
	
	private static LineBox box = new LineBox();
	public static LineBox getBox()
	{
		return box;
	}
}
