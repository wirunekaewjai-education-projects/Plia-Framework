package plia.framework.scene;

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

	public float getRadius()
	{
		return radius;
	}
	
	public void setRadius(float radius)
	{
		this.radius = radius;
	}
}
