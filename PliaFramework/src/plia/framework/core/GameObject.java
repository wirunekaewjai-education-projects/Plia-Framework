package plia.framework.core;

import android.util.Log;

public class GameObject
{
	protected String name;
	protected boolean active = true;
	
	public GameObject()
	{
		this.name = "GameObject";
	}
	
	public GameObject(String name)
	{
		this.name = name;
	}
	
	protected void copyTo(GameObject gameObject)
	{
		gameObject.active = this.active;
		gameObject.name = this.name;
	}

	public GameObject instantiate()
	{
		GameObject copy = new GameObject();
		this.copyTo(copy);
		return copy;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	protected void update()
	{
		
	}
	
	public void log(Object value)
	{
		Log.e("Plia Framework", value+"");
	}
}
