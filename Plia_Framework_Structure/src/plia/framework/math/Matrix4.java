package plia.framework.math;

import plia.framework.math.Mathf;
import plia.framework.math.Vector4;

public final class Matrix4
{
	/* Reference :: Martin John Baker ( http://www.euclideanspace.com/maths/geometry/rotations/index.htm ) */
	
	/*
		
	*/
	
	// Properties
	public float 	m11, m12, m13, m14, 
					m21, m22, m23, m24, 
					m31, m32, m33, m34, 
					m41, m42, m43, m44;
	
	// Constructor
	public Matrix4()
	{
		
	}
		
	public Matrix4(float M11, float M12, float M13, float M14, float M21,
			float M22, float M23, float M24, float M31, float M32, float M33,
			float M34, float M41, float M42, float M43, float M44)
	{
		m11 = M11;
		m12 = M12;
		m13 = M13;
		m14 = M14;
		
		m21 = M21;
		m22 = M22;
		m23 = M23;
		m24 = M24;
		
		m31 = M31;
		m32 = M32;
		m33 = M33;
		m34 = M34;
		
		m41 = M41;
		m42 = M42;
		m43 = M43;
		m44 = M44;
	}
	
	public Matrix4(float[] m)
	{
		m11 = m[0];
		m12 = m[1];
		m13 = m[2];
		m14 = m[3];
		
		m21 = m[4];
		m22 = m[5];
		m23 = m[6];
		m24 = m[7];
		
		m31 = m[8];
		m32 = m[9];
		m33 = m[10];
		m34 = m[11];
		
		m41 = m[12];
		m42 = m[13];
		m43 = m[14];
		m44 = m[15];
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float mm11 = ( Math.round(m11 * 1000) ) / 1000f;
		float mm12 = ( Math.round(m12 * 1000) ) / 1000f;
		float mm13 = ( Math.round(m13 * 1000) ) / 1000f;
		float mm14 = ( Math.round(m14 * 1000) ) / 1000f;
		
		float mm21 = ( Math.round(m21 * 1000) ) / 1000f;
		float mm22 = ( Math.round(m22 * 1000) ) / 1000f;
		float mm23 = ( Math.round(m23 * 1000) ) / 1000f;
		float mm24 = ( Math.round(m24 * 1000) ) / 1000f;
		
		float mm31 = ( Math.round(m31 * 1000) ) / 1000f;
		float mm32 = ( Math.round(m32 * 1000) ) / 1000f;
		float mm33 = ( Math.round(m33 * 1000) ) / 1000f;
		float mm34 = ( Math.round(m34 * 1000) ) / 1000f;
		
		float mm41 = ( Math.round(m41 * 1000) ) / 1000f;
		float mm42 = ( Math.round(m42 * 1000) ) / 1000f;
		float mm43 = ( Math.round(m43 * 1000) ) / 1000f;
		float mm44 = ( Math.round(m44 * 1000) ) / 1000f;
		
		return 	"{M11 : "+mm11+", M12 : "+mm12+", M13 : "+mm13+", M14 : "+mm14+"}\n"+
				"{M21 : "+mm21+", M22 : "+mm22+", M23 : "+mm23+", M24 : "+mm24+"}\n"+
				"{M31 : "+mm31+", M32 : "+mm32+", M33 : "+mm33+", M34 : "+mm34+"}\n"+
				"{M41 : "+mm41+", M42 : "+mm42+", M43 : "+mm43+", M44 : "+mm44+"}";
	}
	
	// Methods
	public float getDeterminant()
	{
		float a11a22 = m11*m22;
		float a11a23 = m11*m23;
		float a11a24 = m11*m24;
		
		float a12a21 = m12*m21;
		float a12a23 = m12*m23;
		float a12a24 = m12*m24;
		
		float a13a21 = m13*m21;
		float a13a22 = m13*m22;
		float a13a24 = m13*m24;
		
		float a14a21 = m14*m21;
		float a14a22 = m14*m22;
		float a14a23 = m14*m23;

		float a31a42 = m31*m42;
		float a31a43 = m31*m43;
		float a31a44 = m31*m44;

		float a32a41 = m32*m41;
		float a32a43 = m32*m43;
		float a32a44 = m32*m44;
		
		float a33a41 = m33*m41;
		float a33a42 = m33*m42;
		float a33a44 = m33*m44;

		float a34a41 = m34*m41;
		float a34a42 = m34*m42;
		float a34a43 = m34*m43;
		
		float det =  (a11a22*a33a44)+(a11a23*a34a42)+(a11a24*a32a43)
					+(a12a21*a34a43)+(a12a23*a31a44)+(a12a24*a33a41)
					+(a13a21*a32a44)+(a13a22*a34a41)+(a13a24*a31a42)
					+(a14a21*a33a42)+(a14a22*a31a43)+(a14a23*a32a41)
					-(a11a22*a34a43)-(a11a23*a32a44)-(a11a24*a33a42)
					-(a12a21*a33a44)-(a12a23*a34a41)-(a12a24*a31a43)
					-(a13a21*a34a42)-(a13a22*a31a44)-(a13a24*a32a41)
					-(a14a21*a32a43)-(a14a22*a33a41)-(a14a23*a31a42);
		
		return det;
	}

	// Void Method
	public void getEulerAngles(Vector3 result)
	{
		float heading, attitude, bank;

		if (m12 > 0.998f) { // singularity at north pole
			heading = Mathf.atan2(m31,m22);
			attitude = Mathf.PI/2;
			bank = 0;

		}
		else if (m12 < -0.998f) { // singularity at south pole
			heading = Mathf.atan2(m31,m22);
			attitude = -Mathf.PI/2;
			bank = 0;
		}
		else
		{
			heading = Mathf.atan2(-m13,m11);
			bank = Mathf.atan2(-m32,m22);
			attitude = Mathf.asin(m12);
		}

		result.x = bank;
		result.y = heading;
		result.z = attitude;
	}
	
	public void getTranslation(Vector3 result)
	{
		result.x = m41;
		result.y = m42;
		result.z = m43;
	}

	public void getForward(Vector3 result)
	{
		result.x = m21;
		result.y = m22;
		result.z = m23;
	}

	public void getUp(Vector3 result)
	{
		result.x = m31;
		result.y = m32;
		result.z = m33;
	}

	public void getRight(Vector3 result)
	{
		result.x = m11;
		result.y = m12;
		result.z = m13;
	}
	
	public void setForward(Vector3 direction)
	{
		setForward(direction.x, direction.y, direction.z);
	}
	
	public void setLookAt(float x, float y, float z)
	{
		float lx = x - m41;
		float ly = y - m42;
		float lz = z - m43;
		
		setForward(lx, ly, lz);
	}
	
