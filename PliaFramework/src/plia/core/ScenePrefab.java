package plia.core;

import plia.core.scene.animation.Animation;
import plia.core.scene.shading.Material;
import plia.core.scene.shading.Shader;
import plia.math.Matrix4;

public class ScenePrefab
{
	private String rootName;
	private Matrix4 axisRotation;

	private Animation animation;
	
	private NodePrefab[] nodePrefabs;
	private Material[] materials;
	
	private Material defaultMaterial = new Material();
	
	public ScenePrefab()
	{
		defaultMaterial.setShader(Shader.DIFFUSE);
	}
	
	public String getRootName()
	{
		return rootName;
	}
	
	public Matrix4 getAxisRotation()
	{
		return axisRotation;
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public NodePrefab[] getNodePrefabs()
	{
		return nodePrefabs;
	}
	
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
	}
	
	public void setAxisRotation(Matrix4 axisRotation)
	{
		this.axisRotation = axisRotation;
	}

	public void setNodePrefabs(NodePrefab[] nodePrefabs)
	{
		this.nodePrefabs = nodePrefabs;
	}
	
	public void setRootName(String rootName)
	{
		this.rootName = rootName;
	}
	
	public Material[] getMaterials()
	{
		return materials;
	}
	
	public void setMaterials(Material[] materials)
	{
		this.materials = materials;
	}
	
	public Material getDefaultMaterial()
	{
		return defaultMaterial;
	}
	
	public void resume()
	{
		for (int i = 0; i < nodePrefabs.length; i++)
		{
			nodePrefabs[i].resume();
		}
	}
	
	public void destroy()
	{
		rootName = null;
		axisRotation = null;

		animation = null;
		
		for (NodePrefab nodePrefab : nodePrefabs)
		{
			nodePrefab.destroy();
		}
		
		nodePrefabs = null;
		materials = null;
		
		defaultMaterial = null;
	}
}
