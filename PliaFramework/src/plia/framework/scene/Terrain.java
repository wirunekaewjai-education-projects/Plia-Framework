package plia.framework.scene;

import plia.framework.scene.group.shading.Texture2D;
import plia.framework.scene.group.terrain.Heightmap;

public class Terrain extends Group
{
	private Heightmap heightmap;
	private Texture2D normalmap;
	private Texture2D baseTexture;
	
	private int scale;
	private int height;

	public Terrain(Heightmap heightmap, Texture2D normalmap, int maxHeight, int scale)
	{
		this.heightmap = heightmap;
		this.normalmap = normalmap;
		this.height = maxHeight;
		this.scale = scale;
	}
	
	public void setTerrainScale(int scale)
	{
		this.scale = scale;
	}
	
	public void setTerrainMaxHeight(int height)
	{
		this.height = height;
	}
	
	public int getTerrainScale()
	{
		return scale;
	}
	
	public int getTerrainMaxHeight()
	{
		return height;
	}
	
	public Heightmap getHeightmap()
	{
		return heightmap;
	}
	
	public Texture2D getBaseTexture()
	{
		return baseTexture;
	}
	
	public void setBaseTexture(Texture2D baseTexture)
	{
		this.baseTexture = baseTexture;
	}
}