	public void setForward(float x, float y, float z)
	{
		// ** Right-Hand Coordinate **
		// 1. Normalized Forward-Direction ( Y-Axis )
		// 2. Calculate Right-Direction and Up-Direction ( X-Axis, Z-Axis )
		// 3. Set Matrix Attribute
		// 		: m11, m12, m13 = xAxis
		// 		: m21, m22, m23 = yAxis
		// 		: m31, m32, m33 = zAxis
		// 		: m41, m42, m43 = translation
		
		float magnitude = (float) Math.sqrt((x*x) + (y*y) + (z*z));
		
		float yAxisX = x/magnitude;
		float yAxisY = y/magnitude;
		float yAxisZ = z/magnitude;
		
		float xAxisX = 0, xAxisY = 0, xAxisZ = 0;
		float zAxisX = 0, zAxisY = 0, zAxisZ = 0;
		
		
		if(yAxisX != 0 || yAxisY != 0)
		{
			// 1. Cross (Y-Axis, Z-Axis) equal Right-Direction ( X-Axis )
			// 		* Z-Axis Vector = 0, 0, 1
			// 2. Cross (X-Axis, Y-Axis) equal Up-Direction ( Z-Axis )
			// 3. Normalize Up-Direction ( X-Axis )
			
			xAxisX = yAxisY;
			xAxisY = -yAxisX;
			
			magnitude = (float) Math.sqrt((xAxisX*xAxisX) + (xAxisY*xAxisY));
			xAxisX /= magnitude;
			xAxisY /= magnitude;
			
			zAxisX = xAxisY * yAxisZ;
			zAxisY = -(xAxisX * yAxisZ);
			zAxisZ = (xAxisX * yAxisY) - (xAxisY * yAxisX);
			
			magnitude = (float) Math.sqrt((zAxisX*zAxisX) + (zAxisY*zAxisY) + (zAxisZ*zAxisZ));
			
			zAxisX /= magnitude;
			zAxisY /= magnitude;
			zAxisZ /= magnitude;
		}
		else
		{
			// 1. Cross (X-Axis, Y-Axis) equal Up-Direction ( Z-Axis )
			// 		* X-Axis Vector = 1, 0, 0
			// 2. Normalize Up-Direction ( Z-Axis )
			// 3. Cross (Y-Axis, Z-Axis) equal Right-Direction ( X-Axis )
			// 4. Normalize Right-Direction ( X-Axis )
			
			zAxisY = -yAxisZ;
			zAxisZ = yAxisY;
			
			magnitude = (float) Math.sqrt((zAxisY*zAxisY) + (zAxisZ*zAxisZ));
			zAxisY /= magnitude;
			zAxisZ /= magnitude;
			
			xAxisX = (yAxisY * zAxisZ) - (yAxisZ * zAxisY);
			xAxisY = -(yAxisX * zAxisZ);
			xAxisZ = yAxisX * zAxisY;
			
			magnitude = (float) Math.sqrt((xAxisX*xAxisX) + (xAxisY*xAxisY) + (xAxisZ*xAxisZ));
			
			xAxisX /= magnitude;
			xAxisY /= magnitude;
			xAxisZ /= magnitude;
		}
		
		m11 = xAxisX;
		m12 = xAxisY;
		m13 = xAxisZ;
		m14 = 0;
		
		m21 = yAxisX;
		m22 = yAxisY;
		m23 = yAxisZ;
		m24 = 0;
		
		m31 = zAxisX;
		m32 = zAxisY;
		m33 = zAxisZ;
		m34 = 0;
	}
	
	public void setUp(Vector3 direction)
	{
		setUp(direction.x, direction.y, direction.z);
	}
	
	public void setUp(float x, float y, float z)
	{
		// ** Right-Hand Coordinate **
		// 1. Normalized Up-Direction ( Z-Axis )
		// 2. Calculate Forward-Direction and Right-Direction ( Y-Axis, X-Axis )
		// 5. Set Matrix Attribute
		// 		: m11, m12, m13 = xAxis
		// 		: m21, m22, m23 = yAxis
		// 		: m31, m32, m33 = zAxis
		// 		: m41, m42, m43 = translation
		
		float magnitude = (float) Math.sqrt((x*x) + (y*y) + (z*z));
		
		float zAxisX = x/magnitude;
		float zAxisY = y/magnitude;
		float zAxisZ = z/magnitude;
		
		float xAxisX = 0, xAxisY = 0, xAxisZ = 0;
		float yAxisX = 0, yAxisY = 0, yAxisZ = 0;
		
		if(zAxisY != 0 || zAxisZ != 0)
		{
			// 1. Cross (Z-Axis, X-Axis) equal Forward-Direction ( Y-Axis )
			//		* X-Axis Vector = 1, 0, 0
			// 2. Normalize Forward-Direction ( Y-Axis )
			// 3. Cross (Y-Axis, Z-Axis) equal Right-Direction ( X-Axis )
			// 4. Normalize Right-Direction ( X-Axis )
			
			yAxisY = zAxisZ;
			yAxisZ = -zAxisY;
			
			magnitude = (float) Math.sqrt((yAxisY*yAxisY) + (yAxisZ*yAxisZ));
			yAxisY /= magnitude;
			yAxisZ /= magnitude;
			
			xAxisX = (yAxisY * zAxisZ) - (yAxisZ * zAxisY);
			xAxisY = (yAxisZ * zAxisX);
			xAxisZ = -(yAxisY * zAxisX);
			
			magnitude = (float) Math.sqrt((xAxisX*xAxisX) + (xAxisY*xAxisY) + (xAxisZ*xAxisZ));
			
			xAxisX /= magnitude;
			xAxisY /= magnitude;
			xAxisZ /= magnitude;
		}
		else
		{
			// 1. Cross (Y-Axis, Z-Axis) equal Right-Direction ( X-Axis )
			//		* Y-Axis Vector = 0, 1, 0
			// 2. Normalize Right-Direction ( X-Axis )
			// 3. Cross (Z-Axis, X-Axis) equal Forward-Direction ( Y-Axis )
			// 4. Normalize Forward-Direction ( Y-Axis )
			
			// 1
			xAxisX = zAxisZ;
			xAxisZ = -zAxisX;
			
			// 2
			magnitude = (float) Math.sqrt((xAxisX*xAxisX) + (xAxisZ*xAxisZ));
			xAxisX /= magnitude;
			xAxisZ /= magnitude;
			
			// 3
			yAxisX = zAxisY * xAxisZ;
			yAxisY = (zAxisZ * xAxisX) - (zAxisX * xAxisZ);
			yAxisZ = -(zAxisY * xAxisX);
			
			// 4
			magnitude = (float) Math.sqrt((yAxisX*yAxisX) + (yAxisY*yAxisY) + (yAxisZ*yAxisZ));
			
			yAxisX /= magnitude;
			yAxisY /= magnitude;
			yAxisZ /= magnitude;
		}

		m11 = xAxisX;
		m12 = xAxisY;
		m13 = xAxisZ;
		m14 = 0;
		
		m21 = yAxisX;
		m22 = yAxisY;
		m23 = yAxisZ;
		m24 = 0;
		
		m31 = zAxisX;
		m32 = zAxisY;
		m33 = zAxisZ;
		m34 = 0;
	}
	
