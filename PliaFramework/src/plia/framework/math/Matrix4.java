package plia.framework.math;

public final class Matrix4
{
	public float  m11 = 1, m12, m13, m14, 
				  m21, m22 = 1, m23, m24,
				  m31, m32, m33 = 1, m34, 
				  m41, m42, m43, m44 = 1;
	
	public Matrix4()
	{
		
	}
	
	public Matrix4(Matrix4 m)
	{
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;
		m14 = m.m14;
		
		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;
		m24 = m.m24;
		
		m31 = m.m31;
		m32 = m.m32;
		m33 = m.m33;
		m34 = m.m34;
		
		m41 = m.m41;
		m42 = m.m42;
		m43 = m.m43;
		m44 = m.m44;
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
	
	@Override
	public Matrix4 clone() 
	{
		return new Matrix4(this);
	}
	
	public void set(Matrix3 m)
	{
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;

		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;

		m31 = m.m31;
		m32 = m.m32;
		m33 = m.m33;
	}
	
	public void set(Matrix4 m)
	{
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;
		m14 = m.m14;

		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;
		m24 = m.m24;

		m31 = m.m31;
		m32 = m.m32;
		m33 = m.m33;
		m34 = m.m34;
		
		m41 = m.m41;
		m42 = m.m42;
		m43 = m.m43;
		m44 = m.m44;
	}
	
	public void set(float[] m)
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
	
	public void copyTo(float[] m)
	{
		m[0] = m11;
		m[1] = m12;
		m[2] = m13;
		m[3] = m14;
		
		m[4] = m21;
		m[5] = m22;
		m[6] = m23;
		m[7] = m24;
		
		m[8] = m31;
		m[9] = m32;
		m[10] = m33;
		m[11] = m34;
		
		m[12] = m41;
		m[13] = m42;
		m[14] = m43;
		m[15] = m44;
	}
	
	public Matrix3 toMatrix3()
	{
		return new Matrix3(m11, m12, m13, m21, m22, m23, m31, m32, m33);
	}
	
	// Methods
	public void setIdentity()
	{
		this.m11 = 1;
		this.m22 = 1;
		this.m33 = 1;
		this.m44 = 1;
		
		this.m12 = 0;
		this.m13 = 0;
		this.m14 = 0;
		
		this.m21 = 0;
		this.m23 = 0;
		this.m24 = 0;
		
		this.m31 = 0;
		this.m32 = 0;
		this.m34 = 0;
		
		this.m41 = 0;
		this.m42 = 0;
		this.m43 = 0;
	}
	
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
	
	public Matrix4 getInvert()
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

		float a21a32a43 = m21*a32a43;
		float a21a32a44 = m21*a32a44;
		
		float a21a33a42 = m21*a33a42;
		float a21a33a44 = m21*a33a44;

		float a21a34a42 = m21*a34a42;
		float a21a34a43 = m21*a34a43;
		
		float a22a31a43 = m22*a31a43;
		float a22a31a44 = m22*a31a44;
		
		float a22a33a41 = m22*a33a41;
		float a22a33a44 = m22*a33a44;
		
		float a22a34a41 = m22*a34a41;
		float a22a34a43 = m22*a34a43;
		
		float a23a31a42 = m23*a31a42;
		float a23a31a44 = m23*a31a44;
		
		float a23a32a41 = m23*a32a41;
		float a23a32a44 = m23*a32a44;
		
		float a23a34a41 = m23*a34a41;
		float a23a34a42 = m23*a34a42;
		
		float a24a31a42 = m24*a31a42;
		float a24a31a43 = m24*a31a43;
		
		float a24a32a43 = m24*a32a43;
		float a24a32a41 = m24*a32a41;
		
		float a24a33a41 = m24*a33a41;
		float a24a33a42 = m24*a33a42;
		
		// Calculate DETERMINANT
		float det =   (m11 * a22a33a44) + (m11 * a23a34a42) + (m11 * a24a32a43)
					+ (m12 * a21a34a43) + (m12 * a23a31a44) + (m12 * a24a33a41)
					+ (m13 * a21a32a44) + (m13 * a22a34a41) + (m13 * a24a31a42)
					+ (m14 * a21a33a42) + (m14 * a22a31a43) + (m14 * a23a32a41)
					- (m11 * a22a34a43) - (m11 * a23a32a44) - (m11 * a24a33a42)
					- (m12 * a21a33a44) - (m12 * a23a34a41) - (m12 * a24a31a43)
					- (m13 * a21a34a42) - (m13 * a22a31a44) - (m13 * a24a32a41)
					- (m14 * a21a32a43) - (m14 * a22a33a41) - (m14 * a23a31a42);
		
		// Calculate ADJOINT
		float b11 = (a22a33a44) + (a23a34a42) + (a24a32a43) - (a22a34a43) - (a23a32a44) - (a24a33a42);
		float b12 = (m12 * a34a43) + (m13 * a32a44) + (m14 * a33a42) - (m12 * a33a44) - (m13 * a34a42) - (m14 * a32a43);
		float b13 = (a12a23 * m44) + (a13a24 * m42) + (a14a22 * m43) - (a12a24 * m43) - (a13a22 * m44) - (a14a23 * m42);
		float b14 = (a12a24 * m33) + (a13a22 * m34) + (a14a23 * m32) - (a12a23 * m34) - (a13a24 * m32) - (a14a22 * m33);
		
		float b21 = (a21a34a43) + (a23a31a44) + (a24a33a41) - (a21a33a44) - (a23a34a41) - (a24a31a43);
		float b22 = (m11 * a33a44) + (m13 * a34a41) + (m14 * a31a43) - (m11 * a34a43) - (m13 * a31a44) - (m14 * a33a41);
		float b23 = (a11a24 * m43) + (a13a21 * m44) + (a14a23 * m41) - (a11a23 * m44) - (a13a24 * m41) - (a14a21 * m43);
		float b24 = (a11a23 * m34) + (a13a24 * m31) + (a14a21 * m33) - (a11a24 * m33) - (a13a21 * m34) - (a14a23 * m31);
		
		float b31 = (a21a32a44) + (a22a34a41) + (a24a31a42) - (a21a34a42) - (a22a31a44) - (a24a32a41);
		float b32 = (m11 * a34a42) + (m12 * a31a44) + (m14 * a32a41) - (m11 * a32a44) - (m12 * a34a41) - (m14 * a31a42);
		float b33 = (a11a22 * m44) + (a12a24 * m41) + (a14a21 * m42) - (a11a24 * m42) - (a12a21 * m44) - (a14a22 * m41);
		float b34 = (a11a24 * m32) + (a12a21 * m34) + (a14a22 * m31) - (a11a22 * m34) - (a12a24 * m31) - (a14a21 * m32);
		
		float b41 = (a21a33a42) + (a22a31a44) + (a23a32a41) - (a21a32a43) - (a22a33a41) - (a23a31a42);
		float b42 = (m11 * a32a43) + (m12 * a33a41) + (m13 * a31a42) - (m11 * a33a42) - (m12 * a31a43) - (m13 * a32a41);
		float b43 = (a11a23 * m42) + (a12a21 * m43) + (a13a22 * m41) - (a11a22 * m43) - (a12a23 * m41) - (a13a21 * m42);
		float b44 = (a11a22 * m33) + (a12a23 * m31) + (a13a21 * m32) - (a11a23 * m32) - (a12a21 * m33) - (a13a22 * m31);

		Matrix4 result = new Matrix4();
		
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
		
		return result;
	}
	
