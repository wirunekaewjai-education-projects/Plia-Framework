package plia.framework.scene.obj3d;

import plia.framework.scene.Object3D;
import plia.framework.scene.obj3d.geometry.Geometry;
import plia.framework.scene.obj3d.shading.Material;


public class Model extends Object3D
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
