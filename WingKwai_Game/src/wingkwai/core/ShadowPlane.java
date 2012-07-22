package wingkwai.core;

import plia.core.scene.Group;
import plia.math.Vector3;

public class ShadowPlane
{
	private Group plane;
	private Group target;
	private float z;
	
	public ShadowPlane(Group plane, Group target, float z)
	{
		this.plane = plane;
		this.target = target;
		this.z = z;
	}
	
	public void update()
	{
		Vector3 t_position = target.getPosition();
		Vector3 position = new Vector3(t_position.x, t_position.y, z);
		
		plane.setPosition(position);
	}
	
	public Group getPlane()
	{
		return plane;
	}
	
	public Group getTarget()
	{
		return target;
	}
	
	public float getZ()
	{
		return z;
	}
}
