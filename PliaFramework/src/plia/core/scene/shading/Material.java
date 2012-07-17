package plia.core.scene.shading;


public class Material
{
	private Shader shader;
	private Color3 baseColor = new Color3(0.5f, 0.5f, 0.5f);
	private Texture2D baseTexture;
	private float lightAbsorbMultipler = 1;
	
	public Material()
	{

	}
	
	public Shader getShader()
	{
		return shader;
	}
	
	public void setShader(Shader shader)
	{
		this.shader = shader;
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
	
	public float getLightAbsorbMultipler()
	{
		return lightAbsorbMultipler;
	}
	
	public void setLightAbsorbMultipler(float lightAbsorbMultipler)
	{
		this.lightAbsorbMultipler = lightAbsorbMultipler;
	}
}
