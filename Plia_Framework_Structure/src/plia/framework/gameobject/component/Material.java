package plia.framework.gameobject.component;

import plia.framework.graphics.Color3;
import plia.framework.graphics.Texture2D;

public class Material
{
	private int shaderType;
	private Color3 baseColor;
	private Texture2D baseTexture;
	
	public int getShaderType()
	{
		return shaderType;
	}
	
	public void setShaderType(int shaderType)
	{
		this.shaderType = shaderType;
	}
	
	public Color3 getBaseColor()
	{
		return baseColor;
	}
	
	public void setBaseColor(float r, float g, float b)
	{
		this.baseColor.r = r;
		this.baseColor.g = g;
		this.baseColor.b = b;
	}
	
	public Texture2D getBaseTexture()
	{
		return baseTexture;
	}
	
	public void setBaseTexture(Texture2D baseTexture)
	{
		this.baseTexture = baseTexture;
	}
}
