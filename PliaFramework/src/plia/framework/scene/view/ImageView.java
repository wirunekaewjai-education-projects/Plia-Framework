package plia.framework.scene.view;

import android.graphics.RectF;
import plia.framework.math.Vector2;
import plia.framework.scene.View;
import plia.framework.scene.group.shading.Texture2D;

public class ImageView extends View
{
	private Texture2D imageSrc;
	private float sx = 1, sy = 1;
	
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
		return new Vector2(sx, sy);
	}
	
	public void setScale(float x, float y)
	{
		this.sx = x;
		this.sy = y;
	}

	public boolean intersect(ImageView view)
	{
		
		A.set(getX(), getY(), getX() + sx, getY() + sy);
		B.set(view.getX(), view.getY(), view.getX() + view.sx, view.getY() + view.sy);
		
		return A.intersect(B);
	}

	public boolean intersect(float x, float y)
	{
		A.set(getX(), getY(), getX() + sx, getY() + sy);
		return A.contains(x, y);
	}
	
	private static RectF A = new RectF();
	private static RectF B = new RectF();
}
