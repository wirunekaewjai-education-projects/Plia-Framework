package plia.framework.scene.view;

import android.graphics.RectF;
import plia.framework.math.Vector2;
import plia.framework.scene.View;
import plia.framework.scene.group.animation.Animation;
import plia.framework.scene.group.shading.Texture2D;

public class Sprite extends View
{
	private Texture2D imageSrc;
	private Vector2 scale = new Vector2(1, 1);
	
	public Sprite()
	{
		
	}
	
	public Sprite(int frame)
	{
		setAnimation(new Animation(0, frame));
	}
	
	public Texture2D getImageSrc()
	{
		return imageSrc;
	}
	
	public void setImageSrc(Texture2D imageSrc)
	{
		this.imageSrc = imageSrc;
	}
	
	public Vector2 getScale()
	{
		return new Vector2(scale.x, scale.y);
	}
	
	public void setScale(float x, float y)
	{
		this.scale.x = x;
		this.scale.y = y;
	}
	
	public void setScale(Vector2 scale)
	{
		this.scale.set(scale);
	}

	public boolean intersect(Sprite view)
	{
		
		A.set(getX(), getY(), getX() + scale.x, getY() + scale.y);
		B.set(view.getX(), view.getY(), view.getX() + view.scale.x, view.getY() + view.scale.y);
		
		return A.intersect(B);
	}

	public boolean intersect(float x, float y)
	{
		A.set(getX(), getY(), getX() + scale.x, getY() + scale.y);
		return A.contains(x, y);
	}
	
	private static RectF A = new RectF();
	private static RectF B = new RectF();
}
