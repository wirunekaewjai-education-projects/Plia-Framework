package plia.framework.math;

public final class Vector3
{
	// Properties
	public float x, y, z;
	
	// Constructor
	public Vector3()
	{

	}

	public Vector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float xx = ( Math.round(x * 1000) ) / 1000f;
		float yy = ( Math.round(y * 1000) ) / 1000f;
		float zz = ( Math.round(z * 1000) ) / 1000f;
		
		return "X : " + xx + ", Y : " + yy + ", Z : " + zz;
	}
	
	// Methods
	public float getMagnitude()
	{
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public Vector3 getNormalize()
	{
		float length = getMagnitude();
		return new Vector3(x / length, y / length, z / length);
	}

	// Classifier Method
	public static final float dot(Vector3 vec1, Vector3 vec2)
	{
		return ((vec1.x * vec2.x) + (vec1.y * vec2.y) + (vec1.z * vec2.z));
	}

	public static float distance(Vector3 vec1, Vector3 vec2)
	{
		float dx = vec1.x - vec2.x;
		float dy = vec1.y - vec2.y;
		float dz = vec1.z - vec2.z;
		
		return Mathf.sqrt((dx*dx)+(dy*dy)+(dz*dz));
	}
	
	public static void add(Vector3 result, Vector3 vec1, Vector3 vec2)
	{
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
		result.z = vec1.z + vec2.z;
	}
	
	public static void subtract(Vector3 result, Vector3 vec1, Vector3 vec2)
	{
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
		result.z = vec1.z - vec2.z;
	}
	
	public static void scale(Vector3 result, Vector3 vec, float value)
	{
		result.x = vec.x * value;
		result.y = vec.y * value;
		result.z = vec.z * value;
	}
	
	public static void cross(Vector3 result, Vector3 vec1, Vector3 vec2)
	{
		result.x = (vec1.y * vec2.z) - (vec1.z * vec2.y);
		result.y = (vec1.z * vec2.x) - (vec1.x * vec2.z);
		result.z = (vec1.x * vec2.y) - (vec1.y * vec2.x);
	}
	
	public static void normalize(Vector3 result, Vector3 src)
	{
		float length = src.getMagnitude();
		result.x = src.x / length;
		result.y = src.y / length;
		result.z = src.z / length;
	}
	
	public static Vector3 add(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();
		add(result, vec1, vec2);
		return result;
	}
	
	public static Vector3 subtract(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();
		subtract(result, vec1, vec2);
		return result;
	}
	
	public static Vector3 scale(Vector3 vec, float value)
	{
		Vector3 result = new Vector3();
		scale(result, vec, value);
		return result;
	}
	
	public static Vector3 cross(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();
		cross(result, vec1, vec2);
		return result;
	}
	
	
	
	// Additional
	public static void lerp(Vector3 result, Vector3 start, Vector3 end, float t)
	{
		// P = P0 + tv
		result.x = start.x + ((end.x - start.x) * t);
		result.y = start.y + ((end.y - start.y) * t);
		result.z = start.z + ((end.z - start.z) * t);
	}
	
	public static Vector3 lerp(Vector3 start, Vector3 end, float t)
	{
		Vector3 result = new Vector3();
		lerp(result, start, end, t);
		return result;
	}
	
	
}
