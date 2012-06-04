package plia.framework.gameobject;

import plia.framework.graphics.Color3;

public class Light extends Object3D
{
	private float range;
	private int lightType = DIRECTIONAL_LIGHT;
	private Color3 color;
	private float intensity;
	
	public float getRange()
	{
		return range;
	}
	
	public void setRange(float range)
	{
		this.range = range;
	}
	
	public int getLightType()
	{
		return lightType;
	}
	
	public void setLightType(int lightType)
	{
		this.lightType = lightType;
	}
	
	public Color3 getColor()
	{
		return color;
	}
	
	public void setColor(Color3 color)
	{
		this.color = color;
	}
	
	public void setColor(float r, float g, float b)
	{
		this.color.r = r;
		this.color.g = g;
		this.color.b = b;
	}
	
	public float getIntensity()
	{
		return intensity;
	}
	
	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}
	
	public static final int DIRECTIONAL_LIGHT 	= 0;
	public static final int POINT_LIGHT 		= 1;
}
