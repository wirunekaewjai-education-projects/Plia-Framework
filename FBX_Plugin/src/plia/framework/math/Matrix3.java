package plia.framework.math;

public final class Matrix3
{
	public float m11 = 1, m12, m13, 
				 m21, m22 = 1, m23,
			     m31, m32, m33 = 1;
	
	public Matrix3()
	{
		
	}
	
	public Matrix3(Matrix3 m)
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
	
	public Matrix3(float M11, float M12, float M13, float M21, float M22,
			float M23, float M31, float M32, float M33)
	{
		m11 = M11;
		m12 = M12;
		m13 = M13;
		
		m21 = M21;
		m22 = M22;
		m23 = M23;
		
		m31 = M31;
		m32 = M32;
		m33 = M33;
	}
	
	public Matrix3(float[] m)
	{
		m11 = m[0];
		m12 = m[1];
		m13 = m[2];

		m21 = m[3];
		m22 = m[4];
		m23 = m[5];

		m31 = m[6];
		m32 = m[7];
		m33 = m[8];
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		float mm11 = ( Math.round(m11 * 1000) ) / 1000f;
		float mm12 = ( Math.round(m12 * 1000) ) / 1000f;
		float mm13 = ( Math.round(m13 * 1000) ) / 1000f;

		float mm21 = ( Math.round(m21 * 1000) ) / 1000f;
		float mm22 = ( Math.round(m22 * 1000) ) / 1000f;
		float mm23 = ( Math.round(m23 * 1000) ) / 1000f;

		float mm31 = ( Math.round(m31 * 1000) ) / 1000f;
		float mm32 = ( Math.round(m32 * 1000) ) / 1000f;
		float mm33 = ( Math.round(m33 * 1000) ) / 1000f;
		
		return 	"{M11 : "+mm11+", M12 : "+mm12+", M13 : "+mm13+"}\n"+
				"{M21 : "+mm21+", M22 : "+mm22+", M23 : "+mm23+"}\n"+
				"{M31 : "+mm31+", M32 : "+mm32+", M33 : "+mm33+"}";
	}
	
	@Override
	public Matrix3 clone() 
	{
		return new Matrix3(this);
	}
	
	public void copyTo(float[] m)
	{
		m[0] = m11;
		m[1] = m12;
		m[2] = m13;

		m[3] = m21;
		m[4] = m22;
		m[5] = m23;

		m[6] = m31;
		m[7] = m32;
		m[8] = m33;
	}
	
	public Quaternion toQuaternion()
	{
		float x = 0, y = 0, z = 0, w = 0;
		
		if(m11 + m22 + m33 > 0)
		{
			float S = (float) (Math.sqrt(m11 + m22 + m33 + 1.0) * 2.0f);
			w = 0.25f * S;
			x = (m23 - m32) / S;
			y = (m31 - m13) / S;
			z = (m12 - m21) / S;
		}
		else if(m11 > m22 && m11 > m33)
		{
			float S = (float) (Math.sqrt(1.0 + m11 - m22 - m33) * 2.0f);
			w = (m23 - m32) / S;
			x = 0.25f * S;
			y = (m21 + m12) / S;
			z = (m31 + m13) / S;
		}
		else if(m22 > m33)
		{
			float S = (float) (Math.sqrt(1.0 + m22 - m11 - m33) * 2.0f);
			w = (m31 - m13) / S;
			x = (m21 + m12) / S;
			y = 0.25f * S;
			z = (m32 + m23)/S;
		}
		else
		{
			float S = (float) (Math.sqrt(1.0 + m33 - m11 - m22) * 2.0f);
			w = (m12 - m21) / S;
			x = (m31 + m13) / S;
			y = (m32 + m23) / S;
			z = 0.25f * S;
		}
		
		return new Quaternion(x, y, z, w);
	}
	
	// Methods
	public void setIdentity()
	{
		this.m11 = 1;
		this.m22 = 1;
		this.m33 = 1;

		this.m12 = 0;
		this.m13 = 0;

		this.m21 = 0;
		this.m23 = 0;

		this.m31 = 0;
		this.m32 = 0;
	}
	
