package plia.framework.math;

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
		return Mathf.sqrt((w*w)+(x*x)+(y*y)+(z*z));
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
	
	// Void Method
	public void getEulerAngles(Vector3 result)
	{
		float heading, attitude, bank;
		
		float test = (x*y) + (z*w);
		
	    float sqx = 2*x*x;
	    float sqy = 2*y*y;
	    float sqz = 2*z*z;
	    
	    heading = Mathf.atan2(2*y*w - 2*x*z, 1 - sqy - sqz);
		attitude = Mathf.asin(2 * test);
		bank = Mathf.atan2(2*x*w - 2*y*z , 1 - sqx - sqz);

		result.x = bank;
		result.y = heading;
		result.z = attitude;
	}
	
	private void getDirection(Vector3 direction, float dx, float dy, float dz)
	{
		float length = (w*w)+(x*x)+(y*y)+(z*z);
		float norm = Mathf.sqrt(length);

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

		direction.x = x;
		direction.y = y;
		direction.z = z;
	}
	
	public void getForward(Vector3 result)
	{
		getDirection(result, 0, 1, 0);
	}

	public void getBackward(Vector3 result)
	{
		getDirection(result, 0, -1, 0);
	}

	public void getUp(Vector3 result)
	{
		getDirection(result, 0, 0, 1);
	}

	public void getDown(Vector3 result)
	{
		getDirection(result, 0, 0, -1);
	}

	public void getLeft(Vector3 result)
	{
		getDirection(result, -1, 0, 0);
	}

	public void getRight(Vector3 result)
	{
		getDirection(result, 1, 0, 0);
	}
	
	// Return Method
	public Vector3 getEulerAngles()
	{
		Vector3 eulerAngles = new Vector3();
		getEulerAngles(eulerAngles);
		return eulerAngles;
	}
	
	public Vector3 getDirection(float dx, float dy, float dz)
	{
		Vector3 direction = new Vector3();
		getDirection(direction, dx, dy, dz);
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
	public static void add(Quaternion result, Quaternion a, Quaternion b)
	{
		result.x = a.x + b.x;
		result.y = a.y + b.y;
		result.z = a.z + b.z;
		result.w = a.w + b.w;
	}

	public static void subtract(Quaternion result, Quaternion a, Quaternion b)
	{
		result.x = a.x - b.x;
		result.y = a.y - b.y;
		result.z = a.z - b.z;
		result.w = a.w - b.w;
	}
	
	public static void multiply(Quaternion result, Quaternion a, Quaternion b)
	{
		float ax = a.x;
		float ay = a.y;
		float az = a.z;
		float aw = a.w;
		
		float bx = b.x;
		float by = b.y;
		float bz = b.z;
		float bw = b.w;

		result.x = (bx * aw) + (ax * bw) + ((ay * bz) - (az * by));
		result.y = (by * aw) + (ay * bw) + ((az * bx) - (ax * bz));
		result.z = (bz * aw) + (az * bw) + ((ax * by) - (ay * bx));
		result.w = (aw * bw) - ((ax * bx) + (ay * by) + (az * bz));
	}

	public static void createFromMatrix(Quaternion result, float[] m)
	{
		float x = 0, y = 0, z = 0, w = 0;
		
		/*
			M11 = m[0],	M21 = m[4], M31 = m[8],  M41 = m[12]
			
			M12 = m[1], M22 = m[5], M32 = m[9],  M42 = m[13]
			
			M13 = m[2], M23 = m[6], M33 = m[10], M43 = m[14]
			
			M14 = m[3], M24 = m[7], M34 = m[11], M44 = m[15]
		 */
		
		if(m[0] + m[5] + m[10] > 0)
		{
			float S = Mathf.sqrt(m[0] + m[5] + m[10] + 1.0) * 2.0f;
			w = 0.25f * S;
			x = (m[6] - m[9]) / S;
			y = (m[8] - m[2]) / S;
			z = (m[1] - m[4]) / S;
		}
		else if(m[0] > m[5] && m[0] > m[10])
		{
			float S = Mathf.sqrt(1.0 + m[0] - m[5] - m[10]) * 2.0f;
			w = (m[6] - m[9]) / S;
			x = 0.25f * S;
			y = (m[4] + m[1]) / S;
			z = (m[8] + m[2]) / S;
		}
		else if(m[5] > m[10])
		{
			float S = Mathf.sqrt(1.0 + m[5] - m[0] - m[10]) * 2.0f;
			w = (m[8] - m[2]) / S;
			x = (m[4] + m[1]) / S;
			y = 0.25f * S;
			z = (m[9] + m[6])/S;
		}
		else
		{
			float S = Mathf.sqrt(1.0 + m[10] - m[0] - m[5]) * 2.0f;
			w = (m[1] - m[4]) / S;
			x = (m[8] + m[2]) / S;
			y = (m[9] + m[6]) / S;
			z = 0.25f * S;
		}
		
		result.x = x;
		result.y = y;
		result.z = z;
		result.w = w;
	}

	public static void createFromEulerAngles(Quaternion result, float ax, float ay, float az)
	{
		float X2 = ax / 2;
		float Z2 = az / 2;
		float Y2 = ay / 2;

		// Heading ( Yaw :: Y Axis ) ; First
		float c1 = Mathf.cos(Y2);
		float s1 = Mathf.sin(Y2);

		// Attitude ( Pitch :: Z Axis ) ; Second
		float c2 = Mathf.cos(Z2);
		float s2 = Mathf.sin(Z2);

		// Bank ( Roll :: X Axis ) ; Last
		float c3 = Mathf.cos(X2);
		float s3 = Mathf.sin(X2);

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
		float sinHalfTheta = Mathf.sqrt(1.0 - cosHalfTheta*cosHalfTheta);
		
		
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