	public Matrix4 getTranspose()
	{
		Matrix4 result = new Matrix4();
		
		result.m11 = m11;
		result.m12 = m21;
		result.m13 = m31;
		result.m14 = m41;

		result.m21 = m12;
		result.m22 = m22;
		result.m23 = m32;
		result.m24 = m42;
		
		result.m31 = m13;
		result.m32 = m23;
		result.m33 = m33;
		result.m34 = m43;
		
		result.m41 = m14;
		result.m42 = m24;
		result.m43 = m34;
		result.m44 = m44;
		
		return result;
	}
	
	public Matrix4 add(Matrix4 rhs)
	{
		this.m11 += rhs.m11;
		this.m12 += rhs.m12;
		this.m13 += rhs.m13;
		this.m14 += rhs.m14;

		this.m21 += rhs.m21;
		this.m22 += rhs.m22;
		this.m23 += rhs.m23;
		this.m24 += rhs.m24;
		
		this.m31 += rhs.m31;
		this.m32 += rhs.m32;
		this.m33 += rhs.m33;
		this.m34 += rhs.m34;
		
		this.m41 += rhs.m41;
		this.m42 += rhs.m42;
		this.m43 += rhs.m43;
		this.m44 += rhs.m44;
		
		return this;
	}
	
	public Matrix4 subtract(Matrix4 rhs)
	{
		this.m11 -= rhs.m11;
		this.m12 -= rhs.m12;
		this.m13 -= rhs.m13;
		this.m14 -= rhs.m14;

		this.m21 -= rhs.m21;
		this.m22 -= rhs.m22;
		this.m23 -= rhs.m23;
		this.m24 -= rhs.m24;
		
		this.m31 -= rhs.m31;
		this.m32 -= rhs.m32;
		this.m33 -= rhs.m33;
		this.m34 -= rhs.m34;
		
		this.m41 -= rhs.m41;
		this.m42 -= rhs.m42;
		this.m43 -= rhs.m43;
		this.m44 -= rhs.m44;
		
		return this;
	}
	
