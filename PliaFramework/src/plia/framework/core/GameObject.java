package plia.framework.core;

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
}
