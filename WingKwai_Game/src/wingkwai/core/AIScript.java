package wingkwai.core;

import java.util.ArrayList;

import plia.core.scene.Collider;
import plia.core.scene.Group;
import plia.core.scene.PlaneCollider;
import plia.core.scene.SphereCollider;
import plia.math.Vector2;
import plia.math.Vector3;

public class AIScript
{
	private Player player;
	private Vehicle vehicle;
	private Checkpoint checkpoint;
	private int currentCheckPoint = 0;

	private Vector2 currentDirection = new Vector2();
	private Vector3 forwardDir = new Vector3(0, 1, 0);
	
	private ArrayList<SphereCollider> avoidances = new ArrayList<SphereCollider>();

	public AIScript(Player player, Checkpoint checkpoint)
	{
		this.player = player;
		this.vehicle = player.getVehicle();
		this.checkpoint = checkpoint;

		Vector2 chkpp = new Vector2(checkpoint.get(currentCheckPoint).getPosition());
		Vector2 sphrpp = new Vector2(((SphereCollider) vehicle.getObject().getCollider()).getPosition());
		
		currentDirection = Vector2.subtract(chkpp, sphrpp).getNormalized();
		
		forwardDir = vehicle.getObject().getForward();
		player.setAI(true);
	}

	public void update()
	{
		if(player.hasItem() && player.getRank() > 1)
		{
			player.useItem();
		}
		
		float rand = (float) Math.max(0.7f, Math.random()) * 0.075f;
		vehicle.accelerate(rand);

		Group obj = vehicle.getObject();
		
		PlaneCollider chkp = checkpoint.get(currentCheckPoint);
		SphereCollider sphr = (SphereCollider) obj.getCollider();

		Vector3 va = sphr.getPosition();

		// Avoid Target
		for (SphereCollider target : avoidances)
		{
			Vector3 vb = target.getPosition();

			float ar = Math.abs(sphr.getRadius());
			float br = Math.abs(target.getRadius());

			float radius =  ar + br;
			float distance = Vector3.distance(va, vb);

			if(distance - radius <= 5)
			{
				Vector3 dir = Vector3.subtract(vb, va).getNormalized();
				Vector3 right = obj.getRight();
				
				float d = Vector3.dot(dir, right);
				if(d > 0)
				{
					obj.rotate(0, 0, 1);
				}
				else
				{
					obj.rotate(0, 0, -1);
				}
			}
		}
		
		obj.setForward(Vector3.lerp(obj.getForward(), forwardDir, 0.025f));
		
		// Check Checkpoint
		if(Collider.intersect(chkp, sphr))
		{
			currentCheckPoint++;

			if(currentCheckPoint >= checkpoint.size())
			{
				currentCheckPoint -= checkpoint.size();
			}
			
			int futureIndx = currentCheckPoint;
			
			if(futureIndx >= checkpoint.size())
			{
				futureIndx -= checkpoint.size();
			}
			
			PlaneCollider pc = checkpoint.get(futureIndx);
			
			Vector2 chkpp = new Vector2(pc.getPosition());
			Vector2 sphrpp = new Vector2(((SphereCollider) vehicle.getObject().getCollider()).getPosition());
			
			Vector3 up = pc.getUp();
			Vector3 nSurface = new Vector3(up.x, up.y, 0);
			currentDirection = Vector2.subtract(chkpp, sphrpp).getNormalized();
			
			if(Vector3.dot(nSurface, new Vector3(currentDirection.x, currentDirection.y, 0)) < 0)
			{
				nSurface.set(-nSurface.x, -nSurface.y, 0);
			}
			
			forwardDir = Vector3.lerp(nSurface, new Vector3(currentDirection.x, currentDirection.y, 0), (float) Math.max(0.35f, Math.min(0.8f, Math.random())));
//			forwardDir = Vector3.scale(Vector3.add(nSurface, new Vector3(currentDirection.x, currentDirection.y, 0)), (float) Math.max(0.35f, (Math.random() * 0.5f)));
		}
	}
	
	public Vehicle getVehicle()
	{
		return vehicle;
	}

	public void addObjectAvoidance(SphereCollider target)
	{
		avoidances.add(target);
	}
	
	public void removeObjectAvoidance(SphereCollider target)
	{
		avoidances.remove(target);
	}
}