	public Matrix4 multiply(Matrix4 rhs)
	{
		float mm11 = m11*rhs.m11 +m21*rhs.m12 +m31*rhs.m13 +m41*rhs.m14;
		float mm12 = m12*rhs.m11 +m22*rhs.m12 +m32*rhs.m13 +m42*rhs.m14;
		float mm13 = m13*rhs.m11 +m23*rhs.m12 +m33*rhs.m13 +m43*rhs.m14;
		float mm14 = m14*rhs.m11 +m24*rhs.m12 +m34*rhs.m13 +m44*rhs.m14;

		float mm21 = m11*rhs.m21 +m21*rhs.m22 +m31*rhs.m23 +m41*rhs.m24;
		float mm22 = m12*rhs.m21 +m22*rhs.m22 +m32*rhs.m23 +m42*rhs.m24;
		float mm23 = m13*rhs.m21 +m23*rhs.m22 +m33*rhs.m23 +m43*rhs.m24;
		float mm24 = m14*rhs.m21 +m24*rhs.m22 +m34*rhs.m23 +m44*rhs.m24;
		
		float mm31 = m11*rhs.m31 +m21*rhs.m32 +m31*rhs.m33 +m41*rhs.m34;
		float mm32 = m12*rhs.m31 +m22*rhs.m32 +m32*rhs.m33 +m42*rhs.m34;
		float mm33 = m13*rhs.m31 +m23*rhs.m32 +m33*rhs.m33 +m43*rhs.m34;
		float mm34 = m14*rhs.m31 +m24*rhs.m32 +m34*rhs.m33 +m44*rhs.m34;
		
		float mm41 = m11*rhs.m41 +m21*rhs.m42 +m31*rhs.m43 +m41*rhs.m44;
		float mm42 = m12*rhs.m41 +m22*rhs.m42 +m32*rhs.m43 +m42*rhs.m44;
		float mm43 = m13*rhs.m41 +m23*rhs.m42 +m33*rhs.m43 +m43*rhs.m44;
		float mm44 = m14*rhs.m41 +m24*rhs.m42 +m34*rhs.m43 +m44*rhs.m44;
		
		m11 = mm11;
		m12 = mm12;
		m13 = mm13;
		m14 = mm14;
		
		m21 = mm21;
		m22 = mm22;
		m23 = mm23;
		m24 = mm24;
		
		m31 = mm31;
		m32 = mm32;
		m33 = mm33;
		m34 = mm34;
		
		m41 = mm41;
		m42 = mm42;
		m43 = mm43;
		m44 = mm44;
		
		return this;
	}
	
	public Vector4 multiply(Vector4 rhs)
	{
		Vector4 result = new Vector4();

		result.x = (m11 * rhs.x) + (m21 * rhs.y) + (m31 * rhs.z) + (m41 * rhs.w);
		result.y = (m12 * rhs.x) + (m22 * rhs.y) + (m32 * rhs.z) + (m42 * rhs.w);
		result.z = (m13 * rhs.x) + (m23 * rhs.y) + (m33 * rhs.z) + (m43 * rhs.w);
		result.w = (m14 * rhs.x) + (m24 * rhs.y) + (m34 * rhs.z) + (m44 * rhs.w);

		return result;
	}
	
	public Vector3 getTranslation()
	{
		return new Vector3(m41, m42, m43);
	}
	
	public void setTranslation(float x, float y, float z)
	{
		this.m41 = x;
		this.m42 = y;
		this.m43 = z;
	}
	
	public void setTranslation(Vector3 translation)
	{
		this.m41 = translation.x;
		this.m42 = translation.y;
		this.m43 = translation.z;
	}

