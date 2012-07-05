package plia.scene.shading;

public final class Color4
{
	public float r, g, b, a;
	
	public Color4()
	{
		this.a = 1;
	}
	
	public Color4(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1;
	}
	
	public Color4(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
}
