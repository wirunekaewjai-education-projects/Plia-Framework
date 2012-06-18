package plia.framework.scene.obj3d.shading;

import plia.framework.core.GameObject;

public class Texture2D extends GameObject
{
	private int textureBuffer;
	
	private int width;
	private int height;
	
	private Color4[] colors;
	
	public Texture2D(String name, int texBuffer, Color4[] colors, int width, int height)
	{
		super(name);
		this.textureBuffer = texBuffer;
		this.width = width;
		this.height = height;
		this.colors = colors;
	}
	
	public int getTextureBuffer()
	{
		return textureBuffer;
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
		int row = y * height;
		return colors[row + x];
	}
}
