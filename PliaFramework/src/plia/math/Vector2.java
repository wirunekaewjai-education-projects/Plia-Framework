package plia.math;

public final class Vector2
{
	// Properties
	public float x, y;

	// Constructor
	public Vector2()
	{

	}

	public Vector2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2(float[] vec)
	{
		this.x = vec[0];
		this.y = vec[1];
	}
	
	public Vector2(Vector2 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float xx = ( Math.round(x * 1000) ) / 1000f;
		float yy = ( Math.round(y * 1000) ) / 1000f;
		
		return "X : " + xx + ", Y : " + yy;
	}
	
	@Override
	public Vector2 clone()
	{
		return new Vector2(x, y);
	}
	
	public void copyTo(float[] v)
	{
		v[0] = x;
		v[1] = y;
	}

	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
	}

	// Methods
	public float getMagnituded()
	{
		return (float) Math.sqrt((x * x) + (y * y));
	}

	public Vector2 getNormalized()
	{
		float length = getMagnituded();
		return new Vector2(x / length, y / length);
	}
	
	public Vector2 add(Vector2 vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		
		return this;
	}
	
	public Vector2 subtract(Vector2 vector)
	{
		this.x -= vector.x;
		this.y -= vector.y;
		
		return this;
	}
	
	public Vector2 scale(float value)
	{
		this.x *= value;
		this.y *= value;

		return this;
	}

	// Classifier Method
	public static Vector2 add(Vector2 vec1, Vector2 vec2)
	{
		return new Vector2(vec1.x + vec2.x, vec1.y + vec2.y);
	}
	
	public static Vector2 subtract(Vector2 vec1, Vector2 vec2)
	{
		return new Vector2(vec1.x - vec2.x, vec1.y - vec2.y);
	}
	
	public static Vector2 scale(Vector2 vec, float value)
	{
		return new Vector2(vec.x * value, vec.y * value);
	}
	
	public static Vector2 lerp(Vector2 start, Vector2 end, float t)
	{
		Vector2 result = new Vector2();
		// P = P0 + tv
		result.x = start.x + ((end.x - start.x) * t);
		result.y = start.y + ((end.y - start.y) * t);
		
		return result;
	}
	
	public static Vector2 normalize(Vector2 result, Vector2 vec)
	{
		float length = vec.getMagnituded();
		result.set(vec.x / length, vec.y / length);
		
		return result;
	}
}
