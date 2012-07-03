package airtaro.math;

public final class Quaternion
{
	// Properties
	public float w, x, y, z;
	
	// Constructor
	public Quaternion()
	{

	}
	
	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(Vector3 vectorPart, float scalarPart)
	{
		this.x = vectorPart.x;
		this.y = vectorPart.y;
		this.z = vectorPart.z;
		this.w = scalarPart;
	}
	
	@Override
	public String toString()
	{
		float xx = ( Math.round(x * 1000) ) / 1000f;
		float yy = ( Math.round(y * 1000) ) / 1000f;
		float zz = ( Math.round(z * 1000) ) / 1000f;
		float ww = ( Math.round(w * 1000) ) / 1000f;
		
		return "X : "+xx+", Y : "+yy+", Z : "+zz+", W : "+ww;
	}
	
	@Override
	public Quaternion clone()
	{
		return new Quaternion(x, y, z, w);
	}
	
	public void copyTo(float[] q)
	{
		q[0] = x;
		q[1] = y;
		q[2] = z;
		q[3] = w;
	}
	
	public Matrix3 toMatrix3()
	{
		float _2xx = 2*x*x;
		float _2yy = 2*y*y;
		float _2zz = 2*z*z;
		
		float _2xy = 2*x*y;
		float _2xz = 2*x*z;
		float _2yz = 2*y*z;
		
		float _2xw = 2*x*w;
		float _2yw = 2*y*w;
		float _2zw = 2*z*w;
		
		float _1sub2xx = 1.0f - _2xx;

		Matrix3 result = new Matrix3();
		
		result.m11 = 1.0f - _2yy - _2zz;
		result.m12 = _2xy + _2zw;
		result.m13 = _2xz - _2yw;

		result.m21 = _2xy - _2zw;
		result.m22 = _1sub2xx - _2zz;
		result.m23 = _2yz + _2xw;

		result.m31 = _2xz + _2yw;
		result.m32 = _2yz - _2xw;
		result.m33 = _1sub2xx - _2yy;

		return result;
	}
	
