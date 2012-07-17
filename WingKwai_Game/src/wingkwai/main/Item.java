package wingkwai.main;

import plia.core.GameObject;

public class Item extends GameObject
{
	private float strength = 0;
	private float speed = 0;
	private float control = 0;
	private float luck = 0;
	private long time = 0;
	
	public Item(String name, float str, float spd, float ctrl, float luck, long time)
	{
		super(name);
		this.strength = str;
		this.speed = spd;
		this.control = ctrl;
		this.luck = luck;
		this.time = time;
	}
	
	@Override
	public GameObject instantiate()
	{
		Item item = new Item(name, strength, speed, control, luck, time);
		return item;
	}
	
	public float getStrength()
	{
		return strength;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public float getControl()
	{
		return control;
	}
	
	public float getLuck()
	{
		return luck;
	}
	
	public long getTime()
	{
		return time;
	}
	
	
	//
	public static final Item none = new Item("None", 0, 0, 1, 0, 0);
}
