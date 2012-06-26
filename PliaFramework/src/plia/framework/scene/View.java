package plia.framework.scene;

import plia.framework.math.Vector2;

public class View extends Node<View>
{
	private Vector2 position = new Vector2();
	
	protected View()
	{
		setName("View");
	}
	
	@Override
	protected void update()
	{
		// TODO Auto-generated method stub
		super.update();
	}
	
	public Vector2 getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector2 position)
	{
		this.position = position;
	}
	
	public float getX()
	{
		return position.x;
	}
	
	public float getY()
	{
		return position.y;
	}
}