	public Matrix4 toMatrix4()
	{
		float _2xx = 2*x*x;
		float _2yy = 2*y*y;
		float _2zz = 2*z*z;
		
		float _2xy = 2*x*y;
		float _2xz = 2*x*z;
		float _2yz = 2*y*z;
		
		float _2xw = 2*x*w;
		float _2yw = 2*y*w;
		float _2zw = 2*z*w;
		
		float _1sub2xx = 1.0f - _2xx;

		Matrix4 result = new Matrix4();
		
		result.m11 = 1.0f - _2yy - _2zz;
		result.m12 = _2xy + _2zw;
		result.m13 = _2xz - _2yw;
		result.m14 = 0;
		
		result.m21 = _2xy - _2zw;
		result.m22 = _1sub2xx - _2zz;
		result.m23 = _2yz + _2xw;
		result.m24 = 0;
		
		result.m31 = _2xz + _2yw;
		result.m32 = _2yz - _2xw;
		result.m33 = _1sub2xx - _2yy;
		result.m34 = 0;

		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		result.m44 = 1;
		
		return result;
	}

	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Quaternion quaternion)
	{
		this.x = quaternion.x;
		this.y = quaternion.y;
		this.z = quaternion.z;
		this.w = quaternion.w;
	}
	
	public void set(Vector3 vectorPart, float scalarPart)
	{
		this.x = vectorPart.x;
		this.y = vectorPart.y;
		this.z = vectorPart.z;
		this.w = scalarPart;
	}
	
	// Methods
	public Quaternion getConjugate()
	{
		return new Quaternion(-x, -y, -z, w);
	}
	
	public Quaternion getNormalize()
	{
		float magnitude = getMagnitude();
		
		Quaternion normalize = new Quaternion();
		
		normalize.x = x/magnitude;
		normalize.y = y/magnitude;
		normalize.z = z/magnitude;
		normalize.w = w/magnitude;

		return normalize;
	}
	
	public Quaternion getInverse()
	{
		float length = (w*w)+(x*x)+(y*y)+(z*z);
		
		Quaternion inverse = new Quaternion();
		
		inverse.x = (-x)/length;
		inverse.y = (-y)/length;
		inverse.z = (-z)/length;
		inverse.w = w/length;

		return inverse;
	}
	
	public float getMagnitude()
	{
		return (float) Math.sqrt((w*w)+(x*x)+(y*y)+(z*z));
	}

	public Quaternion multiply(Quaternion b)
	{
		float bx = b.x;
		float by = b.y;
		float bz = b.z;
		float bw = b.w;
		
		float ax = x;
		float ay = y;
		float az = z;
		float aw = w;

		x = (bx * aw) + (ax * bw) + ((ay * bz) - (az * by));
		y = (by * aw) + (ay * bw) + ((az * bx) - (ax * bz));
		z = (bz * aw) + (az * bw) + ((ax * by) - (ay * bx));
		
		w = (aw*bw) - (ax*bx) + (ay*by) + (az*bz);
		
		return this;
	}

	public Vector3 getEulerAngles()
	{
		float heading, attitude, bank;
		
		float test = (x*y) + (z*w);
		
	    float sqx = 2*x*x;
	    float sqy = 2*y*y;
	    float sqz = 2*z*z;
	    
	    heading = (float) Math.atan2(2*y*w - 2*x*z, 1 - sqy - sqz);
		attitude = (float) Math.asin(2 * test);
		bank = (float) Math.atan2(2*x*w - 2*y*z , 1 - sqx - sqz);

		Vector3 result = new Vector3();
		// Radian to Degree
		result.x = bank * 57.29578f;
		result.y = heading * 57.29578f;
		result.z = attitude * 57.29578f;
		
		return result;
	}
	
	private Vector3 getDirection(float dx, float dy, float dz)
	{
		float length = (w*w)+(x*x)+(y*y)+(z*z);
		float norm = (float) Math.sqrt(length);

		float nx = x/norm;
		float ny = y/norm;
		float nz = z/norm;
		float nw = w/norm;
		
		float ix = (-nx)/length;
		float iy = (-ny)/length;
		float iz = (-nz)/length;
		float iw = nw/length;
		
		// Inverse * Direction
		float cx = (dx * iw) + ((iy * dz) - (iz * dy));
		float cy = (dy * iw) + ((iz * dx) - (ix * dz));
		float cz = (dz * iw) + ((ix * dy) - (iy * dx));
		
		float cw = -((ix * dx) + (iy * dy) + (iz * dz));

		// (Inverse * Direction) * Normalize
		float x = (nx * cw) + (cx * nw) + (cy * nz) - (cz * ny);
		float y = (ny * cw) + (cy * nw) + (cz * nx) - (cx * nz);
		float z = (nz * cw) + (cz * nw) + (cx * ny) - (cy * nx);

		Vector3 direction = new Vector3();
		
		direction.x = x;
		direction.y = y;
		direction.z = z;
		
		return direction;
	}
	
	public Vector3 getForward()
	{
		return getDirection(0, 1, 0);
	}

	public Vector3 getBackward()
	{
		return getDirection(0, -1, 0);
	}

	public Vector3 getUp()
	{
		return getDirection(0, 0, 1);
	}

	public Vector3 getDown()
	{
		return getDirection(0, 0, -1);
	}

	public Vector3 getLeft()
	{
		return getDirection(-1, 0, 0);
	}

	public Vector3 getRight()
	{
		return getDirection(1, 0, 0);
	}
	
	// Classifier Methods
	public static Quaternion add(Quaternion a, Quaternion b)
	{
		Quaternion result = new Quaternion();
		
		result.x = a.x + b.x;
		result.y = a.y + b.y;
		result.z = a.z + b.z;
		result.w = a.w + b.w;
		
		return result;
	}

	public static Quaternion subtract(Quaternion a, Quaternion b)
	{
		Quaternion result = new Quaternion();
		
		result.x = a.x - b.x;
		result.y = a.y - b.y;
		result.z = a.z - b.z;
		result.w = a.w - b.w;
		
		return result;
	}
	
	public static Quaternion multiply(Quaternion a, Quaternion b)
	{
		Quaternion result = new Quaternion();

		result.x = (b.x * a.w) + (a.x * b.w) + ((a.y * b.z) - (a.z * b.y));
		result.y = (b.y * a.w) + (a.y * b.w) + ((a.z * b.x) - (a.x * b.z));
		result.z = (b.z * a.w) + (a.z * b.w) + ((a.x * b.y) - (a.y * b.x));
		result.w = (a.w * b.w) - ((a.x * b.x) + (a.y * b.y) + (a.z * b.z));

		return result;
	}

	public static void createFromEulerAngles(Quaternion result, float ax, float ay, float az)
	{
		float X2 = (ax / 2) * 0.0174533f;
		float Z2 = (az / 2) * 0.0174533f;
		float Y2 = (ay / 2) * 0.0174533f;

		// Heading ( Yaw :: Y Axis ) ; First
		float c1 = (float) Math.cos(Y2);
		float s1 = (float) Math.sin(Y2);

		// Attitude ( Pitch :: Z Axis ) ; Second
		float c2 = (float) Math.cos(Z2);
		float s2 = (float) Math.sin(Z2);

		// Bank ( Roll :: X Axis ) ; Last
		float c3 = (float) Math.cos(X2);
		float s3 = (float) Math.sin(X2);

		float c1c2 = c1 * c2;
		float s1s2 = s1 * s2;

		result.w = (c1c2*c3  - s1s2*s3);
		result.x = (c1c2*s3  + s1s2*c3);
		result.y = (s1*c2*c3 + c1*s2*s3);
		result.z = (c1*s2*c3 - s1*c2*s3);
	}
	
	public static void slerp(Quaternion result, Quaternion from, Quaternion to, float t)
	{
		// Calculate angle between them.
		float cosHalfTheta = from.w * to.w + from.x * to.x + from.y * to.y + from.z * to.z;

		// Calculate temporary values.
		float halfTheta = (float) Math.acos(cosHalfTheta);
		float sinHalfTheta = (float) Math.sqrt(1.0 - cosHalfTheta*cosHalfTheta);

		if (Math.abs(cosHalfTheta) >= 1.0)
		{
			// if qa=qb or qa=-qb then theta = 0 and we can return qa
			result.w = from.w;
			result.x = from.x;
			result.y = from.y;
			result.z = from.z;
		}
		else if (Math.abs(sinHalfTheta) < 0.001)
		{ 
			// if theta = 180 degrees then result is not fully defined
			// we could rotate around any axis normal to qa or qb
			result.w = (from.w * 0.5f + to.w * 0.5f);
			result.x = (from.x * 0.5f + to.x * 0.5f);
			result.y = (from.y * 0.5f + to.y * 0.5f);
			result.z = (from.z * 0.5f + to.z * 0.5f);
		}
		else
		{
			float ratioA = (float) (Math.sin((1 - t) * halfTheta) / sinHalfTheta);
			float ratioB = (float) (Math.sin(t * halfTheta) / sinHalfTheta); 
			//calculate Quaternion.
			result.w = (from.w * ratioA + to.w * ratioB);
			result.x = (from.x * ratioA + to.x * ratioB);
			result.y = (from.y * ratioA + to.y * ratioB);
			result.z = (from.z * ratioA + to.z * ratioB);
		}
	}
}
