package wingkwai.main;

import java.util.ArrayList;

import plia.core.scene.PlaneCollider;

public class Checkpoint
{
	private ArrayList<PlaneCollider> colliders = new ArrayList<PlaneCollider>();
	
	public void add(PlaneCollider collider)
	{
		colliders.add(collider);
	}
	
	public void remove(PlaneCollider collider)
	{
		colliders.remove(collider);
	}
	
	public int size()
	{
		return colliders.size();
	}
	
	public PlaneCollider get(int index)
	{
		return colliders.get(index);
	}
}
