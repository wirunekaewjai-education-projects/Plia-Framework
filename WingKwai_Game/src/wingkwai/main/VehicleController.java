package wingkwai.main;

import plia.core.scene.Group;
import plia.core.scene.animation.Animation;

public class VehicleController
{
	private Vehicle vehicle;
	private float speed;
	private float angle;
	
	public VehicleController(Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}
	
	public Vehicle getVehicle()
	{
		return vehicle;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public float getAngle()
	{
		return angle;
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
		
		Group object = vehicle.getObject();
		object.translate(0, speed, 0);
		object.setEulerAngles(0, 0, angle);
		
		if(object.hasAnimation())
		{
			Animation animation = object.getAnimation();
			animation.setPlaybackSpeed(Math.abs(speed));
			
			if(speed == 0 && !animation.isPlaying(vehicle.getIdleClipName()))
			{
				animation.play(vehicle.getIdleClipName());
			}
		}
	}
	
	public void accelerate(float dir)
	{
		speed += dir;
		
		if (speed > Vehicle.MAX_FORWARD_VELOCITY)
        {
            speed = Vehicle.MAX_FORWARD_VELOCITY;
        }

        if (speed < Vehicle.MAX_BACKWARD_VELOCITY)
        {
            speed = Vehicle.MAX_BACKWARD_VELOCITY;
        }
        
        Group object = vehicle.getObject();
        if(object.hasAnimation())
        {
        	Animation animation = object.getAnimation();
        	if(!animation.isPlaying(vehicle.getRunClipName()))
        	{
        		animation.play(vehicle.getRunClipName());
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
}