	public Vector3 getEulerAngles()
	{
		Vector3 result = new Vector3();
		float heading, attitude, bank;

		if (m12 > 0.998f) { // singularity at north pole
			heading = (float) Math.atan2(m31,m22);
			attitude = (float) (Math.PI/2);
			bank = 0;

		}
		else if (m12 < -0.998f) { // singularity at south pole
			heading = (float) Math.atan2(m31,m22);
			attitude = (float) (-Math.PI/2);
			bank = 0;
		}
		else
		{
			heading = (float) Math.atan2(-m13,m11);
			bank = (float) Math.atan2(-m32,m22);
			attitude = (float) Math.asin(m12);
		}

		result.x = bank;
		result.y = heading;
		result.z = attitude;
		
		return result;
	}
	
	public void setEulerAngles(float x, float y, float z)
	{
		// Degreen to Radian
		float rx = x * 0.0174533f;
		float ry = y * 0.0174533f;
		float rz = z * 0.0174533f;
		
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = (float) Math.cos(ry);
		float sh = (float) Math.sin(ry);
			    
		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = (float) Math.cos(rz);
		float sa = (float) Math.sin(rz);
		    
		// Bank ( Roll :: X Axis ) ; Last
		float cb = (float) Math.cos(rx);
		float sb = (float) Math.sin(rx);
	    
	    this.m11 = ch*ca;
	    this.m12 = sa;
	    this.m13 = -sh*ca;
	    
	    this.m21 = sh*sb - ch*sa*cb;
	    this.m22 = ca*cb;
	    this.m23 = sh*sa*cb + ch*sb;

	    this.m31 = ch*sa*sb + sh*cb;
	    this.m32 = -ca*sb;
	    this.m33 = -sh*sa*sb + ch*cb;
	}
	
	public Vector3 getRight()
	{
		return new Vector3(m11, m12, m13);
	}
	
	public void setRight(float x, float y, float z)
	{
		this.setRight(new Vector3(x, y, z));
	}
	
	public void setRight(Vector3 right)
	{
		right = right.getNormalized();
		
		this.m11 = right.x;
		this.m12 = right.y;
		this.m13 = right.z;
		
		if(right.x != 0 && right.z != 0)
		{
			Vector3 up = Vector3.cross(right, getForward()).getNormalized();
			Vector3 forward = Vector3.cross(up, right).getNormalized();
			
			this.m31 = up.x;
			this.m32 = up.y;
			this.m33 = up.z;
			
			this.m21 = forward.x;
			this.m22 = forward.y;
			this.m23 = forward.z;
		}
		else
		{
			Vector3 forward = Vector3.cross(getUp(), right).getNormalized();
			Vector3 up = Vector3.cross(right, forward).getNormalized();
			
			this.m31 = up.x;
			this.m32 = up.y;
			this.m33 = up.z;
			
			this.m21 = forward.x;
			this.m22 = forward.y;
			this.m23 = forward.z;
		}
	}
	
	public Vector3 getForward()
	{
		return new Vector3(m21, m22, m23);
	}
	
	public void setForward(float x, float y, float z)
	{
		this.setForward(new Vector3(x, y, z));
	}
	
	public void setForward(Vector3 forward)
	{
		forward = forward.getNormalized();
		
		this.m21 = forward.x;
		this.m22 = forward.y;
		this.m23 = forward.z;
		
		if(forward.x != 0 && forward.y != 0)
		{
			Vector3 right = Vector3.cross(forward, getUp()).getNormalized();
			Vector3 up = Vector3.cross(right, forward).getNormalized();
			
			this.m31 = up.x;
			this.m32 = up.y;
			this.m33 = up.z;
			
			this.m11 = right.x;
			this.m12 = right.y;
			this.m13 = right.z;
		}
		else
		{
			Vector3 up = Vector3.cross(getRight(), forward).getNormalized();
			Vector3 right = Vector3.cross(forward, up).getNormalized();

			this.m31 = up.x;
			this.m32 = up.y;
			this.m33 = up.z;
			
			this.m11 = right.x;
			this.m12 = right.y;
			this.m13 = right.z;
		}
	}
	
	public Vector3 getUp()
	{
		return new Vector3(m31, m32, m33);
	}
	
	public void setUp(float x, float y, float z)
	{
		this.setUp(new Vector3(x, y, z));
	}
	
