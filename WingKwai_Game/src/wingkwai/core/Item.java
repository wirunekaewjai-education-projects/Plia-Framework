package wingkwai.core;

import plia.core.GameObject;
import plia.core.scene.Sprite;

public class Item extends GameObject
{
	private float strength = 0;
	private float speed = 0;
	private float control = 0;
	private float luck = 0;
	private long time = 0;
	
	private OnItemEventListener onItemEventListener;
	
	private Sprite shortcut;
	
	public Item(String name, Sprite shortcut, float str, float spd, float ctrl, float luck, long time)
	{
		super(name);
		this.shortcut = shortcut;
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
	
	public Item(String name, Sprite shortcut, float str, float spd, float ctrl, float luck, long time, OnItemEventListener onItemEventListener)
	{
		super(name);
		this.shortcut = shortcut;
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
		Item item = new Item(name, shortcut, strength, speed, control, luck, time);
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
	
	public Sprite getShortcut()
	{
		return shortcut;
	}
}
