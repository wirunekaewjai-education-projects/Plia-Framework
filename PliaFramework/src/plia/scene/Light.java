package plia.scene;

import plia.core.GameObject;
import plia.scene.shading.Color3;

public class Light extends Group
{
	private float range = 1;
	private int lightType = DIRECTIONAL_LIGHT;
	private Color3 color = new Color3(1, 1, 1);
	private float intensity = 1;

	public Light()
	{
		setName("Light");
	}
	
	public Light(int type)
	{
		setName("Light");
		this.lightType = type;
	}
	
	public Light(int type, float range)
	{
		setName("Light");
		this.lightType = type;
	}
	
	public Light(int type, float range, float intensity)
	{
		setName("Light");
		this.lightType = type;
		this.intensity = intensity;
	}
	
	public Light(int type, float red, float green, float blue)
	{
		setName("Light");
		this.lightType = type;
		this.color.r = red;
		this.color.g = green;
		this.color.b = blue;
	}

	public Light(int type, float intensity, float red, float green, float blue)
	{
		setName("Light");
		this.lightType = type;
		this.color.r = red;
		this.color.g = green;
		this.color.b = blue;
		this.intensity = intensity;
	}

	public Light(int type, float range, float intensity, float red, float green, float blue)
	{
		setName("Light");
		this.lightType = type;
		this.range = range;
		this.color.r = red;
		this.color.g = green;
		this.color.b = blue;
		this.intensity = intensity;
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		Light b = (Light) gameObject;
		b.range = this.range;
		b.lightType = this.lightType;
		b.intensity = this.intensity;
		b.color.r = this.color.r;
		b.color.g = this.color.g;
		b.color.b = this.color.b;
	}

	@Override
	public Light instantiate()
	{
		Light copy = new Light();
		this.copyTo(copy);
		return copy;
	}

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
