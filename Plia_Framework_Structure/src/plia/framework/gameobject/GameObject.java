package plia.framework.gameobject;

import plia.framework.core.UpdateAdapter;

public abstract class GameObject implements UpdateAdapter
{
	private String name;
	private boolean isActive = true;
	
	protected GameObject()
	{
		this.name = "GameObject";
	}
	
	protected GameObject(String name)
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
		return isActive;
	}
	
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
}
