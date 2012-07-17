package wingkwai.main;

import plia.core.scene.Collider;
import plia.core.scene.PlaneCollider;
import plia.core.scene.SphereCollider;
import plia.math.Vector3;

public class AIScript
{
	private Vehicle vehicle;
	private Checkpoint checkpoint;
	
	private int currentCheckPoint = 0;
	private boolean isStarted = false;
	
	public AIScript(Vehicle vehicle, Checkpoint checkpoint)
	{
		this.vehicle = vehicle;
		this.checkpoint = checkpoint;
	}
	
	private void set()
	{
		PlaneCollider chkp = checkpoint.get(currentCheckPoint);
		SphereCollider sphr = vehicle.getCollider();

		Vector3 dir = Vector3.subtract(chkp.getPosition(), sphr.getPosition());
		
		float distance = dir.getMagnituded();
		Vector3 dirn = dir.getNormalized();
		
		vehicle.getObject().setLookAt(chkp.getPosition());
	}
	
	public void update()
	{
		if(!isStarted)
		{
//			set();
			isStarted = true;
		}
		
		PlaneCollider chkp = checkpoint.get(currentCheckPoint);
		SphereCollider sphr = vehicle.getCollider();
		
		vehicle.accelerate(1);
		
		if(Collider.intersect(chkp, sphr))
		{
			currentCheckPoint++;
			
			if(currentCheckPoint >= checkpoint.size())
			{
				currentCheckPoint -= checkpoint.size();
			}
			
//			set();
		}
	}
}
