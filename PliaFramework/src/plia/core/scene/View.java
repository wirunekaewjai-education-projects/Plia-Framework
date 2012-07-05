package plia.core.scene;

import plia.core.GameObject;
import plia.math.Vector2;

public class View extends Node<View>
{
	private Vector2 position = new Vector2();
	
	protected View()
	{
		setName("View");
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		View view = (View) gameObject;
		view.position.set(this.position);
	}

	@Override
	public View instantiate()
	{
		View copy = new View();
		this.copyTo(copy);
		return copy;
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
		this.position.set(position);
	}
	
	public void setPosition(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
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
