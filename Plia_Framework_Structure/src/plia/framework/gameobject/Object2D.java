package plia.framework.gameobject;

import plia.framework.math.Vector2;

public abstract class Object2D extends Node<Object2D>
{
	private float x, y;
	
	public void update()
	{
		
	}

	public Vector2 getPosition()
	{
		return new Vector2(x, y);
	}
	
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float getX()
	{
		return x;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
}
