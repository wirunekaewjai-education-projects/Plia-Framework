package library.graphics;

public class Vector3
{
	public float x, y, z;

	public Vector3()
	{
		x = 0;
		y = 0;
		z = 0;
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

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "x : " + x + ", y : " + y + ", z : " + z;
	}

	public Vector3 asUnitVector()
	{
		float size = (float) Math.sqrt((x * x) + (y * y) + (z * z));

		return new Vector3(x / size, y / size, z / size);
	}

	public static Vector3 add(Vector3 vec1, Vector3 vec2)
	{
		return new Vector3(vec1.x + vec2.x, vec1.y + vec2.y, vec1.z + vec2.z);
	}

	public static Vector3 add(float[] vec1, float[] vec2)
	{
		return new Vector3(vec1[0] + vec2[0], vec1[1] + vec2[1], vec1[2]
				+ vec2[2]);
	}

	public static Vector3 minus(Vector3 vec1, Vector3 vec2)
	{
		return new Vector3(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z);
	}

	public static Vector3 minus(float[] vec1, float[] vec2)
	{
		return new Vector3(vec1[0] - vec2[0], vec1[1] - vec2[1], vec1[2]
				- vec2[2]);
	}

	public static Vector3 multiply(Vector3 vec, float value)
	{
		return new Vector3(vec.x * value, vec.y * value, vec.z * value);
	}

	public static Vector3 cross(Vector3 vec1, Vector3 vec2)
	{
		Vector3 result = new Vector3();

		result.x = (vec1.y * vec2.z) - (vec1.z * vec2.y);
		result.y = (vec1.z * vec2.x) - (vec1.x * vec2.z);
		result.z = (vec1.x * vec2.y) - (vec1.y * vec2.x);

		return result;
	}

	public static Vector3 cross(float[] vec1, float[] vec2)
	{
		Vector3 result = new Vector3();

		result.x = (vec1[1] * vec2[2]) - (vec1[2] * vec2[1]);
		result.y = (vec1[2] * vec2[0]) - (vec1[0] * vec2[2]);
		result.z = (vec1[0] * vec2[1]) - (vec1[1] * vec2[0]);

		return result;
	}

	public static float dot(Vector3 vec1, Vector3 vec2)
	{
		return ((vec1.x * vec2.x) + (vec1.y * vec2.y) + (vec1.z * vec2.z));
	}

	public static float dot(float[] vec1, float[] vec2)
	{
		return ((vec1[0] * vec2[0]) + (vec1[1] * vec2[1]) + (vec1[2] * vec2[2]));
	}

	public static float distance(Vector3 vec1, Vector3 vec2)
	{
		Vector3 vec = new Vector3((vec1.x - vec2.x), (vec1.y - vec2.y),
				(vec1.z - vec2.z));
		return (float) Math.sqrt(dot(vec, vec));
	}

	public static Vector3 convertMatrixToAngle(float[] matrix)
	{
//		Quaternion q1 = Quaternion.convertMatrixToQuaternion(matrix);
//
//		float heading, attitude, bank;
//		// y = (float)( Math.toDegrees(Math.atan2(2.0f*q.y*q.w-2.0f*q.x*q.z,
//		// 1.0f-2.0f*q.y*q.y-2*q.z*q.z)) );
//		// z = (float)( Math.toDegrees(Math.asin(2.0f*q.x*q.y+2.0f*q.z*q.w)) );
//		// x = (float)( Math.toDegrees(Math.atan2(2.0*q.x*q.w-2*q.y*q.z,
//		// 1.0f-2.0f*q.x*q.x-2.0f*q.z*q.z)) );
//
//		double sqw = q1.w * q1.w;
//		double sqx = q1.x * q1.x;
//		double sqy = q1.y * q1.y;
//		double sqz = q1.z * q1.z;
//		double unit = sqx + sqy + sqz + sqw; // if normalised is one, otherwise
//												// is correction factor
//		double test = q1.x * q1.y + q1.z * q1.w;
//		if (test > 0.499 * unit)
//		{ // singularity at north pole
//			heading = (float) (2 * Math.atan2(q1.x, q1.w));
//			attitude = (float) (Math.PI / 2);
//			bank = 0;
//		} else if (test < -0.499 * unit)
//		{ // singularity at south pole
//			heading = (float) (-2 * Math.atan2(q1.x, q1.w));
//			attitude = (float) (-Math.PI / 2);
//			bank = 0;
//		} else
//		{
//			heading = (float) Math.atan2(2 * q1.y * q1.w - 2 * q1.x * q1.z, sqx
//					- sqy - sqz + sqw);
//			attitude = (float) Math.asin(2 * test / unit);
//			bank = (float) Math.atan2(2 * q1.x * q1.w - 2 * q1.y * q1.z, -sqx
//					+ sqy - sqz + sqw);
//		}
//
//		float x, y, z;
//		x = (float) Math.toDegrees(bank);
//		y = (float) Math.toDegrees(heading);
//		z = (float) Math.toDegrees(attitude);

		return convertQuaternionToAngle(Quaternion.convertMatrixToQuaternion(matrix));
	}
	
	public static Vector3 convertQuaternionToAngle(Quaternion q1)
	{
		float heading, attitude, bank;
		// y = (float)( Math.toDegrees(Math.atan2(2.0f*q.y*q.w-2.0f*q.x*q.z,
		// 1.0f-2.0f*q.y*q.y-2*q.z*q.z)) );
		// z = (float)( Math.toDegrees(Math.asin(2.0f*q.x*q.y+2.0f*q.z*q.w)) );
		// x = (float)( Math.toDegrees(Math.atan2(2.0*q.x*q.w-2*q.y*q.z,
		// 1.0f-2.0f*q.x*q.x-2.0f*q.z*q.z)) );

		double sqw = q1.w * q1.w;
		double sqx = q1.x * q1.x;
		double sqy = q1.y * q1.y;
		double sqz = q1.z * q1.z;
		double unit = sqx + sqy + sqz + sqw; // if normalised is one, otherwise
												// is correction factor
		double test = q1.x * q1.y + q1.z * q1.w;
		if (test > 0.499 * unit)
		{ // singularity at north pole
			heading = (float) (2 * Math.atan2(q1.x, q1.w));
			attitude = (float) (Math.PI / 2);
			bank = 0;
		} else if (test < -0.499 * unit)
		{ // singularity at south pole
			heading = (float) (-2 * Math.atan2(q1.x, q1.w));
			attitude = (float) (-Math.PI / 2);
			bank = 0;
		} else
		{
			heading = (float) Math.atan2(2 * q1.y * q1.w - 2 * q1.x * q1.z, sqx
					- sqy - sqz + sqw);
			attitude = (float) Math.asin(2 * test / unit);
			bank = (float) Math.atan2(2 * q1.x * q1.w - 2 * q1.y * q1.z, -sqx
					+ sqy - sqz + sqw);
		}

		float x, y, z;
		x = (float) Math.toDegrees(bank);
		y = (float) Math.toDegrees(heading);
		z = (float) Math.toDegrees(attitude);

		return new Vector3(x, y, z);
	}

	public static final Vector3 zero = new Vector3(0, 0, 0);

	// Direction
	public static final Vector3 left = new Vector3(1, 0, 0);
	public static final Vector3 right = new Vector3(-1, 0, 0);
	public static final Vector3 up = new Vector3(0, 1, 0);
	public static final Vector3 down = new Vector3(0, -1, 0);
	public static final Vector3 forward = new Vector3(0, 0, 1);
	public static final Vector3 backward = new Vector3(0, 0, -1);
}
