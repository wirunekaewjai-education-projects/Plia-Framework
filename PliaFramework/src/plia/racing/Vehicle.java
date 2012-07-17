package plia.racing;

import plia.core.scene.Group;
import plia.core.scene.SphereCollider;
import plia.core.scene.animation.Animation;

public class Vehicle
{
	private Group object;
	private SphereCollider collider = new SphereCollider(3);
	
	private String idleClipName = "idle";
	private String runClipName = "run";
	
	private float speed;
	private float angle;
	
	private float maxVelocityMultiplier = 1;
	
	public Vehicle(Group object)
	{
		this.object = object;
		this.object.setCollider(collider);
	}
	
	public Group getObject()
	{
		return object;
	}
	
	public SphereCollider getCollider()
	{
		return collider;
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
	
	public float getVelocity()
	{
		return speed;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public void setVelocity(float v)
	{
		speed = v;
	}
	
	public void update()
	{
		if(speed > 0.02f)
		{
			speed -= 0.01f;
		}
		else if(speed < -0.02f)
		{
			speed += 0.01f;
		}
		else if (speed > -0.02f && speed < 0.02f)
        {
            speed = 0;
        }
		
		if (angle >= 360)
        {
            angle = 0;
        }
        else if (angle < 0)
        {
            angle = 359;
        }

		object.translate(0, speed, 0);
		object.setEulerAngles(0, 0, angle);
		
		if(object.hasAnimation())
		{
			Animation animation = object.getAnimation();
			animation.setPlaybackSpeed(Math.abs(speed));
			
			if(speed == 0 && !animation.isPlaying(idleClipName))
			{
				animation.play(idleClipName);
			}
		}
	}
	
	public void accelerate(float dir)
	{
		speed += dir;
		
		float maxFV = Vehicle.MAX_FORWARD_VELOCITY * maxVelocityMultiplier;
		float maxBV = Vehicle.MAX_BACKWARD_VELOCITY * maxVelocityMultiplier;
		
		if (speed > maxFV)
        {
            speed = maxFV;
        }

        if (speed < maxBV)
        {
            speed = maxBV;
        }

        if(object.hasAnimation())
        {
        	Animation animation = object.getAnimation();
        	if(!animation.isPlaying(runClipName))
        	{
        		animation.play(runClipName);
        	}
        }
	}
	
	public void turn(float dir)
	{
		if (speed > 0f)
        {
            angle += dir;
            speed -= 0.008f;
        }
        else if (speed < 0f)
        {
            angle -= dir;
            speed += 0.008f;
        }
	}
	
	public float getMaxVelocityMultiplier()
	{
		return maxVelocityMultiplier;
	}
	
	public void setMaxVelocityMultiplier(float maxVelocityMultiplier)
	{
		this.maxVelocityMultiplier = maxVelocityMultiplier;
	}
	
	public static float MAX_FORWARD_VELOCITY = 1.7f;
	public static float MAX_BACKWARD_VELOCITY = -0.3f;
	public static float MAX_ANGULAR_VELOCITY = 1;
}