	public float getDeterminant()
	{
		float a21a32a44 = m21*m32;
		float a21a33a44 = m21*m33;
		float a22a31a44 = m22*m31;
		float a22a33a44 = m22*m33;
		float a23a31a44 = m23*m31;
		float a23a32a44 = m23*m32;
		
		// Calculate DETERMINANT
		return (m11 * a22a33a44) + (m12 * a23a31a44) + (m13 * a21a32a44)
				- (m11 * a23a32a44) - (m12 * a21a33a44) - (m13 * a22a31a44);
	}
	
	public Matrix3 getInvert()
	{
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
		float b13 = (m12 * m23) - (m13 * m22);
		
		float b21 = (a23a31a44) - (a21a33a44);
		float b22 = (m11 * m33) - (m13 * m31);
		float b23 = (m13 * m21) - (m11 * m23);
		
		float b31 = (a21a32a44) - (a22a31a44);
		float b32 = (m12 * m31) - (m11 * m32);
		float b33 = (m11 * m22) - (m12 * m21);

		Matrix3 result = new Matrix3();
		// Calculate Invert Matrix 3x3
		result.m11 = b11 / det;
		result.m12 = b12 / det;
		result.m13 = b13 / det;
		
		result.m21 = b21 / det;
		result.m22 = b22 / det;
		result.m23 = b23 / det;

		result.m31 = b31 / det;
		result.m32 = b32 / det;
		result.m33 = b33 / det;
		
		return result;
	}
	
	public Matrix3 getTranspose()
	{
		Matrix3 result = new Matrix3();
		
		result.m11 = m11;
		result.m12 = m21;
		result.m13 = m31;

		result.m21 = m12;
		result.m22 = m22;
		result.m23 = m32;

		result.m31 = m13;
		result.m32 = m23;
		result.m33 = m33;
		
		return result;
	}
	
	public Matrix3 getInvertTranspose()
	{
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

		Matrix3 result = new Matrix3();
		// Calculate Invert Matrix 3x3
		
		float m11 = b11 / det;
		float m12 = b12 / det;
		float m13 = b13 / det;
		
		float m21 = b21 / det;
		float m22 = b22 / det;
		float m23 = b23 / det;

		float m31 = b31 / det;
		float m32 = b32 / det;
		float m33 = b33 / det;
		
		// Transpose
		result.m11 = m11;
		result.m12 = m21;
		result.m13 = m31;

		result.m21 = m12;
		result.m22 = m22;
		result.m23 = m32;

		result.m31 = m13;
		result.m32 = m23;
		result.m33 = m33;
		
		return result;
	}
	
	public Matrix3 add(Matrix3 rhs)
	{
		this.m11 += rhs.m11;
		this.m12 += rhs.m12;
		this.m13 += rhs.m13;

		this.m21 += rhs.m21;
		this.m22 += rhs.m22;
		this.m23 += rhs.m23;

		this.m31 += rhs.m31;
		this.m32 += rhs.m32;
		this.m33 += rhs.m33;

		return this;
	}
	
	public Matrix3 subtract(Matrix3 rhs)
	{
		this.m11 -= rhs.m11;
		this.m12 -= rhs.m12;
		this.m13 -= rhs.m13;

		this.m21 -= rhs.m21;
		this.m22 -= rhs.m22;
		this.m23 -= rhs.m23;

		this.m31 -= rhs.m31;
		this.m32 -= rhs.m32;
		this.m33 -= rhs.m33;

		return this;
	}
	
	public Matrix3 multiply(Matrix3 rhs)
	{
		float mm11 = m11*rhs.m11 +m21*rhs.m12 +m31*rhs.m13;
		float mm12 = m12*rhs.m11 +m22*rhs.m12 +m32*rhs.m13;
		float mm13 = m13*rhs.m11 +m23*rhs.m12 +m33*rhs.m13;

		float mm21 = m11*rhs.m21 +m21*rhs.m22 +m31*rhs.m23;
		float mm22 = m12*rhs.m21 +m22*rhs.m22 +m32*rhs.m23;
		float mm23 = m13*rhs.m21 +m23*rhs.m22 +m33*rhs.m23;

		float mm31 = m11*rhs.m31 +m21*rhs.m32 +m31*rhs.m33;
		float mm32 = m12*rhs.m31 +m22*rhs.m32 +m32*rhs.m33;
		float mm33 = m13*rhs.m31 +m23*rhs.m32 +m33*rhs.m33;
		
		m11 = mm11;
		m12 = mm12;
		m13 = mm13;
		
		m21 = mm21;
		m22 = mm22;
		m23 = mm23;
		
		m31 = mm31;
		m32 = mm32;
		m33 = mm33;
		
		return this;
	}
	
