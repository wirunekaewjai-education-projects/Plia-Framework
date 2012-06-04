package plia.framework.gameobject.component;

import plia.framework.math.Vector3;

public class Bounds
{
	private Vector3 center = new Vector3();
	
	public Vector3 getCenter()
	{
		return center;
	}
	
	public void setCenter(Vector3 center)
	{
		this.center = center;
	}
	
	public void setCenter(float x, float y, float z)
	{
		this.center.x = x;
		this.center.y = y;
		this.center.z = z;
	}
	
}
