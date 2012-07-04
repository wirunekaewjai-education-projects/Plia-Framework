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
		
		this.setCollider(new TerrainCollider(this));
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
	
	@Override
	public TerrainCollider getCollider()
	{
		// TODO Auto-generated method stub
		return (TerrainCollider) super.getCollider();
	}
	
	@Override
	public void setCollider(Collider collider)
	{
		// TODO Auto-generated method stub
		if(collider instanceof TerrainCollider)
		{
			super.setCollider(collider);
		}
	}
	
	private static int[] terrainBuffers = new int[2];
	public static void setTerrainBuffer(int[] terrainBuffers)
	{
		Terrain.terrainBuffers = terrainBuffers;
	}
	
	public static int getTerrainBuffer(int index)
	{
		return terrainBuffers[index];
	}
}
