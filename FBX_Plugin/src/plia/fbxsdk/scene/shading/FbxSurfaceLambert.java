package plia.fbxsdk.scene.shading;

import plia.framework.math.Vector3;

public class FbxSurfaceLambert extends FbxSurfaceMaterial
{
	private FbxTexture diffuseTexture;
	
	private Vector3 emissive = new Vector3();
	private Vector3 ambient = new Vector3();
	private Vector3 diffuse = new Vector3();
	private Vector3 normalMap = new Vector3();
	private Vector3 bump = new Vector3();
	private Vector3 transparentColor = new Vector3();
	private Vector3 displacementColor = new Vector3();
	private Vector3 vectorDisplacementColor = new Vector3();
	
	private float emissiveFactor = 0;
	private float ambientFactor = 0;
	private float diffuseFactor = 0;
	private float bumpFactor = 0;
	private float transparencyFactor = 0;
	private float displacementFactor = 0;
	private float vectorDisplacementFactor = 0;

	public FbxSurfaceLambert(long uniqueID)
	{
		super(uniqueID);
		// TODO Auto-generated constructor stub
	}

	public Vector3 getEmissive()
	{
		return emissive;
	}
	
	public Vector3 getAmbient()
	{
		return ambient;
	}
	
	public Vector3 getDiffuse()
	{
		return diffuse;
	}
	
	public Vector3 getNormalMap()
	{
		return normalMap;
	}
	
	public Vector3 getBump()
	{
		return bump;
	}
	
	public Vector3 getTransparentColor()
	{
		return transparentColor;
	}
	
	public Vector3 getDisplacementColor()
	{
		return displacementColor;
	}
	
	public Vector3 getVectorDisplacementColor()
	{
		return vectorDisplacementColor;
	}
	
	public float getEmissiveFactor()
	{
		return emissiveFactor;
	}
	
	public float getAmbientFactor()
	{
		return ambientFactor;
	}
	
	public float getDiffuseFactor()
	{
		return diffuseFactor;
	}
	
	public float getBumpFactor()
	{
		return bumpFactor;
	}
	
	public float getTransparencyFactor()
	{
		return transparencyFactor;
	}
	
	public float getDisplacementFactor()
	{
		return displacementFactor;
	}
	
	public float getVectorDisplacementFactor()
	{
		return vectorDisplacementFactor;
	}
	
	public FbxTexture getDiffuseTexture()
	{
		return diffuseTexture;
	}
	
	
	
	
	public void setEmissive(float r, float g, float b)
	{
		this.emissive.x = r;
		this.emissive.y = g;
		this.emissive.z = b;
	}
	
	public void setAmbient(float r, float g, float b)
	{
		this.ambient.x = r;
		this.ambient.y = g;
		this.ambient.z = b;
	}
	
	public void setDiffuse(float r, float g, float b)
	{
		this.diffuse.x = r;
		this.diffuse.y = g;
		this.diffuse.z = b;
	}
	
	public void setNormalMap(float r, float g, float b)
	{
		this.normalMap.x = r;
		this.normalMap.y = g;
		this.normalMap.z = b;
	}
	
	public void setBump(float r, float g, float b)
	{
		this.bump.x = r;
		this.bump.y = g;
		this.bump.z = b;
	}
	
	public void setTransparentColor(float r, float g, float b)
	{
		this.transparentColor.x = r;
		this.transparentColor.y = g;
		this.transparentColor.z = b;
	}
	
	public void setDisplacementColor(float r, float g, float b)
	{
		this.displacementColor.x = r;
		this.displacementColor.y = g;
		this.displacementColor.z = b;
	}
	
	public void setVectorDisplacementColor(float r, float g, float b)
	{
		this.vectorDisplacementColor.x = r;
		this.vectorDisplacementColor.y = g;
		this.vectorDisplacementColor.z = b;
	}
	
	public void setEmissiveFactor(float emissiveFactor)
	{
		this.emissiveFactor = emissiveFactor;
	}
	
	public void setAmbientFactor(float ambientFactor)
	{
		this.ambientFactor = ambientFactor;
	}
	
	public void setDiffuseFactor(float diffuseFactor)
	{
		this.diffuseFactor = diffuseFactor;
	}
	
	public void setBumpFactor(float bumpFactor)
	{
		this.bumpFactor = bumpFactor;
	}
	
	public void setTransparencyFactor(float transparencyFactor)
	{
		this.transparencyFactor = transparencyFactor;
	}
	
	public void setDisplacementFactor(float displacementFactor)
	{
		this.displacementFactor = displacementFactor;
	}
	
	public void setVectorDisplacementFactor(float vectorDisplacementFactor)
	{
		this.vectorDisplacementFactor = vectorDisplacementFactor;
	}

	public void setDiffuseTexture(FbxTexture diffuseTexture)
	{
		this.diffuseTexture = diffuseTexture;
	}
}
