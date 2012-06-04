package plia.framework.gameobject;

import android.graphics.RectF;
import plia.framework.graphics.Texture2D;
import plia.framework.math.Vector2;

public class ImageView extends Object2D
{
	private Texture2D texture;
	private float width, height;

	public Texture2D getTexture()
	{
		return texture;
	}
	
	public void setTexture(Texture2D texture)
	{
		this.texture = texture;
	}
	
	public Vector2 getSize()
	{
		return new Vector2(width, height);
	}
	
	public void setSize(float w, float h)
	{
		this.width = w;
		this.height = h;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public void setHeight(float height)
	{
		this.height = height;
	}
	
	public boolean intersect(ImageView view)
	{
		A.set(getX(), getY(), getX() + getWidth(), getY() + getHeight());
		B.set(view.getX(), view.getY(), view.getX() + view.getWidth(), view.getY() + view.getHeight());
		
		return A.intersect(B);
	}

	public boolean intersect(float x, float y)
	{
		A.set(getX(), getY(), getX() + getWidth(), getY() + getHeight());
		return A.contains(x, y);
	}
	
	private static RectF A = new RectF();
	private static RectF B = new RectF();
}
