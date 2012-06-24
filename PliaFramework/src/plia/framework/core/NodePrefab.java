package plia.framework.core;

import plia.framework.scene.obj3d.geometry.Mesh;
import plia.framework.scene.obj3d.shading.Material;

public class NodePrefab
{
	private String name;
	private Mesh mesh;
	private Material material;
	private boolean hasAnimation;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Mesh getMesh()
	{
		return mesh;
	}
	
	public void setMesh(Mesh mesh)
	{
		this.mesh = mesh;
	}
	
	public Material getMaterial()
	{
		return material;
	}
	
	public void setMaterial(Material material)
	{
		this.material = material;
	}
	
	public boolean hasAnimation()
	{
		return hasAnimation;
	}
	
	public void setHasAnimation(boolean hasAnimation)
	{
		this.hasAnimation = hasAnimation;
	}
	
	public void resume()
	{
		mesh.resume();
	}
}