	public Vector3 multiply(Vector3 rhs)
	{
		Vector3 result = new Vector3();
		
		result.x = (m11 * rhs.x) + (m21 * rhs.y) + (m31 * rhs.z);
		result.y = (m12 * rhs.x) + (m22 * rhs.y) + (m32 * rhs.z);
		result.z = (m13 * rhs.x) + (m23 * rhs.y) + (m33 * rhs.z);

		return result;
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
	public static Matrix3 add(Matrix3 lhs, Matrix3 rhs)
	{
		Matrix3 result = new Matrix3();
		
		result.m11 = lhs.m11+rhs.m11;
		result.m12 = lhs.m12+rhs.m12;
		result.m13 = lhs.m13+rhs.m13;

		result.m21 = lhs.m21+rhs.m21;
		result.m22 = lhs.m22+rhs.m22;
		result.m23 = lhs.m23+rhs.m23;

		result.m31 = lhs.m31+rhs.m31;
		result.m32 = lhs.m32+rhs.m32;
		result.m33 = lhs.m33+rhs.m33;

		return result;
	}

	public static Matrix3 subtract(Matrix3 lhs, Matrix3 rhs)
	{
		Matrix3 result = new Matrix3();
		
		result.m11 = lhs.m11-rhs.m11;
		result.m12 = lhs.m12-rhs.m12;
		result.m13 = lhs.m13-rhs.m13;

		result.m21 = lhs.m21-rhs.m21;
		result.m22 = lhs.m22-rhs.m22;
		result.m23 = lhs.m23-rhs.m23;

		result.m31 = lhs.m31-rhs.m31;
		result.m32 = lhs.m32-rhs.m32;
		result.m33 = lhs.m33-rhs.m33;

		return result;
	}
	
	public static Matrix3 multiply(Matrix3 lhs, Matrix3 rhs)
	{
		Matrix3 result = new Matrix3();
		
		result.m11 = lhs.m11*rhs.m11 +lhs.m21*rhs.m12 +lhs.m31*rhs.m13;
		result.m12 = lhs.m12*rhs.m11 +lhs.m22*rhs.m12 +lhs.m32*rhs.m13;
		result.m13 = lhs.m13*rhs.m11 +lhs.m23*rhs.m12 +lhs.m33*rhs.m13;

		result.m21 = lhs.m11*rhs.m21 +lhs.m21*rhs.m22 +lhs.m31*rhs.m23;
		result.m22 = lhs.m12*rhs.m21 +lhs.m22*rhs.m22 +lhs.m32*rhs.m23;
		result.m23 = lhs.m13*rhs.m21 +lhs.m23*rhs.m22 +lhs.m33*rhs.m23;

		result.m31 = lhs.m11*rhs.m31 +lhs.m21*rhs.m32 +lhs.m31*rhs.m33;
		result.m32 = lhs.m12*rhs.m31 +lhs.m22*rhs.m32 +lhs.m32*rhs.m33;
		result.m33 = lhs.m13*rhs.m31 +lhs.m23*rhs.m32 +lhs.m33*rhs.m33;
		
		return result;
	}
	
	public static Vector3 multiply(Matrix3 lhs, Vector3 rhs)
	{
		Vector3 result = new Vector3();
		
		result.x = (lhs.m11 * rhs.x) + (lhs.m21 * rhs.y) + (lhs.m31 * rhs.z);
		result.y = (lhs.m12 * rhs.x) + (lhs.m22 * rhs.y) + (lhs.m32 * rhs.z);
		result.z = (lhs.m13 * rhs.x) + (lhs.m23 * rhs.y) + (lhs.m33 * rhs.z);

		return result;
	}
	
	public static Matrix3 createFromEulerAngles(float x, float y, float z)
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
	    
		Matrix3 result = new Matrix3();
		
	    result.m11 = ch*ca;
	    result.m12 = sa;
	    result.m13 = -sh*ca;

	    result.m21 = sh*sb - ch*sa*cb;
	    result.m22 = ca*cb;
	    result.m23 = sh*sa*cb + ch*sb;

	    result.m31 = ch*sa*sb + sh*cb;
	    result.m32 = -ca*sb;
	    result.m33 = -sh*sa*sb + ch*cb;
	    
	    return result;
	}
}