	public void setRight(Vector3 direction)
	{
		setRight(direction.x, direction.y, direction.z);
	}
	
	public void setRight(float x, float y, float z)
	{
		// ** Right-Hand Coordinate **
		// 1. Normalized Forward-Direction ( Y-Axis )
		// 2. Calculate Up-Direction and Right-Direction ( Z-Axis, X-Axis )
		// 3. Set Matrix Attribute
		// 		: m11, m12, m13 = xAxis
		// 		: m21, m22, m23 = yAxis
		// 		: m31, m32, m33 = zAxis
		// 		: m41, m42, m43 = translation
		
		float magnitude = (float) Math.sqrt((x*x) + (y*y) + (z*z));
		
		float xAxisX = x/magnitude;
		float xAxisY = y/magnitude;
		float xAxisZ = z/magnitude;
		
		float yAxisX = 0, yAxisY = 0, yAxisZ = 0;
		float zAxisX = 0, zAxisY = 0, zAxisZ = 0;
		
		if(xAxisX != 0 || xAxisZ != 0)
		{
			// 1. Cross (X-Axis, Y-Axis) equal Up-Direction ( Z-Axis )
			// 		* Y-Axis Vector = 0, 1, 0
			// 2. Normalize Up-Direction ( Z-Axis )
			// 3. Cross (Z-Axis, X-Axis) equal Forward-Direction ( Y-Axis )
			// 4. Normalize Forward-Direction ( Y-Axis )
			
			zAxisX = -xAxisZ;
			zAxisZ = xAxisX;
			
			magnitude = (float) Math.sqrt((zAxisX*zAxisX) + (zAxisZ*zAxisZ));
			zAxisX /= magnitude;
			zAxisZ /= magnitude;
			
			yAxisX = -(zAxisZ * xAxisY);
			yAxisY = (zAxisZ * xAxisX) - (zAxisX * xAxisZ);
			yAxisZ = (zAxisX * xAxisY);
			
			magnitude = (float) Math.sqrt((yAxisX*yAxisX) + (yAxisY*yAxisY) + (yAxisZ*yAxisZ));
			
			yAxisX /= magnitude;
			yAxisY /= magnitude;
			yAxisZ /= magnitude;
		}
		else
		{
			// 1. Cross (Z-Axis, X-Axis) equal Forward-Direction ( Y-Axis )
			// 		* Z-Axis Vector = 0, 0, 1
			// 2. Normalize Forward-Direction ( Y-Axis )
			// 3. Cross (X-Axis, Y-Axis) equal Up-Direction ( Z-Axis )
			// 4. Normalize Up-Direction ( Z-Axis )
			
			yAxisX = -xAxisY;
			yAxisY = xAxisX;
			
			magnitude = (float) Math.sqrt((yAxisX*yAxisX) + (yAxisY*yAxisY));
			yAxisX /= magnitude;
			yAxisY /= magnitude;
			
			zAxisX = -(xAxisZ * yAxisY);
			zAxisY = xAxisZ * yAxisX;
			zAxisZ = (xAxisX * yAxisY) - (xAxisY * yAxisX);
			
			magnitude = (float) Math.sqrt((zAxisX*zAxisX) + (zAxisY*zAxisY) + (zAxisZ*zAxisZ));
			
			zAxisX /= magnitude;
			zAxisY /= magnitude;
			zAxisZ /= magnitude;
		}
		
		
		m11 = xAxisX;
		m12 = xAxisY;
		m13 = xAxisZ;
		m14 = 0;
		
		m21 = yAxisX;
		m22 = yAxisY;
		m23 = yAxisZ;
		m24 = 0;
		
		m31 = zAxisX;
		m32 = zAxisY;
		m33 = zAxisZ;
		m34 = 0;
	}
	
	public void setTranslation(Vector3 translation)
	{
		m41 = translation.x;
		m42 = translation.y;
		m43 = translation.z;
	}
	
	public void setTranslation(float x, float y, float z)
	{
		m41 = x;
		m42 = y;
		m43 = z;
	}

	// Return Method
	public Vector3 getTranslation()
	{
		return new Vector3(m41, m42, m43);
	}
	
	public Vector3 getEulerAngles()
	{
		Vector3 eulerAngles = new Vector3();
		getEulerAngles(eulerAngles);
		return eulerAngles;
	}

	public Vector3 getForward()
	{
		Vector3 direction = new Vector3();
		getForward(direction);
		return direction;
	}

	public Vector3 getUp()
	{
		Vector3 direction = new Vector3();
		getUp(direction);
		return direction;
	}

