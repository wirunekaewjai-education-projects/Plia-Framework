package plia.framework.gameobject;

import plia.framework.gameobject.component.Animation;

public class Model extends Object3D
{
	private boolean hasAnimation = false;
	private Animation animation;
	
	public boolean hasAnimation()
	{
		return hasAnimation;
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
	}
}
