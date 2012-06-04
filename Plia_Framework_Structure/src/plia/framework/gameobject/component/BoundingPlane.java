package plia.framework.gameobject.component;

import plia.framework.math.Vector2;
import plia.framework.math.Vector3;

public class BoundingPlane extends Bounds
{
	private Vector2 size = new Vector2();
	private Vector3 up = new Vector3();
	
	public Vector2 getSize()
	{
		return size;
	}
	
	public void setSize(float x, float y)
	{
		this.size.x = x;
		this.size.y = y;
	}
	
	public Vector3 getUp()
	{
		return up;
	}
	
	public void setUp(float x, float y, float z)
	{
		this.up.x = x;
		this.up.y = y;
		this.up.z = z;
	}
}
