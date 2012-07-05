package plia.core;

import java.util.Vector;

import plia.scene.animation.Animation;

public final class AnimationPlayer
{
	private Vector<Animation> queues = new Vector<Animation>();
	
	private AnimationPlayer()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void enqueue(Animation animation)
	{
		if(!queues.contains(animation))
		{
			queues.add(animation);
		}
	}
	
	public void update()
	{
		for (int i = 0; i < queues.size(); i++)
		{
			queues.get(i).update();
		}
		
		queues.clear();
	}
	
	private static AnimationPlayer instance = new AnimationPlayer();
	public static AnimationPlayer getInstance()
	{
		return instance;
	}
}
