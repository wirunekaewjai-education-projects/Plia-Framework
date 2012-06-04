package plia.framework.gameobject.component;

import plia.framework.math.Vector3;

public class BoundingBox extends Bounds
{
	private Vector3 size = new Vector3();
	
	public Vector3 getSize()
	{
		return size;
	}
	
	public void setSize(float x, float y, float z)
	{
		this.size.x = x;
		this.size.y = y;
		this.size.z = z;
	}
}
