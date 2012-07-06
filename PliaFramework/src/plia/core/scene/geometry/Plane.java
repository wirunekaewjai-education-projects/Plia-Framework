package plia.core.scene.geometry;

public class Plane
{
	private final float[] vertices;
	private final int[] indices;

	private int segment = 32;
	
	private Plane()
	{
		int length = (segment+1);
		int s, t;
		
		vertices = new float[length*length*2];
		indices = new int[segment*segment*6];
		
		int seg6 = segment*6;

		for (int v = 0; v < length; v++)
		{
			for (int u = 0; u < length; u++)
			{
				int index = ((v*length)+u);
				int vertex_index = index*2;
				
				vertices[vertex_index] = u;
				vertices[vertex_index+1] = v;

				if(v < segment && u < segment)
				{
					t = ((v+1)*(length))+u;
					s = index;

					int indices_index = ((v*seg6)+(u*6));

					indices[indices_index] = s;
					indices[indices_index+1] = t;
					indices[indices_index+2] = t+1;
					
					indices[indices_index+3] = s;
					indices[indices_index+4] = t+1;
					indices[indices_index+5] = s+1;
				}
			}
		}
	}
	
	public int getSegment()
	{
		return segment;
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public int[] getIndices()
	{
		return indices;
	}
	
	public int getIndicesCount()
	{
		return indices.length;
	}
	
	private static final Plane instance = new Plane();
	public static Plane getInstance()
	{
		return instance;
	}
}
