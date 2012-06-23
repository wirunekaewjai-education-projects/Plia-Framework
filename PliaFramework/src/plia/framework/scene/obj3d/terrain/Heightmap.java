package plia.framework.scene.obj3d.terrain;

import plia.framework.core.GameObject;
import android.graphics.Color;

public class Heightmap extends GameObject
{
	private int textureBuffer;
	
	private int width;
	private int height;
	
	private int[] pixels;
	
	public Heightmap(String name, int texBuffer, int[] pixels, int width, int height)
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
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public float getPixel(int x, int y)
	{
		int row = y * height;
		int i = row + x;
		
		float r = Color.red(pixels[i]) / 255f;
		return r;
	}
}