	public void setUp(Vector3 up)
	{
		up = up.getNormalized();
		
		this.m31 = up.x;
		this.m32 = up.y;
		this.m33 = up.z;
		
		if(up.x != 0 && up.z != 0)
		{
			Vector3 right = Vector3.cross(getForward(), up).getNormalized();
			Vector3 forward = Vector3.cross(up, right).getNormalized();
			
			this.m21 = forward.x;
			this.m22 = forward.y;
			this.m23 = forward.z;
			
			this.m11 = right.x;
			this.m12 = right.y;
			this.m13 = right.z;
		}
		else
		{
			Vector3 forward = Vector3.cross(up, getRight()).getNormalized();
			Vector3 right = Vector3.cross(forward, up).getNormalized();

			this.m21 = forward.x;
			this.m22 = forward.y;
			this.m23 = forward.z;
			
			this.m11 = right.x;
			this.m12 = right.y;
			this.m13 = right.z;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////

	// Classifier Method
	public static Matrix4 add(Matrix4 lhs, Matrix4 rhs)
	{
		Matrix4 result = new Matrix4();
		
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
		
		return result;
	}

	public static Matrix4 subtract(Matrix4 lhs, Matrix4 rhs)
	{
		Matrix4 result = new Matrix4();
		
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
		
		return result;
	}
	
	public static Matrix4 multiply(Matrix4 lhs, Matrix4 rhs)
	{
		Matrix4 result = new Matrix4();
		
		result.m11 = lhs.m11*rhs.m11 +lhs.m21*rhs.m12 +lhs.m31*rhs.m13 +lhs.m41*rhs.m14;
		result.m12 = lhs.m12*rhs.m11 +lhs.m22*rhs.m12 +lhs.m32*rhs.m13 +lhs.m42*rhs.m14;
		result.m13 = lhs.m13*rhs.m11 +lhs.m23*rhs.m12 +lhs.m33*rhs.m13 +lhs.m43*rhs.m14;
		result.m14 = lhs.m14*rhs.m11 +lhs.m24*rhs.m12 +lhs.m34*rhs.m13 +lhs.m44*rhs.m14;

		result.m21 = lhs.m11*rhs.m21 +lhs.m21*rhs.m22 +lhs.m31*rhs.m23 +lhs.m41*rhs.m24;
		result.m22 = lhs.m12*rhs.m21 +lhs.m22*rhs.m22 +lhs.m32*rhs.m23 +lhs.m42*rhs.m24;
		result.m23 = lhs.m13*rhs.m21 +lhs.m23*rhs.m22 +lhs.m33*rhs.m23 +lhs.m43*rhs.m24;
		result.m24 = lhs.m14*rhs.m21 +lhs.m24*rhs.m22 +lhs.m34*rhs.m23 +lhs.m44*rhs.m24;
		
		result.m31 = lhs.m11*rhs.m31 +lhs.m21*rhs.m32 +lhs.m31*rhs.m33 +lhs.m41*rhs.m34;
		result.m32 = lhs.m12*rhs.m31 +lhs.m22*rhs.m32 +lhs.m32*rhs.m33 +lhs.m42*rhs.m34;
		result.m33 = lhs.m13*rhs.m31 +lhs.m23*rhs.m32 +lhs.m33*rhs.m33 +lhs.m43*rhs.m34;
		result.m34 = lhs.m14*rhs.m31 +lhs.m24*rhs.m32 +lhs.m34*rhs.m33 +lhs.m44*rhs.m34;
		
		result.m41 = lhs.m11*rhs.m41 +lhs.m21*rhs.m42 +lhs.m31*rhs.m43 +lhs.m41*rhs.m44;
		result.m42 = lhs.m12*rhs.m41 +lhs.m22*rhs.m42 +lhs.m32*rhs.m43 +lhs.m42*rhs.m44;
		result.m43 = lhs.m13*rhs.m41 +lhs.m23*rhs.m42 +lhs.m33*rhs.m43 +lhs.m43*rhs.m44;
		result.m44 = lhs.m14*rhs.m41 +lhs.m24*rhs.m42 +lhs.m34*rhs.m43 +lhs.m44*rhs.m44;
		
		return result;
	}
	
	public static Vector4 multiply(Matrix4 lhs, Vector4 rhs)
	{
		Vector4 result = new Vector4();
		
		result.x = (lhs.m11*rhs.x) + (lhs.m21*rhs.y) + (lhs.m31*rhs.z) + (lhs.m41*rhs.w);
		result.y = (lhs.m12*rhs.x) + (lhs.m22*rhs.y) + (lhs.m32*rhs.z) + (lhs.m42*rhs.w);
		result.z = (lhs.m13*rhs.x) + (lhs.m23*rhs.y) + (lhs.m33*rhs.z) + (lhs.m43*rhs.w);
		result.w = (lhs.m14*rhs.x) + (lhs.m24*rhs.y) + (lhs.m34*rhs.z) + (lhs.m44*rhs.w);

		return result;
	}
	
	public static Matrix4 createLookAt(float eyeX, float eyeY, float eyeZ, float cenX, float cenY, float cenZ, float upX, float upY, float upZ)
	{
		return createLookAt(new Vector3(eyeX, eyeY, eyeY), new Vector3(cenX, cenY, cenZ), new Vector3(upX, upY, upZ));
	}

	public static Matrix4 createLookAt(Vector3 eye, Vector3 center, Vector3 up)
	{
		Vector3 forward = Vector3.subtract(eye, center).getNormalized();
		Vector3 left = Vector3.cross(up, forward).getNormalized();
		up = Vector3.cross(forward, left).getNormalized();

		Matrix4 result = new Matrix4();
		
		result.m11 = left.x;
		result.m12 = up.x;
		result.m13 = forward.x;
		
		result.m21 = left.y;
		result.m22 = up.y;
		result.m23 = forward.y;

		result.m31 = left.z;
		result.m32 = up.z;
		result.m33 = forward.z;

		result.m41 = -Vector3.dot(eye, left);
		result.m42 = -Vector3.dot(eye, up);
		result.m43 = -Vector3.dot(eye, forward);
		result.m44 = 1;
		
		return result;
	}
	
	public static Matrix4 createOrtho(float left, float right, float bottom, float top, float near, float far)
	{
		float w = right-left;
		float h = top-bottom;
		float Zf_Zn = far-near;
		
		Matrix4 result = new Matrix4();
		
		result.m11 = 2/w;

		result.m22 = 2/h;
		
		result.m33 = (-2) / Zf_Zn;
		
		result.m41 = -(right + left) / w;
		result.m42 = -(top + bottom) / h;
		result.m43 = -(far + near) / Zf_Zn;
		result.m44 = 1;
		
		return result;
	}
	
	public static Matrix4 createFrustum(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4 result = new Matrix4();
		
		float _2n = (2.0f*near);
		float RsubL = right - left;
		float TsubB = top - bottom;
		float FsubN = far - near;
		
		float A = - ( (far + near) / FsubN );
		float B = - ( (2 * far * near) / FsubN );
		
		result.m11 = _2n / RsubL;
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = _2n / TsubB;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = (right + left) / RsubL;
		result.m32 = (top + bottom) / TsubB;
		result.m33 = A;
		result.m34 = -1;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = B;
		result.m44 = 0;
		
		return result;
	}
	
	////	
	public static Matrix4 add(Matrix4 result, Matrix4 lhs, Matrix4 rhs)
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
		
		return result;
	}

	public static Matrix4 subtract(Matrix4 result, Matrix4 lhs, Matrix4 rhs)
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
		
		return result;
	}
	
