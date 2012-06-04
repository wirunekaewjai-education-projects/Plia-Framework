package plia.framework.math;

public final class Vector4
{
	// Properties
	public float x, y, z, w;

	// Constructor
	public Vector4()
	{

	}

	public Vector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Vector4(float[] vec)
	{
		this.x = vec[0];
		this.y = vec[1];
		this.z = vec[2];
		this.w = vec[3];
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float xx = ( Math.round(x * 1000) ) / 1000f;
		float yy = ( Math.round(y * 1000) ) / 1000f;
		float zz = ( Math.round(z * 1000) ) / 1000f;
		float ww = ( Math.round(w * 1000) ) / 1000f;
		
		return "X : "+xx+", Y : "+yy+", Z : "+zz+", W : "+ww;
	}

	// Methods
	public float getMagnitude()
	{
		return (float) Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
	}

	public Vector4 getNormalize()
	{
		float length = getMagnitude();
		return new Vector4(x / length, y / length, z / length, w / length);
	}

	// Classifier Method
	public static float dot(Vector4 vec1, Vector4 vec2)
	{
		return ((vec1.x * vec2.x) + (vec1.y * vec2.y) + (vec1.z * vec2.z) + (vec1.w * vec2.w));
	}
	
	public static void add(Vector4 result, Vector4 vec1, Vector4 vec2)
	{
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
		result.z = vec1.z + vec2.z;
		result.w = vec1.w + vec2.w;
	}
	
	public static void subtract(Vector4 result, Vector4 vec1, Vector4 vec2)
	{
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
		result.z = vec1.z - vec2.z;
		result.w = vec1.w - vec2.w;
	}
	
	public static void scale(Vector4 result, Vector4 vec, float value)
	{
		result.x = vec.x * value;
		result.y = vec.y * value;
		result.z = vec.z * value;
		result.w = vec.w * value;
	}
	
	public static Vector4 add(Vector4 vec1, Vector4 vec2)
	{
		Vector4 result = new Vector4();
		add(result, vec1, vec2);
		return result;
	}
	
	public static Vector4 subtract(Vector4 vec1, Vector4 vec2)
	{
		Vector4 result = new Vector4();
		subtract(result, vec1, vec2);
		return result;
	}
	
	public static Vector4 scale(Vector4 vec, float value)
	{
		Vector4 result = new Vector4();
		scale(result, vec, value);
		return result;
	}
}
