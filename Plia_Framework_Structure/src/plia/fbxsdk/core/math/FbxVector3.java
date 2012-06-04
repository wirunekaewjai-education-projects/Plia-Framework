package plia.fbxsdk.core.math;

public class FbxVector3
{
	// Properties
	public float x;
	public float y;
	public float z;

	// Constructor
	public FbxVector3()
	{

	}

	public FbxVector3(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float xx = (Math.round(x * 1000)) / 1000f;
		float yy = (Math.round(y * 1000)) / 1000f;
		float zz = (Math.round(z * 1000)) / 1000f;

		return "X : " + xx + ", Y : " + yy + ", Z : " + zz;
	}

	// Methods
	public float getMagnitude()
	{
		return (float) Math.sqrt((x * x) + (y * y) + (z * z));
	}

	public FbxVector3 getNormalize()
	{
		float length = getMagnitude();
		return new FbxVector3(x / length, y / length, z / length);
	}

	// Classifier Method
	public static final float dot(FbxVector3 vec1, FbxVector3 vec2)
	{
		return ((vec1.x * vec2.x) + (vec1.y * vec2.y) + (vec1.z * vec2.z));
	}

	public static float distance(FbxVector3 vec1, FbxVector3 vec2)
	{
		float dx = vec1.x - vec2.x;
		float dy = vec1.y - vec2.y;
		float dz = vec1.z - vec2.z;

		return (float) Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
	}

	public static void add(FbxVector3 result, FbxVector3 vec1, FbxVector3 vec2)
	{
		result.x = vec1.x + vec2.x;
		result.y = vec1.y + vec2.y;
		result.z = vec1.z + vec2.z;
	}

	public static void subtract(FbxVector3 result, FbxVector3 vec1, FbxVector3 vec2)
	{
		result.x = vec1.x - vec2.x;
		result.y = vec1.y - vec2.y;
		result.z = vec1.z - vec2.z;
	}

	public static void scale(FbxVector3 result, FbxVector3 vec, float value)
	{
		result.x = vec.x * value;
		result.y = vec.y * value;
		result.z = vec.z * value;
	}

	public static void cross(FbxVector3 result, FbxVector3 vec1, FbxVector3 vec2)
	{
		result.x = (vec1.y * vec2.z) - (vec1.z * vec2.y);
		result.y = (vec1.z * vec2.x) - (vec1.x * vec2.z);
		result.z = (vec1.x * vec2.y) - (vec1.y * vec2.x);
	}

	public static FbxVector3 add(FbxVector3 vec1, FbxVector3 vec2)
	{
		FbxVector3 result = new FbxVector3();
		add(result, vec1, vec2);
		return result;
	}

	public static FbxVector3 subtract(FbxVector3 vec1, FbxVector3 vec2)
	{
		FbxVector3 result = new FbxVector3();
		subtract(result, vec1, vec2);
		return result;
	}

	public static FbxVector3 scale(FbxVector3 vec, float value)
	{
		FbxVector3 result = new FbxVector3();
		scale(result, vec, value);
		return result;
	}

	public static FbxVector3 cross(FbxVector3 vec1, FbxVector3 vec2)
	{
		FbxVector3 result = new FbxVector3();
		cross(result, vec1, vec2);
		return result;
	}

	// Additional
	public static void lerp(FbxVector3 result, FbxVector3 start, FbxVector3 end, float t)
	{
		// P = P0 + tv
		result.x = start.x + ((end.x - start.x) * t);
		result.y = start.y + ((end.y - start.y) * t);
		result.z = start.z + ((end.z - start.z) * t);
	}

	public static FbxVector3 lerp(FbxVector3 start, FbxVector3 end, float t)
	{
		FbxVector3 result = new FbxVector3();
		lerp(result, start, end, t);
		return result;
	}
}
