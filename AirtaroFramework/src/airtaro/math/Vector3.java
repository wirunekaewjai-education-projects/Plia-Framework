package airtaro.math;

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
	
	public Vector3(float[] vec)
	{
		this.x = vec[0];
		this.y = vec[1];
		this.z = vec[2];
	}
	
	public Vector3(Vector3 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
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
	
	@Override
	public Vector3 clone()
	{
		return new Vector3(x, y, z);
	}
	
	public void copyTo(float[] v)
	{
		v[0] = x;
		v[1] = y;
		v[2] = z;
	}

	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Vector3 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
	}
	
	// Methods
	public float getMagnituded()
	{
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public Vector3 getNormalized()
	{
		float length = getMagnituded();
		return new Vector3(x / length, y / length, z / length);
	}
	
	public Vector3 add(Vector3 vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		
		return this;
	}
	
	public Vector3 subtract(Vector3 vector)
	{
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		
		return this;
	}
	
	public Vector3 scale(float value)
	{
		this.x *= value;
		this.y *= value;
		this.z *= value;
		
		return this;
	}

	// Classifier Method
	public static float dot(Vector3 vec1, Vector3 vec2)
	{
		return ((vec1.x * vec2.x) + (vec1.y * vec2.y) + (vec1.z * vec2.z));
	}

	public static Vector3 add(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();
		
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
		result.z = vec1.z + vec2.z;
		
		return result;
	}
	
	public static Vector3 subtract(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();
		
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
		result.z = vec1.z - vec2.z;
		
		return result;
	}
	
	public static Vector3 scale(Vector3 vec, float value)
	{
		Vector3 result = new Vector3();
		
		result.x = vec.x * value;
		result.y = vec.y * value;
		result.z = vec.z * value;
		
		return result;
	}

	public static Vector3 lerp(Vector3 start, Vector3 end, float t)
	{
		Vector3 result = new Vector3();
		
		// P = P0 + tv
		result.x = start.x + ((end.x - start.x) * t);
		result.y = start.y + ((end.y - start.y) * t);
		result.z = start.z + ((end.z - start.z) * t);
		
		return result;
	}

	public static float distance(Vector3 vec1, Vector3 vec2)
	{
		float dx = vec1.x - vec2.x;
		float dy = vec1.y - vec2.y;
		float dz = vec1.z - vec2.z;
		
		return (float) Math.sqrt((dx*dx)+(dy*dy)+(dz*dz));
	}

	public static Vector3 cross(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();
		
		result.x = (vec1.y * vec2.z) - (vec1.z * vec2.y);
		result.y = (vec1.z * vec2.x) - (vec1.x * vec2.z);
		result.z = (vec1.x * vec2.y) - (vec1.y * vec2.x);
		
		return result;
	}
	
	//////
	
	public static Vector3 add(Vector3 result, Vector3 vec1, Vector3 vec2)
	{
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
		result.z = vec1.z + vec2.z;
		
		return result;
	}
	
	public static Vector3 subtract(Vector3 result, Vector3 vec1, Vector3 vec2)
	{
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
		result.z = vec1.z - vec2.z;
		
		return result;
	}
	
	public static Vector3 scale(Vector3 result, Vector3 vec, float value)
	{
		result.x = vec.x * value;
		result.y = vec.y * value;
		result.z = vec.z * value;
		
		return result;
	}

	public static Vector3 lerp(Vector3 result, Vector3 start, Vector3 end, float t)
	{
		// P = P0 + tv
		result.x = start.x + ((end.x - start.x) * t);
		result.y = start.y + ((end.y - start.y) * t);
		result.z = start.z + ((end.z - start.z) * t);
		
		return result;
	}
	
	public static Vector3 cross(Vector3 result, Vector3 vec1, Vector3 vec2)
	{
		result.x = (vec1.y * vec2.z) - (vec1.z * vec2.y);
		result.y = (vec1.z * vec2.x) - (vec1.x * vec2.z);
		result.z = (vec1.x * vec2.y) - (vec1.y * vec2.x);
		
		return result;
	}
	
	public static Vector3 normalize(Vector3 result, Vector3 vec)
	{
		float length = vec.getMagnituded();
		result.set(vec.x / length, vec.y / length, vec.z / length);
		
		return result;
	}
}
