package plia.framework.gameobject;

import plia.framework.graphics.Texture2D;

public class Terrain extends Object3D
{
	private Texture2D diffuseMap;
	private Texture2D heightMap;
	private Texture2D normalMap;
	
	private float height;
	private float length;
	
	public Terrain(Texture2D heightmap, Texture2D normalmap, float height, float length)
	{
		this.heightMap = heightmap;
		this.height = height;
		this.length = length;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public float getLength()
	{
		return length;
	}
	
	public Texture2D getDiffuseMap()
	{
		return diffuseMap;
	}
	
	public Texture2D getHeightMap()
	{
		return heightMap;
	}
	
	public Texture2D getNormalMap()
	{
		return normalMap;
	}
	
	public void setDiffuseMap(Texture2D diffuseMap)
	{
		this.diffuseMap = diffuseMap;
	}
}
