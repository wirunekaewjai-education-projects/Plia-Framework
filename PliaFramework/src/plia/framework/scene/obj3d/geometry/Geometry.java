package plia.framework.scene.obj3d.geometry;

public class Geometry
{
	private int type;
	
	protected int[] buffers = new int[10];

	public Geometry(int type)
	{
		this.type = type;
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setBuffer(int index, int value)
	{
		this.buffers[index] = value;
	}
	
	public int getBuffer(int index)
	{
		return buffers[index];
	}

	public static final int MESH			= 10001;
	public static final int SKINNED_MESH	= 10002;
	
	public static final int LINE			= 10003;
}
