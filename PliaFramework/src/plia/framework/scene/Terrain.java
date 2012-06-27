package plia.framework.scene;

import plia.framework.scene.group.shading.Texture2D;

public class Terrain extends Group
{
	private Texture2D heightmap;
	private Texture2D normalmap;
	private Texture2D baseTexture;
	
	private int scale;
	private int height;

	public Terrain(Texture2D heightmap, int maxHeight, int scale)
	{
		this.heightmap = heightmap;
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
	
	public Texture2D getHeightmap()
	{
		return heightmap;
	}
	
	public Texture2D getNormalmap()
	{
		return normalmap;
	}
	
	public Texture2D getBaseTexture()
	{
		return baseTexture;
	}
	
	public void setBaseTexture(Texture2D baseTexture)
	{
		this.baseTexture = baseTexture;
	}
	
	public static void setNormalMapTo(Terrain terrain, Texture2D normalMap)
	{
		terrain.normalmap = normalMap;
	}
}
