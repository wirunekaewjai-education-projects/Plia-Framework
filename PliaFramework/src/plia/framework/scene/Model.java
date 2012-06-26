package plia.framework.scene;

import plia.framework.scene.group.geometry.Geometry;
import plia.framework.scene.group.shading.Material;


public class Model extends Group
{
	private Geometry geometry;
	private Material material;
	
	public Model()
	{
		setName("Model");
	}
	
	public Model(String name)
	{
		setName(name);
	}
	
	public Geometry getGeometry()
	{
		return geometry;
	}
	
	public void setGeometry(Geometry geometry)
	{
		this.geometry = geometry;
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
