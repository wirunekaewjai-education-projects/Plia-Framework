package plia.core.scene;

import plia.core.GameObject;
import plia.core.scene.geometry.Geometry;
import plia.core.scene.shading.Material;


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
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);

		Model b = (Model) gameObject;
		b.geometry = this.geometry;
		b.material = this.material;
	}

	@Override
	public Model instantiate()
	{
		Model copy = new Model();
		this.copyTo(copy);
		return copy;
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
