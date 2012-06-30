package plia.framework.scene;

import plia.framework.core.GameObject;

public final class BoundingSphere extends Bounds
{
	private float radius;
	
	public BoundingSphere()
	{
		this.radius = 1;
	}

	public BoundingSphere(float radius)
	{
		this.radius = radius;
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		BoundingSphere b = (BoundingSphere) gameObject;
		b.radius = this.radius;
	}

	@Override
	public BoundingSphere instantiate()
	{
		BoundingSphere copy = new BoundingSphere();
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
