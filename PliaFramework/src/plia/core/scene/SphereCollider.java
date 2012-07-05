package plia.core.scene;

import plia.core.GameObject;

public final class SphereCollider extends Collider
{
	private float radius;
	
	public SphereCollider()
	{
		this.radius = 1;
	}

	public SphereCollider(float radius)
	{
		this.radius = radius;
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		SphereCollider b = (SphereCollider) gameObject;
		b.radius = this.radius;
	}

	@Override
	public SphereCollider instantiate()
	{
		SphereCollider copy = new SphereCollider();
		this.copyTo(copy);
		return copy;
	}

	public float getRadius()
	{
		return radius;
	}
	
	public void setRadius(float radius)
	{
		this.radius = radius;
	}
}
