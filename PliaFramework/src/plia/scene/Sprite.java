package plia.scene;

import android.graphics.RectF;
import plia.core.AnimationPlayer;
import plia.core.GameObject;
import plia.math.Vector2;
import plia.scene.animation.Animation;
import plia.scene.shading.Texture2D;

public class Sprite extends View
{
	private Texture2D imageSrc;
	private Vector2 scale = new Vector2(1, 1);
	
	public Sprite()
	{
		
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		Sprite view = (Sprite) gameObject;
		view.imageSrc = this.imageSrc;
		view.scale.set(this.scale);
	}

	@Override
	public Sprite instantiate()
	{
		Sprite copy = new Sprite();
		this.copyTo(copy);
		return copy;
	}

	@Override
	protected void update()
	{
		// TODO Auto-generated method stub
		super.update();
		
		if(isActive())
		{
			if(hasAnimation && animation != null)
			{
				AnimationPlayer.getInstance().enqueue(animation);
			}
		}
	}
	
	public Texture2D getImageSrc()
	{
		return imageSrc;
	}
	
	public void setImageSrc(Texture2D imageSrc)
	{
		this.imageSrc = imageSrc;
	}
	
	public void setImageSrc(Texture2D imageSrc, int frame)
	{
		this.imageSrc = imageSrc;
		setAnimation(new Animation(0, frame));
	}
	
	public void setCenter(float x, float y)
	{
		setPosition(x - (scale.x / 2f), y - (scale.y / 2f));
	}
	
	public Vector2 getCenter()
	{
		Vector2 pos = getPosition();
		return new Vector2(pos.x + (scale.x / 2f), pos.y + (scale.y / 2f));
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
