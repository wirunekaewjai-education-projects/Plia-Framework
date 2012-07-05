package plia.scene;

import plia.scene.shading.Texture2D;

public class SkyBox extends Sky
{
	private Texture2D front;
	private Texture2D back;
	private Texture2D left;
	private Texture2D right;
	private Texture2D top;
	private Texture2D bottom;
	
	public SkyBox(Texture2D front, Texture2D back, Texture2D left, Texture2D right, Texture2D top, Texture2D bottom)
	{
		this.front = front;
		this.back = back;
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}
	
	public Texture2D getFront()
	{
		return front;
	}
	
	public Texture2D getBack()
	{
		return back;
	}
	
	public Texture2D getLeft()
	{
		return left;
	}
	
	public Texture2D getRight()
	{
		return right;
	}
	
	public Texture2D getTop()
	{
		return top;
	}
	
	public Texture2D getBottom()
	{
		return bottom;
	}
	
//	public void setFront(Texture2D front)
//	{
//		this.front = front;
//	}
//	
//	public void setBack(Texture2D back)
//	{
//		this.back = back;
//	}
//	
//	public void setLeft(Texture2D left)
//	{
//		this.left = left;
//	}
//	
//	public void setRight(Texture2D right)
//	{
//		this.right = right;
//	}
//	
//	public void setTop(Texture2D top)
//	{
//		this.top = top;
//	}
//	
//	public void setBottom(Texture2D bottom)
//	{
//		this.bottom = bottom;
//	}
}