	public static Matrix4 multiply(Matrix4 result, Matrix4 lhs, Matrix4 rhs)
	{
		result.m11 = lhs.m11*rhs.m11 +lhs.m21*rhs.m12 +lhs.m31*rhs.m13 +lhs.m41*rhs.m14;
		result.m12 = lhs.m12*rhs.m11 +lhs.m22*rhs.m12 +lhs.m32*rhs.m13 +lhs.m42*rhs.m14;
		result.m13 = lhs.m13*rhs.m11 +lhs.m23*rhs.m12 +lhs.m33*rhs.m13 +lhs.m43*rhs.m14;
		result.m14 = lhs.m14*rhs.m11 +lhs.m24*rhs.m12 +lhs.m34*rhs.m13 +lhs.m44*rhs.m14;

		result.m21 = lhs.m11*rhs.m21 +lhs.m21*rhs.m22 +lhs.m31*rhs.m23 +lhs.m41*rhs.m24;
		result.m22 = lhs.m12*rhs.m21 +lhs.m22*rhs.m22 +lhs.m32*rhs.m23 +lhs.m42*rhs.m24;
		result.m23 = lhs.m13*rhs.m21 +lhs.m23*rhs.m22 +lhs.m33*rhs.m23 +lhs.m43*rhs.m24;
		result.m24 = lhs.m14*rhs.m21 +lhs.m24*rhs.m22 +lhs.m34*rhs.m23 +lhs.m44*rhs.m24;
		
		result.m31 = lhs.m11*rhs.m31 +lhs.m21*rhs.m32 +lhs.m31*rhs.m33 +lhs.m41*rhs.m34;
		result.m32 = lhs.m12*rhs.m31 +lhs.m22*rhs.m32 +lhs.m32*rhs.m33 +lhs.m42*rhs.m34;
		result.m33 = lhs.m13*rhs.m31 +lhs.m23*rhs.m32 +lhs.m33*rhs.m33 +lhs.m43*rhs.m34;
		result.m34 = lhs.m14*rhs.m31 +lhs.m24*rhs.m32 +lhs.m34*rhs.m33 +lhs.m44*rhs.m34;
		
		result.m41 = lhs.m11*rhs.m41 +lhs.m21*rhs.m42 +lhs.m31*rhs.m43 +lhs.m41*rhs.m44;
		result.m42 = lhs.m12*rhs.m41 +lhs.m22*rhs.m42 +lhs.m32*rhs.m43 +lhs.m42*rhs.m44;
		result.m43 = lhs.m13*rhs.m41 +lhs.m23*rhs.m42 +lhs.m33*rhs.m43 +lhs.m43*rhs.m44;
		result.m44 = lhs.m14*rhs.m41 +lhs.m24*rhs.m42 +lhs.m34*rhs.m43 +lhs.m44*rhs.m44;
		
		return result;
	}
	
