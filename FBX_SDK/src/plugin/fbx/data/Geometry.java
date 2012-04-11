package plugin.fbx.data;

public class Geometry
{
	public String id = null;
	
	public float[] vertices = null;
	public float[] normals = null;
	public float[] uv = null;
	
	public int[] uvIndices = null;
	public int[] verticesIndices = null;
	
	public Geometry(String id)
	{
		this.id = id;
	}
}
