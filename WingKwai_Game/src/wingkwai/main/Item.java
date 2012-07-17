package wingkwai.main;

import plia.core.GameObject;

public class Item extends GameObject
{
	private float strength = 0;
	private float speed = 0;
	private float control = 0;
	private float luck = 0;
	private long time = 0;
	
	private OnItemEventListener onItemEventListener;
	
	protected Item(String name, float str, float spd, float ctrl, float luck, long time)
	{
		super(name);
		this.strength = str;
		this.speed = spd;
		this.control = ctrl;
		this.luck = luck;
		this.time = time;
		this.onItemEventListener = new OnItemEventListener()
		{
			
			public void onEffectStart(Player player)
			{
				// TODO Auto-generated method stub
				
			}
			
			public void onEffectEnd(Player player)
			{
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	protected Item(String name, float str, float spd, float ctrl, float luck, long time, OnItemEventListener onItemEventListener)
	{
		super(name);
		this.strength = str;
		this.speed = spd;
		this.control = ctrl;
		this.luck = luck;
		this.time = time;
		this.onItemEventListener = onItemEventListener;
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
	
	public OnItemEventListener getOnItemEventListener()
	{
		return onItemEventListener;
	}
}