	public static Vector4 multiply(Vector4 result, Matrix4 lhs, Vector4 rhs)
	{
		result.x = (lhs.m11*rhs.x) + (lhs.m21*rhs.y) + (lhs.m31*rhs.z) + (lhs.m41*rhs.w);
		result.y = (lhs.m12*rhs.x) + (lhs.m22*rhs.y) + (lhs.m32*rhs.z) + (lhs.m42*rhs.w);
		result.z = (lhs.m13*rhs.x) + (lhs.m23*rhs.y) + (lhs.m33*rhs.z) + (lhs.m43*rhs.w);
		result.w = (lhs.m14*rhs.x) + (lhs.m24*rhs.y) + (lhs.m34*rhs.z) + (lhs.m44*rhs.w);

		return result;
	}
	
	public static Matrix4 createLookAt(Matrix4 result, float eyeX, float eyeY, float eyeZ, float cenX, float cenY, float cenZ, float upX, float upY, float upZ)
	{
		return createLookAt(result, new Vector3(eyeX, eyeY, eyeZ), new Vector3(cenX, cenY, cenZ), new Vector3(upX, upY, upZ));
	}

	public static Matrix4 createLookAt(Matrix4 result, Vector3 eye, Vector3 center, Vector3 up)
	{
		Vector3 forward = Vector3.subtract(eye, center).getNormalized();
		Vector3 left = Vector3.cross(up, forward).getNormalized();
		up = Vector3.cross(forward, left).getNormalized();

		result.setIdentity();
		
		result.m11 = left.x;
		result.m12 = up.x;
		result.m13 = forward.x;
		
		result.m21 = left.y;
		result.m22 = up.y;
		result.m23 = forward.y;

		result.m31 = left.z;
		result.m32 = up.z;
		result.m33 = forward.z;

		result.m41 = -Vector3.dot(eye, left);
		result.m42 = -Vector3.dot(eye, up);
		result.m43 = -Vector3.dot(eye, forward);
		result.m44 = 1;
		
		return result;
	}
	
	public static Matrix4 createOrtho(Matrix4 result, float left, float right, float bottom, float top, float near, float far)
	{
		float w = right-left;
		float h = top-bottom;
		float Zf_Zn = far-near;

		result.setIdentity();
		
		result.m11 = 2/w;

		result.m22 = 2/h;
		
		result.m33 = (-2) / Zf_Zn;
		
		result.m41 = -(right + left) / w;
		result.m42 = -(top + bottom) / h;
		result.m43 = -(far + near) / Zf_Zn;
		
		return result;
	}
	
