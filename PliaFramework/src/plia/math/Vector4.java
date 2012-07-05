package plia.math;

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
	
	public Vector4(Vector4 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = vec.w;
	}
	
	public Vector4(Vector3 vec, float w)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = w;
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
	
	@Override
	public Vector4 clone()
	{
		return new Vector4(x, y, z, w);
	}
	
	public void copyTo(float[] v)
	{
		v[0] = x;
		v[1] = y;
		v[2] = z;
		v[3] = w;
	}

	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Vector4 vec)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = vec.w;
	}
	
	public void set(Vector3 vec, float w)
	{
		this.x = vec.x;
		this.y = vec.y;
		this.z = vec.z;
		this.w = w;
	}

	// Methods
	public float getMagnituded()
	{
		return (float) Math.sqrt((x * x) + (y * y) + (z * z) + (w * w));
	}

	public Vector4 getNormalized()
	{
		float length = getMagnituded();
		return new Vector4(x / length, y / length, z / length, w / length);
	}

	public Vector4 add(Vector4 vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		this.w += vector.w;
		
		return this;
	}
	
	public Vector4 subtract(Vector4 vector)
	{
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		this.w -= vector.w;
		
		return this;
	}
	
	public Vector4 scale(float value)
	{
		this.x *= value;
		this.y *= value;
		this.z *= value;
		this.w *= value;
		
		return this;
	}
	
	// Classifier Method
	public static float dot(Vector4 vec1, Vector4 vec2)
	{
		return ((vec1.x * vec2.x) + (vec1.y * vec2.y) + (vec1.z * vec2.z) + (vec1.w * vec2.w));
	}
	
	public static Vector4 add(Vector4 vec1, Vector4 vec2)
	{
		Vector4 result = new Vector4();
		
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
		result.z = vec1.z + vec2.z;
		result.w = vec1.w + vec2.w;
		
		return result;
	}
	
	public static Vector4 subtract(Vector4 vec1, Vector4 vec2)
	{
		Vector4 result = new Vector4();
		
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
		result.z = vec1.z - vec2.z;
		result.w = vec1.w - vec2.w;
		
		return result;
	}
	
	public static Vector4 scale(Vector4 vec, float value)
	{
		Vector4 result = new Vector4();
		
		result.x = vec.x * value;
		result.y = vec.y * value;
		result.z = vec.z * value;
		result.w = vec.w * value;
		
		return result;
	}
	
	public static Vector4 lerp(Vector4 start, Vector4 end, float t)
	{
		Vector4 result = new Vector4();
		
		// P = P0 + tv
		result.x = start.x + ((end.x - start.x) * t);
		result.y = start.y + ((end.y - start.y) * t);
		result.z = start.z + ((end.z - start.z) * t);
		result.w = start.w + ((end.w - start.w) * t);
		
		return result;
	}
	
	public static Vector4 normalize(Vector4 result, Vector4 vec)
	{
		float length = vec.getMagnituded();
		result.set(vec.x / length, vec.y / length, vec.z / length, vec.w / length);
		
		return result;
	}
}
