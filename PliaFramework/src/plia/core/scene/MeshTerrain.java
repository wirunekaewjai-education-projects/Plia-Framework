package plia.core.scene;

import plia.core.scene.shading.Texture2D;

public class MeshTerrain extends Terrain
{
	private Group terrainModel;
	
	public MeshTerrain(Group terrainModel, Texture2D heightmap, Texture2D normalmap, int maxHeight, int scale)
	{
		super(heightmap, maxHeight, scale);
		
		this.terrainModel = terrainModel;
		
		setNormalMapTo(this, normalmap);
	}
	
	public Group getTerrainModel()
	{
		return terrainModel;
	}
}