	public Vector3 getRight()
	{
		Vector3 direction = new Vector3();
		getRight(direction);
		return direction;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////

	// Classifier Method
	public static void add(Matrix4 result, Matrix4 lhs, Matrix4 rhs)
	{
		result.m11 = lhs.m11+rhs.m11;
		result.m12 = lhs.m12+rhs.m12;
		result.m13 = lhs.m13+rhs.m13;
		result.m14 = lhs.m14+rhs.m14;

		result.m21 = lhs.m21+rhs.m21;
		result.m22 = lhs.m22+rhs.m22;
		result.m23 = lhs.m23+rhs.m23;
		result.m24 = lhs.m24+rhs.m24;
		
		result.m31 = lhs.m31+rhs.m31;
		result.m32 = lhs.m32+rhs.m32;
		result.m33 = lhs.m33+rhs.m33;
		result.m34 = lhs.m34+rhs.m34;
		
		result.m41 = lhs.m41+rhs.m41;
		result.m42 = lhs.m42+rhs.m42;
		result.m43 = lhs.m43+rhs.m43;
		result.m44 = lhs.m44+rhs.m44;
	}

	public static void subtract(Matrix4 result, Matrix4 lhs, Matrix4 rhs)
	{
		result.m11 = lhs.m11-rhs.m11;
		result.m12 = lhs.m12-rhs.m12;
		result.m13 = lhs.m13-rhs.m13;
		result.m14 = lhs.m14-rhs.m14;

		result.m21 = lhs.m21-rhs.m21;
		result.m22 = lhs.m22-rhs.m22;
		result.m23 = lhs.m23-rhs.m23;
		result.m24 = lhs.m24-rhs.m24;
		
		result.m31 = lhs.m31-rhs.m31;
		result.m32 = lhs.m32-rhs.m32;
		result.m33 = lhs.m33-rhs.m33;
		result.m34 = lhs.m34-rhs.m34;
		
		result.m41 = lhs.m41-rhs.m41;
		result.m42 = lhs.m42-rhs.m42;
		result.m43 = lhs.m43-rhs.m43;
		result.m44 = lhs.m44-rhs.m44;
	}
	
	public static void multiply(Matrix4 result, Matrix4 lhs, Matrix4 rhs)
	{
		float m11 = rhs.m11*lhs.m11 +rhs.m12*lhs.m21 +rhs.m13*lhs.m31 +rhs.m14*lhs.m41;
		float m12 = rhs.m11*lhs.m12 +rhs.m12*lhs.m22 +rhs.m13*lhs.m32 +rhs.m14*lhs.m42;
		float m13 = rhs.m11*lhs.m13 +rhs.m12*lhs.m23 +rhs.m13*lhs.m33 +rhs.m14*lhs.m43;
		float m14 = rhs.m11*lhs.m14 +rhs.m12*lhs.m24 +rhs.m13*lhs.m34 +rhs.m14*lhs.m44;

		float m21 = rhs.m21*lhs.m11 +rhs.m22*lhs.m21 +rhs.m23*lhs.m31 +rhs.m24*lhs.m41;
		float m22 = rhs.m21*lhs.m12 +rhs.m22*lhs.m22 +rhs.m23*lhs.m32 +rhs.m24*lhs.m42;
		float m23 = rhs.m21*lhs.m13 +rhs.m22*lhs.m23 +rhs.m23*lhs.m33 +rhs.m24*lhs.m43;
		float m24 = rhs.m21*lhs.m14 +rhs.m22*lhs.m24 +rhs.m23*lhs.m34 +rhs.m24*lhs.m44;
		
		float m31 = rhs.m31*lhs.m11 +rhs.m32*lhs.m21 +rhs.m33*lhs.m31 +rhs.m34*lhs.m41;
		float m32 = rhs.m31*lhs.m12 +rhs.m32*lhs.m22 +rhs.m33*lhs.m32 +rhs.m34*lhs.m42;
		float m33 = rhs.m31*lhs.m13 +rhs.m32*lhs.m23 +rhs.m33*lhs.m33 +rhs.m34*lhs.m43;
		float m34 = rhs.m31*lhs.m14 +rhs.m32*lhs.m24 +rhs.m33*lhs.m34 +rhs.m34*lhs.m44;
		
		float m41 = rhs.m41*lhs.m11 +rhs.m42*lhs.m21 +rhs.m43*lhs.m31 +rhs.m44*lhs.m41;
		float m42 = rhs.m41*lhs.m12 +rhs.m42*lhs.m22 +rhs.m43*lhs.m32 +rhs.m44*lhs.m42;
		float m43 = rhs.m41*lhs.m13 +rhs.m42*lhs.m23 +rhs.m43*lhs.m33 +rhs.m44*lhs.m43;
		float m44 = rhs.m41*lhs.m14 +rhs.m42*lhs.m24 +rhs.m43*lhs.m34 +rhs.m44*lhs.m44;
		
		result.m11 = m11;
		result.m12 = m12;
		result.m13 = m13;
		result.m14 = m14;

		result.m21 = m21;
		result.m22 = m22;
		result.m23 = m23;
		result.m24 = m24;
		
		result.m31 = m31;
		result.m32 = m32;
		result.m33 = m33;
		result.m34 = m34;
		
		result.m41 = m41;
		result.m42 = m42;
		result.m43 = m43;
		result.m44 = m44;
	}
	
	public static void multiply(Vector4 result, Matrix4 lhs, Vector4 rhs)
	{
		float x = (lhs.m11*rhs.x) + (lhs.m21*rhs.y) + (lhs.m31*rhs.z) + (lhs.m41*rhs.w);
		float y = (lhs.m12*rhs.x) + (lhs.m22*rhs.y) + (lhs.m32*rhs.z) + (lhs.m42*rhs.w);
		float z = (lhs.m13*rhs.x) + (lhs.m23*rhs.y) + (lhs.m33*rhs.z) + (lhs.m43*rhs.w);
		float w = (lhs.m14*rhs.x) + (lhs.m24*rhs.y) + (lhs.m34*rhs.z) + (lhs.m44*rhs.w);
		
		result.x = x;
		result.y = y;
		result.z = z;
		result.w = w;
	}
	
	public static void transpose(Matrix4 result, Matrix4 m)
	{
		float m11 = m.m11;
		float m12 = m.m21;
		float m13 = m.m31;
		float m14 = m.m41;

		float m21 = m.m12;
		float m22 = m.m22;
		float m23 = m.m32;
		float m24 = m.m42;
		
		float m31 = m.m13;
		float m32 = m.m23;
		float m33 = m.m33;
		float m34 = m.m43;
		
		float m41 = m.m14;
		float m42 = m.m24;
		float m43 = m.m34;
		float m44 = m.m44;
		
		result.m11 = m11;
		result.m12 = m12;
		result.m13 = m13;
		result.m14 = m14;

		result.m21 = m21;
		result.m22 = m22;
		result.m23 = m23;
		result.m24 = m24;
		
		result.m31 = m31;
		result.m32 = m32;
		result.m33 = m33;
		result.m34 = m34;
		
		result.m41 = m41;
		result.m42 = m42;
		result.m43 = m43;
		result.m44 = m44;
	}
	
	public static void invert(Matrix4 result, Matrix4 m)
	{
		float a11a22 = m.m11*m.m22;
		float a11a23 = m.m11*m.m23;
		float a11a24 = m.m11*m.m24;
		
		float a12a21 = m.m12*m.m21;
		float a12a23 = m.m12*m.m23;
		float a12a24 = m.m12*m.m24;
		
		float a13a21 = m.m13*m.m21;
		float a13a22 = m.m13*m.m22;
		float a13a24 = m.m13*m.m24;
		
		float a14a21 = m.m14*m.m21;
		float a14a22 = m.m14*m.m22;
		float a14a23 = m.m14*m.m23;

		float a31a42 = m.m31*m.m42;
		float a31a43 = m.m31*m.m43;
		float a31a44 = m.m31*m.m44;

		float a32a41 = m.m32*m.m41;
		float a32a43 = m.m32*m.m43;
		float a32a44 = m.m32*m.m44;
		
		float a33a41 = m.m33*m.m41;
		float a33a42 = m.m33*m.m42;
		float a33a44 = m.m33*m.m44;

		float a34a41 = m.m34*m.m41;
		float a34a42 = m.m34*m.m42;
		float a34a43 = m.m34*m.m43;

		float a21a32a43 = m.m21*a32a43;
		float a21a32a44 = m.m21*a32a44;
		
		float a21a33a42 = m.m21*a33a42;
		float a21a33a44 = m.m21*a33a44;

		float a21a34a42 = m.m21*a34a42;
		float a21a34a43 = m.m21*a34a43;
		
		float a22a31a43 = m.m22*a31a43;
		float a22a31a44 = m.m22*a31a44;
		
		float a22a33a41 = m.m22*a33a41;
		float a22a33a44 = m.m22*a33a44;
		
		float a22a34a41 = m.m22*a34a41;
		float a22a34a43 = m.m22*a34a43;
		
		float a23a31a42 = m.m23*a31a42;
		float a23a31a44 = m.m23*a31a44;
		
		float a23a32a41 = m.m23*a32a41;
		float a23a32a44 = m.m23*a32a44;
		
		float a23a34a41 = m.m23*a34a41;
		float a23a34a42 = m.m23*a34a42;
		
		float a24a31a42 = m.m24*a31a42;
		float a24a31a43 = m.m24*a31a43;
		
		float a24a32a43 = m.m24*a32a43;
		float a24a32a41 = m.m24*a32a41;
		
		float a24a33a41 = m.m24*a33a41;
		float a24a33a42 = m.m24*a33a42;
		
		// Calculate DETERMINANT
		float det =   (m.m11 * a22a33a44) + (m.m11 * a23a34a42) + (m.m11 * a24a32a43)
					+ (m.m12 * a21a34a43) + (m.m12 * a23a31a44) + (m.m12 * a24a33a41)
					+ (m.m13 * a21a32a44) + (m.m13 * a22a34a41) + (m.m13 * a24a31a42)
					+ (m.m14 * a21a33a42) + (m.m14 * a22a31a43) + (m.m14 * a23a32a41)
					- (m.m11 * a22a34a43) - (m.m11 * a23a32a44) - (m.m11 * a24a33a42)
					- (m.m12 * a21a33a44) - (m.m12 * a23a34a41) - (m.m12 * a24a31a43)
					- (m.m13 * a21a34a42) - (m.m13 * a22a31a44) - (m.m13 * a24a32a41)
					- (m.m14 * a21a32a43) - (m.m14 * a22a33a41) - (m.m14 * a23a31a42);
		
		// Calculate ADJOINT
		float b11 = (a22a33a44) + (a23a34a42) + (a24a32a43) - (a22a34a43) - (a23a32a44) - (a24a33a42);
		float b12 = (m.m12 * a34a43) + (m.m13 * a32a44) + (m.m14 * a33a42) - (m.m12 * a33a44) - (m.m13 * a34a42) - (m.m14 * a32a43);
		float b13 = (a12a23 * m.m44) + (a13a24 * m.m42) + (a14a22 * m.m43) - (a12a24 * m.m43) - (a13a22 * m.m44) - (a14a23 * m.m42);
		float b14 = (a12a24 * m.m33) + (a13a22 * m.m34) + (a14a23 * m.m32) - (a12a23 * m.m34) - (a13a24 * m.m32) - (a14a22 * m.m33);
		
		float b21 = (a21a34a43) + (a23a31a44) + (a24a33a41) - (a21a33a44) - (a23a34a41) - (a24a31a43);
		float b22 = (m.m11 * a33a44) + (m.m13 * a34a41) + (m.m14 * a31a43) - (m.m11 * a34a43) - (m.m13 * a31a44) - (m.m14 * a33a41);
		float b23 = (a11a24 * m.m43) + (a13a21 * m.m44) + (a14a23 * m.m41) - (a11a23 * m.m44) - (a13a24 * m.m41) - (a14a21 * m.m43);
		float b24 = (a11a23 * m.m34) + (a13a24 * m.m31) + (a14a21 * m.m33) - (a11a24 * m.m33) - (a13a21 * m.m34) - (a14a23 * m.m31);
		
		float b31 = (a21a32a44) + (a22a34a41) + (a24a31a42) - (a21a34a42) - (a22a31a44) - (a24a32a41);
		float b32 = (m.m11 * a34a42) + (m.m12 * a31a44) + (m.m14 * a32a41) - (m.m11 * a32a44) - (m.m12 * a34a41) - (m.m14 * a31a42);
		float b33 = (a11a22 * m.m44) + (a12a24 * m.m41) + (a14a21 * m.m42) - (a11a24 * m.m42) - (a12a21 * m.m44) - (a14a22 * m.m41);
		float b34 = (a11a24 * m.m32) + (a12a21 * m.m34) + (a14a22 * m.m31) - (a11a22 * m.m34) - (a12a24 * m.m31) - (a14a21 * m.m32);
		
		float b41 = (a21a33a42) + (a22a31a44) + (a23a32a41) - (a21a32a43) - (a22a33a41) - (a23a31a42);
		float b42 = (m.m11 * a32a43) + (m.m12 * a33a41) + (m.m13 * a31a42) - (m.m11 * a33a42) - (m.m12 * a31a43) - (m.m13 * a32a41);
		float b43 = (a11a23 * m.m42) + (a12a21 * m.m43) + (a13a22 * m.m41) - (a11a22 * m.m43) - (a12a23 * m.m41) - (a13a21 * m.m42);
		float b44 = (a11a22 * m.m33) + (a12a23 * m.m31) + (a13a21 * m.m32) - (a11a23 * m.m32) - (a12a21 * m.m33) - (a13a22 * m.m31);

		// Calculate Invert Matrix 4x4
		result.m11 = b11 / det;
		result.m12 = b12 / det;
		result.m13 = b13 / det;
		result.m14 = b14 / det;
		result.m21 = b21 / det;
		result.m22 = b22 / det;
		result.m23 = b23 / det;
		result.m24 = b24 / det;
		result.m31 = b31 / det;
		result.m32 = b32 / det;
		result.m33 = b33 / det;
		result.m34 = b34 / det;
		result.m41 = b41 / det;
		result.m42 = b42 / det;
		result.m43 = b43 / det;
		result.m44 = b44 / det;
	}
	
	public static void createFromQuaternion(Matrix4 result, Quaternion q)
	{
		float _2xx = 2*q.x*q.x;
		float _2yy = 2*q.y*q.y;
		float _2zz = 2*q.z*q.z;
		
		float _2xy = 2*q.x*q.y;
		float _2xz = 2*q.x*q.z;
		float _2yz = 2*q.y*q.z;
		
		float _2xw = 2*q.x*q.w;
		float _2yw = 2*q.y*q.w;
		float _2zw = 2*q.z*q.w;
		
		float _1sub2xx = 1.0f - _2xx;

		// It's a Right-Handed Coordinate
		// Left-Hand Coordinate is a RHMatrix3X3 Transposed
		
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
	}
	
	public static void createTranslation(Matrix4 result, float x, float y, float z)
	{
		result.m11 = 1;
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = 1;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = 0;
		result.m33 = 1;
		result.m34 = 0;
		
		result.m41 = x;
		result.m42 = y;
		result.m43 = z;
		result.m44 = 1;
	}
	
	public static void createRotationX(Matrix4 result, float degree)
	{
		float cos = Mathf.cos(degree);
		float sin = Mathf.sin(degree);
		
		result.m11 = 1;
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = cos;
		result.m23 = sin;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = -sin;
		result.m33 = cos;
		result.m34 = 0;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		result.m44 = 1;
	}
	
	public static void createRotationY(Matrix4 result, float degree)
	{
		float cos = Mathf.cos(degree);
		float sin = Mathf.sin(degree);
		
		result.m11 = cos;
		result.m12 = 0;
		result.m13 = -sin;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = 1;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = sin;
		result.m32 = 0;
		result.m33 = cos;
		result.m34 = 0;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		result.m44 = 1;
	}
	
	public static void createRotationZ(Matrix4 result, float degree)
	{
		float cos = Mathf.cos(degree);
		float sin = Mathf.sin(degree);
		
		result.m11 = cos;
		result.m12 = sin;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = -sin;
		result.m22 = cos;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = 0;
		result.m33 = 1;
		result.m34 = 0;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		result.m44 = 1;
	}
	
	public static void createScale(Matrix4 result, float sx, float sy, float sz)
	{
		result.m11 = sx;
		result.m22 = sy;
		result.m33 = sz;
		result.m44 = 1;
		
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = 0;
		result.m34 = 0;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		
	}
	
	public static void createTRS(Matrix4 result, float tx, float ty, float tz, float ax, float ay, float az, float sx, float sy, float sz)
	{
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = Mathf.cos(ay);
		float sh = Mathf.sin(ay);
			    
		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = Mathf.cos(az);
		float sa = Mathf.sin(az);
		    
		// Bank ( Roll :: X Axis ) ; Last
		float cb = Mathf.cos(ax);
		float sb = Mathf.sin(ax);

		result.m11 = ( ch*ca ) * sx;
		result.m12 = sa * sy;
		result.m13 = ( -sh*ca ) * sz;
		result.m14 = 0;

		result.m21 = ( sh*sb - ch*sa*cb ) * sx;
		result.m22 = ( ca*cb ) * sy;
		result.m23 = ( sh*sa*cb + ch*sb ) * sz;
		result.m24 = 0;

		result.m31 = ( ch*sa*sb + sh*cb ) * sx;
		result.m32 = ( -ca*sb ) * sy;
		result.m33 = ( -sh*sa*sb + ch*cb ) * sz;
		result.m34 = 0;
		
		result.m41 = tx*sx;
		result.m42 = ty*sy;
		result.m43 = tz*sz;
		result.m44 = 1;
	}
	
	public static void createFromEulerAngles(Matrix4 result, float ax, float ay, float az)
	{
		
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = Mathf.cos(ay);
	    float sh = Mathf.sin(ay);
	    
	    // Attitude ( Pitch :: Z Axis ) ; Second
	    float ca = Mathf.cos(az);
	    float sa = Mathf.sin(az);
	    
	    // Bank ( Roll :: X Axis ) ; Last
	    float cb = Mathf.cos(ax);
	    float sb = Mathf.sin(ax);

	    // It's a Right-Handed Coordinate
	 	// Left-Hand Coordinate is a RHMatrix3X3 Transposed
	    
	    result.m11 = ch*ca;
	    result.m12 = sa;
	    result.m13 = -sh*ca;
	    result.m14 = 0;
	    
	    result.m21 = sh*sb - ch*sa*cb;
	    result.m22 = ca*cb;
	    result.m23 = sh*sa*cb + ch*sb;
	    result.m24 = 0;

	    result.m31 = ch*sa*sb + sh*cb;
	    result.m32 = -ca*sb;
	    result.m33 = -sh*sa*sb + ch*cb;
	    result.m34 = 0;

	    result.m41 = 0;
	    result.m42 = 0;
	    result.m43 = 0;
	    
	    result.m44 = 1;
	}
	
	public static void createLookAt(Matrix4 result, float eyeX, float eyeY, float eyeZ, float targetX, float targetY, float targetZ, float upX, float upY, float upZ)
	{
		// Forward-Direction
		float fx = eyeX - targetX;
		float fy = eyeY - targetY;
		float fz = eyeZ - targetZ;
		
		float magnitude = Mathf.sqrt((fx*fx)+(fy*fy)+(fz*fz));
		fx /= magnitude;
		fy /= magnitude;
		fz /= magnitude;
		
		// Left-Direction
		float lx = (upY * fz) - (upZ * fy);
		float ly = (upZ * fx) - (upX * fz);
		float lz = (upX * fy) - (upY * fx);
		
		magnitude = Mathf.sqrt((lx*lx)+(ly*ly)+(lz*lz));
		lx /= magnitude;
		ly /= magnitude;
		lz /= magnitude;
		
		// Up-Direction
		float ux = (fy * lz) - (fz * ly);
		float uy = (fz * lx) - (fx * lz);
		float uz = (fx * ly) - (fy * lx);
		
		// Translation
		float tx = (lx*eyeX) + (ly*eyeY) + (lz*eyeZ);
		float ty = (ux*eyeX) + (uy*eyeY) + (uz*eyeZ);
		float tz = (fx*eyeX) + (fy*eyeY) + (fz*eyeZ);
		
		result.m11 = lx;
		result.m12 = ux;
		result.m13 = fx;
		result.m14 = 0;
		
		result.m21 = ly;
		result.m22 = uy;
		result.m23 = fy;
		result.m24 = 0;

		result.m31 = lz;
		result.m32 = uz;
		result.m33 = fz;
		result.m34 = 0;
		
		result.m41 = -tx;
		result.m42 = -ty;
		result.m43 = -tz;
		result.m44 = 1;
	}

	public static void createLookAt(Matrix4 result, Vector3 eye, Vector3 target, Vector3 up)
	{
		createLookAt(result, eye.x, eye.y, eye.z, target.x, target.y, target.z, up.x, up.y, up.z);
	}
	
	public static void createOrtho(Matrix4 result, float left, float right, float bottom, float top, float near, float far)
	{
		float w = right-left;
		float h = top-bottom;
		float Zf_Zn = far-near;
		
		result.m11 = 2/w;
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = 2/h;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = 0;
		result.m33 = (-2) / Zf_Zn;
		result.m34 = 0;
		
		result.m41 = -(right + left) / w;
		result.m42 = -(top + bottom) / h;
		result.m43 = -(far + near) / Zf_Zn;
		result.m44 = 1;
	}
	
	public static void createFrustum(Matrix4 result, float left, float right, float bottom, float top, float near, float far)
	{
		float Zn = near;
		float Zf = far;
		float Vw = right-left;
		float Vh = top-bottom;
		
		float _2Zn = (2.0f*Zn);
		float w = _2Zn/Vw;
		float h = _2Zn/Vh;
		
		float Q = Zf/(Zf-Zn);
		
		result.m11 = w;
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = h;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = 0;
		result.m33 = Q;
		result.m34 = -1;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = Q*Zn;
		result.m44 = 0;
	}
	
	public static void identity(Matrix4 result)
	{
		result.m11 = 1;
		result.m22 = 1;
		result.m33 = 1;
		result.m44 = 1;
		
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = 0;
		result.m32 = 0;
		result.m34 = 0;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		
	}

	public static void createNormalMatrix(Matrix4 result, Matrix4 modelView)
	{
		float m11 = modelView.m11;
		float m12 = modelView.m12;
		float m13 = modelView.m13;
		
		float m21 = modelView.m21;
		float m22 = modelView.m22;
		float m23 = modelView.m23;
		
		float m31 = modelView.m31;
		float m32 = modelView.m32;
		float m33 = modelView.m33;
		
		float a11a22 = m11*m22;
		float a11a23 = m11*m23;
		
		float a12a21 = m12*m21;
		float a12a23 = m12*m23;
		
		float a13a21 = m13*m21;
		float a13a22 = m13*m22;

		float a21a32a44 = m21*m32;
		float a21a33a44 = m21*m33;
		float a22a31a44 = m22*m31;
		float a22a33a44 = m22*m33;
		float a23a31a44 = m23*m31;
		float a23a32a44 = m23*m32;
		
		// Calculate DETERMINANT
		float det =   (m11 * a22a33a44) + (m12 * a23a31a44) + (m13 * a21a32a44)
					- (m11 * a23a32a44) - (m12 * a21a33a44) - (m13 * a22a31a44);
		
		// Calculate ADJOINT
		float b11 = (a22a33a44) - (a23a32a44);
		float b12 = (m13 * m32) - (m12 * m33);
		float b13 = a12a23 - a13a22;
		
		float b21 = (a23a31a44) - (a21a33a44);
		float b22 = (m11 * m33) - (m13 * m31);
		float b23 = a13a21 - a11a23;
		
		float b31 = (a21a32a44) - (a22a31a44);
		float b32 = (m12 * m31) - (m11 * m32);
		float b33 = a11a22 - a12a21;

		// Calculate Invert Matrix 3x3
		m11 = b11 / det;
		m12 = b12 / det;
		m13 = b13 / det;
		
		m21 = b21 / det;
		m22 = b22 / det;
		m23 = b23 / det;

		m31 = b31 / det;
		m32 = b32 / det;
		m33 = b33 / det;

		// Calculate Transpose
		result.m11 = m11;
		result.m12 = m21;
		result.m13 = m31;
		result.m14 = 0;
		
		result.m21 = m12;
		result.m22 = m22;
		result.m23 = m32;
		result.m24 = 0;
		
		result.m31 = m13;
		result.m32 = m23;
		result.m33 = m33;
		result.m34 = 0;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = 0;
		result.m44 = 1;
	}
	
	public static void lerp(Matrix4 result, Matrix4 from, Matrix4 to, float t)
	{
		float fx = 0, fy = 0, fz = 0, fw = 0;
		float tx = 0, ty = 0, tz = 0, tw = 0;

		// Matrix to Quaternion
		// From Quaternion
		if(from.m11 + from.m22 + from.m33 > 0)
		{
			float S = Mathf.sqrt(from.m11 + from.m22 + from.m33 + 1.0) * 2.0f;
			fw = 0.25f * S;
			fx = (from.m23 - from.m32) / S;
			fy = (from.m31 - from.m13) / S;
			fz = (from.m12 - from.m21) / S;
		}
		else if(from.m11 > from.m22 && from.m11 > from.m33)
		{
			float S = Mathf.sqrt(1.0 + from.m11 - from.m22 - from.m33) * 2.0f;
			fw = (from.m23 - from.m32) / S;
			fx = 0.25f * S;
			fy = (from.m21 + from.m12) / S;
			fz = (from.m31 + from.m13) / S;
		}
		else if(from.m22 > from.m33)
		{
			float S = Mathf.sqrt(1.0 + from.m22 - from.m11 - from.m33) * 2.0f;
			fw = (from.m31 - from.m13) / S;
			fx = (from.m21 + from.m12) / S;
			fy = 0.25f * S;
			fz = (from.m32 + from.m23)/S;
		}
		else
		{
			float S = Mathf.sqrt(1.0 + from.m33 - from.m11 - from.m22) * 2.0f;
			fw = (from.m12 - from.m21) / S;
			fx = (from.m31 + from.m13) / S;
			fy = (from.m32 + from.m23) / S;
			fz = 0.25f * S;
		}
			
		// To Quaternion
		if(to.m11 + to.m22 + to.m33 > 0)
		{
			float S = Mathf.sqrt(to.m11 + to.m22 + to.m33 + 1.0) * 2.0f;
			tw = 0.25f * S;
			tx = (to.m23 - to.m32) / S;
			ty = (to.m31 - to.m13) / S;
			tz = (to.m12 - to.m21) / S;
		}
		else if(to.m11 > to.m22 && to.m11 > to.m33)
		{
			float S = Mathf.sqrt(1.0 + to.m11 - to.m22 - to.m33) * 2.0f;
			tw = (to.m23 - to.m32) / S;
			tx = 0.25f * S;
			ty = (to.m21 + to.m12) / S;
			tz = (to.m31 + to.m13) / S;
		}
		else if(to.m22 > to.m33)
		{
			float S = Mathf.sqrt(1.0 + to.m22 - to.m11 - to.m33) * 2.0f;
			tw = (to.m31 - to.m13) / S;
			tx = (to.m21 + to.m12) / S;
			ty = 0.25f * S;
			tz = (to.m32 + to.m23)/S;
		}
		else
		{
			float S = Mathf.sqrt(1.0 + to.m33 - to.m11 - to.m22) * 2.0f;
			tw = (to.m12 - to.m21) / S;
			tx = (to.m31 + to.m13) / S;
			ty = (to.m32 + to.m23) / S;
			tz = 0.25f * S;
		}
		
		// Slerp With Quaternion
		float rx = 0, ry = 0, rz = 0, rw = 0;
		
		// Calculate angle between them.
		float cosHalfTheta = fw * tw + fx * tx + fy * ty + fz * tz;

		// Calculate temporary values.
		float halfTheta = (float) Math.acos(cosHalfTheta);
		float sinHalfTheta = Mathf.sqrt(1.0 - cosHalfTheta * cosHalfTheta);

		if (Math.abs(cosHalfTheta) >= 1.0)
		{
			// if qa=qb or qa=-qb then theta = 0 and we can return qa
			rw = fw;
			rx = fx;
			ry = fy;
			rz = fz;
		} else if (Math.abs(sinHalfTheta) < 0.001)
		{
			// if theta = 180 degrees then result is not fully defined
			// we could rotate around any axis normal to qa or qb
			rw = (fw * 0.5f + tw * 0.5f);
			rx = (fx * 0.5f + tx * 0.5f);
			ry = (fy * 0.5f + ty * 0.5f);
			rz = (fz * 0.5f + tz * 0.5f);
		} else
		{
			float ratioA = (float) (Math.sin((1 - t) * halfTheta) / sinHalfTheta);
			float ratioB = (float) (Math.sin(t * halfTheta) / sinHalfTheta);
			// calculate Quaternion.
			rw = (fw * ratioA + tw * ratioB);
			rx = (fx * ratioA + tx * ratioB);
			ry = (fy * ratioA + ty * ratioB);
			rz = (fz * ratioA + tz * ratioB);
		}
		
		// Convert Quaternion to Matrix
		float _2xx = 2*rx*rx;
		float _2yy = 2*ry*ry;
		float _2zz = 2*rz*rz;
		
		float _2xy = 2*rx*ry;
		float _2xz = 2*rx*rz;
		float _2yz = 2*ry*rz;
		
		float _2xw = 2*rx*rw;
		float _2yw = 2*ry*rw;
		float _2zw = 2*rz*rw;
		
		float _1sub2xx = 1.0f - _2xx;

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
	}
	
	public static Matrix4 add(Matrix4 lhs, Matrix4 rhs)
	{
		Matrix4 m = new Matrix4();
		add(m, lhs, rhs);
		return m;
	}
	
	public static Matrix4 subtract(Matrix4 lhs, Matrix4 rhs)
	{
		Matrix4 m = new Matrix4();
		subtract(m, lhs, rhs);
		return m;
	}
	
	public static Matrix4 multiply(Matrix4 lhs, Matrix4 rhs)
	{
		Matrix4 m = new Matrix4();
		multiply(m, lhs, rhs);
		return m;
	}
	
	public static Vector4 multiply(Matrix4 lhs, Vector4 rhs)
	{
		Vector4 v = new Vector4();
		multiply(v, lhs, rhs);
		return v;
	}
	
	public static Matrix4 transpose(Matrix4 m)
	{
		Matrix4 mm = new Matrix4();
		transpose(mm, m);
		return mm;
	}
	
	public static Matrix4 invert(Matrix4 m)
	{
		Matrix4 mm = new Matrix4();
		invert(mm, m);
		return mm;
	}
	
	public static Matrix4 createFromQuaternion(Quaternion q)
	{
		Matrix4 mm = new Matrix4();
		createFromQuaternion(mm, q);
		return mm;
	}
	
	public static Matrix4 createTranslation(float x, float y, float z)
	{
		Matrix4 m = new Matrix4();
		createTranslation(m, x, y, z);
		return m;
	}
	
	
	public static Matrix4 createRotationX(float degree)
	{
		Matrix4 a = new Matrix4();
		createRotationX(a, degree);
		return a;
	}
	
	public static Matrix4 createRotationY(float degree)
	{
		Matrix4 a = new Matrix4();
		createRotationY(a, degree);
		return a;
	}
	
	public static Matrix4 createRotationZ(float degree)
	{
		Matrix4 a = new Matrix4();
		createRotationZ(a, degree);
		return a;
	}
	
	public static Matrix4 identity()
	{
		Matrix4 a = new Matrix4();
		identity(a);
		return a;
	}

	public static Matrix4 createFromEulerAngles(float ax, float ay, float az)
	{
		Matrix4 m = new Matrix4();
		createFromEulerAngles(m, ax, ay, az);
		return m;
	}
	
	public static Matrix4 createLookAt(float eyeX, float eyeY, float eyeZ, float targetX, float targetY, float targetZ, float upX, float upY, float upZ)
	{
		Matrix4 m = new Matrix4();
		createLookAt(m, eyeX, eyeY, eyeZ, targetX, targetY, targetZ, upX, upY, upZ);
		return m;
	}
	
	public static Matrix4 createLookAt(Vector3 eye, Vector3 target, Vector3 up)
	{
		Matrix4 m = new Matrix4();
		createLookAt(m, eye, target, up);
		return m;
	}
	
	public static Matrix4 createOrtho(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4 m = new Matrix4();
		createOrtho(m, left, right, bottom, top, near, far);
		return m;
	}
	
	public static Matrix4 createFrustum(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4 m = new Matrix4();
		createFrustum(m, left, right, bottom, top, near, far);
		return m;
	}
	
	public static Matrix4 createNormalMatrix(Matrix4 modelView)
	{
		Matrix4 m = new Matrix4();
		createNormalMatrix(m, modelView);
		return m;
	}
	
	// Additional
	public static void createArrayMatrix4(float[] result, Matrix4 src)
	{
		result[0] = src.m11;
		result[1] = src.m12;
		result[2] = src.m13;
		result[3] = src.m14;
		
		result[4] = src.m21;
		result[5] = src.m22;
		result[6] = src.m23;
		result[7] = src.m24;
		
		result[8] = src.m31;
		result[9] = src.m32;
		result[10] = src.m33;
		result[11] = src.m34;
		
		result[12] = src.m41;
		result[13] = src.m42;
		result[14] = src.m43;
		result[15] = src.m44;
	}
	
	public static void createArrayMatrixFromQuaternion(float[] result, Quaternion src)
	{
		float _2xx = 2*src.x*src.x;
		float _2yy = 2*src.y*src.y;
		float _2zz = 2*src.z*src.z;
		
		float _2xy = 2*src.x*src.y;
		float _2xz = 2*src.x*src.z;
		float _2yz = 2*src.y*src.z;
		
		float _2xw = 2*src.x*src.w;
		float _2yw = 2*src.y*src.w;
		float _2zw = 2*src.z*src.w;
		
		float _1sub2xx = 1.0f - _2xx;
		
		result[0] = 1.0f - _2yy - _2zz;
		result[1] = _2xy + _2zw;
		result[2] = _2xz - _2yw;
		result[3] = 0;
		
		result[4] = _2xy - _2zw;
		result[5] = _1sub2xx - _2zz;
		result[6] = _2yz + _2xw;
		result[7] = 0;
		
		result[8] = _2xz + _2yw;
		result[9] = _2yz - _2xw;
		result[10] = _1sub2xx - _2yy;
		result[11] = 0;

		result[12] = 0;
		result[13] = 0;
		result[14] = 0;
		result[15] = 1;
	}

	public static float[] createArrayMatrix4(Matrix4 src)
	{
		float[] m = new float[16];
		createArrayMatrix4(m, src);
		return m;
	}
	
	public static float[] createArrayMatrixFromQuaternion(Quaternion src)
	{
		float[] m = new float[16];
		createArrayMatrixFromQuaternion(m, src);
		return m;
	}
}
