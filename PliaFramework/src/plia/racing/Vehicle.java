package plia.racing;

import plia.core.scene.Group;

public class Vehicle
{
	private Group object;
	
	private String idleClipName = "idle";
	private String runClipName = "run";
	
	public Vehicle(Group object)
	{
		this.object = object;
	}
	
	public Group getObject()
	{
		return object;
	}
	
	public String getIdleClipName()
	{
		return idleClipName;
	}
	
	public void setIdleClipName(String idleClipName)
	{
		this.idleClipName = idleClipName;
	}
	
	public String getRunClipName()
	{
		return runClipName;
	}
	
	public void setRunClipName(String runClipName)
	{
		this.runClipName = runClipName;
	}
	
	public static float MAX_FORWARD_VELOCITY = 1.7f;
	public static float MAX_BACKWARD_VELOCITY = -0.3f;
	public static float MAX_ANGULAR_VELOCITY = 1;
}
