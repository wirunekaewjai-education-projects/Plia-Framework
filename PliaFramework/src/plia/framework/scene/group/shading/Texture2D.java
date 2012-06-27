package plia.framework.scene.group.shading;

import plia.framework.core.GameObject;
import android.graphics.Color;

public class Texture2D extends GameObject
{
	private int textureBuffer;
	
	private int width;
	private int height;
	
	protected int[] pixels;
	
	public Texture2D(String name, int texBuffer, int[] pixels, int width, int height)
	{
		super(name);
		this.textureBuffer = texBuffer;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}
	
	public int getTextureBuffer()
	{
		return textureBuffer;
	}
	
	public void setTextureBuffer(int b)
	{
		this.textureBuffer = b;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Color4 getPixel(int x, int y)
	{
		int row = y * getHeight();
		int i = row + x;
		
		float r = Color.red(pixels[i]) / 255f;
		float g = Color.green(pixels[i]) / 255f;
		float b = Color.blue(pixels[i]) / 255f;
		float a = Color.alpha(pixels[i]) / 255f;

		return new Color4(r, g, b, a);
	}
}



//package plia.framework.scene.obj3d.shading;
//
//import plia.framework.core.GameObject;
//
//public class Texture2D extends GameObject
//{
//	private int textureBuffer;
//	
//	private int width;
//	private int height;
//	
//	private Color4[] colors;
//	
//	public Texture2D(String name, int texBuffer, Color4[] colors, int width, int height)
//	{
//		super(name);
//		this.textureBuffer = texBuffer;
//		this.width = width;
//		this.height = height;
//		this.colors = colors;
//	}
//	
//	public int getTextureBuffer()
//	{
//		return textureBuffer;
//	}
//	
//	public int getWidth()
//	{
//		return width;
//	}
//	
//	public int getHeight()
//	{
//		return height;
//	}
//	
//	public Color4 getPixel(int x, int y)
//	{
//		int row = y * height;
//		return colors[row + x];
//	}
//}
