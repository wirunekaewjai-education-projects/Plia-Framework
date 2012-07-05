package plia.core.scene.geometry;

import plia.math.Vector3;

public class Geometry
{
	private int type;
	
	protected int[] buffers = new int[10];
	
	private Vector3 min = new Vector3(), max = new Vector3();

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
	
	public Vector3 getMin()
	{
		return min;
	}
	
	public Vector3 getMax()
	{
		return max;
	}
	
	public void setMin(Vector3 min)
	{
		this.min = min;
	}
	
	public void setMax(Vector3 max)
	{
		this.max = max;
	}

	public static final int MESH			= 10001;
	public static final int SKINNED_MESH	= 10002;
	
	public static final int LINE			= 10003;
}
