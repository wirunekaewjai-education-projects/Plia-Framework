package plia.framework.gameobject.fbxobject;

import plia.framework.gameobject.component.Material;

public class FbxMesh extends FbxNode
{
	private int[] meshBuffers = new int[2];
	private Material material;
	
	public FbxMesh(float[] vertices, float[] normals, float[] uv, int[] indices)
	{
		// TODO
	}
	
	public int getMeshBuffers(int index)
	{
		return meshBuffers[index];
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public void setMaterial(Material material)
	{
		this.material = material;
	}
}
