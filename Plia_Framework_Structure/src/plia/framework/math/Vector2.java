package plia.framework.math;

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

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float xx = ( Math.round(x * 1000) ) / 1000f;
		float yy = ( Math.round(y * 1000) ) / 1000f;
		
		return "X : " + xx + ", Y : " + yy;
	}

	// Methods
	public float getMagnitude()
	{
		return (float) Math.sqrt((x * x) + (y * y));
	}

	public Vector2 getNormalize()
	{
		float length = getMagnitude();
		return new Vector2(x / length, y / length);
	}

	// Classifier Method
	public static void add(Vector2 result, Vector2 vec1, Vector2 vec2)
	{
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
	}
	
	public static void subtract(Vector2 result, Vector2 vec1, Vector2 vec2)
	{
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
	}
	
	public static void scale(Vector2 result, Vector2 vec, float value)
	{
		result.x = vec.x * value;
		result.y = vec.y * value;
	}
	
	public static Vector2 add(Vector2 vec1, Vector2 vec2)
	{
		Vector2 result = new Vector2();
		add(result, vec1, vec2);
		return result;
	}
	
	public static Vector2 subtract(Vector2 vec1, Vector2 vec2)
	{
		Vector2 result = new Vector2();
		subtract(result, vec1, vec2);
		return result;
	}
	
	public static Vector2 scale(Vector2 vec, float value)
	{
		Vector2 result = new Vector2();
		scale(result, vec, value);
		return result;
	}
}