	public static Matrix4 createFrustum(Matrix4 result, float left, float right, float bottom, float top, float near, float far)
	{
		float _2n = (2.0f*near);
		float RsubL = right - left;
		float TsubB = top - bottom;
		float FsubN = far - near;
		
		float A = - ( (far + near) / FsubN );
		float B = - ( (2 * far * near) / FsubN );
		
		result.m11 = _2n / RsubL;
		result.m12 = 0;
		result.m13 = 0;
		result.m14 = 0;
		
		result.m21 = 0;
		result.m22 = _2n / TsubB;
		result.m23 = 0;
		result.m24 = 0;
		
		result.m31 = (right + left) / RsubL;
		result.m32 = (top + bottom) / TsubB;
		result.m33 = A;
		result.m34 = -1;
		
		result.m41 = 0;
		result.m42 = 0;
		result.m43 = B;
		result.m44 = 0;
		
		return result;
	}
	
	public static Matrix4 createTRS_YZX(Matrix4 result, float tx, float ty, float tz, float ax, float ay, float az, float sx, float sy, float sz)
	{
		// Degreen to Radian
		float rx = ax * 0.0174533f;
		float ry = ay * 0.0174533f;
		float rz = az * 0.0174533f;
		
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = (float) Math.cos(ry);
		float sh = (float) Math.sin(ry);
			    
		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = (float) Math.cos(rz);
		float sa = (float) Math.sin(rz);
		    
		// Bank ( Roll :: X Axis ) ; Last
		float cb = (float) Math.cos(rx);
		float sb = (float) Math.sin(rx);

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
		
		result.m41 = tx;
		result.m42 = ty;
		result.m43 = tz;
		result.m44 = 1;
		
		return result;
	}
	
	public static Matrix4 createTRS_ZYX(Matrix4 result, float tx, float ty, float tz, float ax, float ay, float az, float sx, float sy, float sz)
	{
		// Degreen to Radian
		float rx = ax * 0.0174533f;
		float ry = ay * 0.0174533f;
		float rz = az * 0.0174533f;
		
		// Heading ( Yaw :: Z Axis ) ; First
		float ch = (float) Math.cos(rz);
		float sh = (float) Math.sin(rz);

		// Attitude ( Pitch :: Y Axis ) ; Second
		float ca = (float) Math.cos(ry);
		float sa = (float) Math.sin(ry);

		// Bank ( Roll :: X Axis ) ; Last
		float cb = (float) Math.cos(rx);
		float sb = (float) Math.sin(rx);
		//
		//
		result.m11 = (ch * ca) * sx;
		result.m12 = (sh * ca) * sy;
		result.m13 = -sa * sz;
		result.m14 = 0;

		result.m21 = ( (-sh * cb) + (sb * (sa*ch)) ) * sx;
		result.m22 = ( (ch * cb) + (sb * (sa*sh)) ) * sy;
		result.m23 = (sb * ca) * sz;
		result.m24 = 0;

		result.m31 = ( (-sb * -sh) + (cb * (sa*ch)) ) * sx;
		result.m32 = ( (-sb * ch) + (cb * (sa*sh)) ) * sy;
		result.m33 = (cb * ca) * sz;
		result.m34 = 0;

		result.m41 = tx;
		result.m42 = ty;
		result.m43 = tz;
		result.m44 = 1;
		
		return result;
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
		float radian = degree * 0.0174533f;
		float cos = (float) Math.cos(radian);
		float sin = (float) Math.sin(radian);
		
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
		float radian = degree * 0.0174533f;
		float cos = (float) Math.cos(radian);
		float sin = (float) Math.sin(radian);
		
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
		float radian = degree * 0.0174533f;
		float cos = (float) Math.cos(radian);
		float sin = (float) Math.sin(radian);
		
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
	
	public static Matrix4 createFromEulerAngles(float x, float y, float z)
	{
		// Degreen to Radian
		float rx = x * 0.0174533f;
		float ry = y * 0.0174533f;
		float rz = z * 0.0174533f;
		
		// Heading ( Yaw :: Y Axis ) ; First
		float ch = (float) Math.cos(ry);
		float sh = (float) Math.sin(ry);
			    
		// Attitude ( Pitch :: Z Axis ) ; Second
		float ca = (float) Math.cos(rz);
		float sa = (float) Math.sin(rz);
		    
		// Bank ( Roll :: X Axis ) ; Last
		float cb = (float) Math.cos(rx);
		float sb = (float) Math.sin(rx);
	    
		Matrix4 result = new Matrix4();
		
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
	    
	    return result;
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
}
