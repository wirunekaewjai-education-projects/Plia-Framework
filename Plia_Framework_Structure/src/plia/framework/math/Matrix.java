package plia.framework.math;

import plia.framework.math.Mathf;
import plia.framework.math.Vector4;

public final class Matrix
{
	/* Reference :: Martin John Baker ( http://www.euclideanspace.com/maths/geometry/rotations/index.htm ) */
	
	/*
		
	*/
	public static String toString(float[] matrix)
	{
		// TODO Auto-generated method stub
		float mm11 = ( Math.round(matrix[0] * 1000) ) / 1000f;
		float mm12 = ( Math.round(matrix[1] * 1000) ) / 1000f;
		float mm13 = ( Math.round(matrix[2] * 1000) ) / 1000f;
		float mm14 = ( Math.round(matrix[3] * 1000) ) / 1000f;
		
		float mm21 = ( Math.round(matrix[4] * 1000) ) / 1000f;
		float mm22 = ( Math.round(matrix[5] * 1000) ) / 1000f;
		float mm23 = ( Math.round(matrix[6] * 1000) ) / 1000f;
		float mm24 = ( Math.round(matrix[7] * 1000) ) / 1000f;
		
		float mm31 = ( Math.round(matrix[8] * 1000) ) / 1000f;
		float mm32 = ( Math.round(matrix[9] * 1000) ) / 1000f;
		float mm33 = ( Math.round(matrix[10] * 1000) ) / 1000f;
		float mm34 = ( Math.round(matrix[11] * 1000) ) / 1000f;
		
		float mm41 = ( Math.round(matrix[12] * 1000) ) / 1000f;
		float mm42 = ( Math.round(matrix[13] * 1000) ) / 1000f;
		float mm43 = ( Math.round(matrix[14] * 1000) ) / 1000f;
		float mm44 = ( Math.round(matrix[15] * 1000) ) / 1000f;
		
		return 	"{M11 : "+mm11+", M12 : "+mm12+", M13 : "+mm13+", M14 : "+mm14+"}\n"+
				"{M21 : "+mm21+", M22 : "+mm22+", M23 : "+mm23+", M24 : "+mm24+"}\n"+
				"{M31 : "+mm31+", M32 : "+mm32+", M33 : "+mm33+", M34 : "+mm34+"}\n"+
				"{M41 : "+mm41+", M42 : "+mm42+", M43 : "+mm43+", M44 : "+mm44+"}";
	}
	
	
	
	// Classifier Method
	public static void add(float[] result, float[] lhs, float[] rhs)
	{
		result[0] = lhs[0]+rhs[0];
		result[1] = lhs[1]+rhs[1];
		result[2] = lhs[2]+rhs[2];
		result[3] = lhs[3]+rhs[3];

		result[4] = lhs[4]+rhs[4];
		result[5] = lhs[5]+rhs[5];
		result[6] = lhs[6]+rhs[6];
		result[7] = lhs[7]+rhs[7];
		
		result[8] = lhs[8]+rhs[8];
		result[9] = lhs[9]+rhs[9];
		result[10] = lhs[10]+rhs[10];
		result[11] = lhs[11]+rhs[11];
		
		result[12] = lhs[12]+rhs[12];
		result[13] = lhs[13]+rhs[13];
		result[14] = lhs[14]+rhs[14];
		result[15] = lhs[15]+rhs[15];
	}

	public static void subtract(float[] result, float[] lhs, float[] rhs)
	{
		result[0] = lhs[0]-rhs[0];
		result[1] = lhs[1]-rhs[1];
		result[2] = lhs[2]-rhs[2];
		result[3] = lhs[3]-rhs[3];

		result[4] = lhs[4]-rhs[4];
		result[5] = lhs[5]-rhs[5];
		result[6] = lhs[6]-rhs[6];
		result[7] = lhs[7]-rhs[7];
		
		result[8] = lhs[8]-rhs[8];
		result[9] = lhs[9]-rhs[9];
		result[10] = lhs[10]-rhs[10];
		result[11] = lhs[11]-rhs[11];
		
		result[12] = lhs[12]-rhs[12];
		result[13] = lhs[13]-rhs[13];
		result[14] = lhs[14]-rhs[14];
		result[15] = lhs[15]-rhs[15];
	}
	
	public static void multiply(float[] result, float[] lhs, float[] rhs)
	{
		float m11 = rhs[0]*lhs[0] +rhs[1]*lhs[4] +rhs[2]*lhs[8] +rhs[3]*lhs[12];
		float m12 = rhs[0]*lhs[1] +rhs[1]*lhs[5] +rhs[2]*lhs[9] +rhs[3]*lhs[13];
		float m13 = rhs[0]*lhs[2] +rhs[1]*lhs[6] +rhs[2]*lhs[10] +rhs[3]*lhs[14];
		float m14 = rhs[0]*lhs[3] +rhs[1]*lhs[7] +rhs[2]*lhs[11] +rhs[3]*lhs[15];

		float m21 = rhs[4]*lhs[0] +rhs[5]*lhs[4] +rhs[6]*lhs[8] +rhs[7]*lhs[12];
		float m22 = rhs[4]*lhs[1] +rhs[5]*lhs[5] +rhs[6]*lhs[9] +rhs[7]*lhs[13];
		float m23 = rhs[4]*lhs[2] +rhs[5]*lhs[6] +rhs[6]*lhs[10] +rhs[7]*lhs[14];
		float m24 = rhs[4]*lhs[3] +rhs[5]*lhs[7] +rhs[6]*lhs[11] +rhs[7]*lhs[15];
		
		float m31 = rhs[8]*lhs[0] +rhs[9]*lhs[4] +rhs[10]*lhs[8] +rhs[11]*lhs[12];
		float m32 = rhs[8]*lhs[1] +rhs[9]*lhs[5] +rhs[10]*lhs[9] +rhs[11]*lhs[13];
		float m33 = rhs[8]*lhs[2] +rhs[9]*lhs[6] +rhs[10]*lhs[10] +rhs[11]*lhs[14];
		float m34 = rhs[8]*lhs[3] +rhs[9]*lhs[7] +rhs[10]*lhs[11] +rhs[11]*lhs[15];
		
		float m41 = rhs[12]*lhs[0] +rhs[13]*lhs[4] +rhs[14]*lhs[8] +rhs[15]*lhs[12];
		float m42 = rhs[12]*lhs[1] +rhs[13]*lhs[5] +rhs[14]*lhs[9] +rhs[15]*lhs[13];
		float m43 = rhs[12]*lhs[2] +rhs[13]*lhs[6] +rhs[14]*lhs[10] +rhs[15]*lhs[14];
		float m44 = rhs[12]*lhs[3] +rhs[13]*lhs[7] +rhs[14]*lhs[11] +rhs[15]*lhs[15];
		
		result[0] = m11;
		result[1] = m12;
		result[2] = m13;
		result[3] = m14;

		result[4] = m21;
		result[5] = m22;
		result[6] = m23;
		result[7] = m24;
		
		result[8] = m31;
		result[9] = m32;
		result[10] = m33;
		result[11] = m34;
		
		result[12] = m41;
		result[13] = m42;
		result[14] = m43;
		result[15] = m44;
	}
	
	public static void multiply(Vector4 result, float[] lhs, Vector4 rhs)
	{
		float x = (lhs[0]*rhs.x) + (lhs[4]*rhs.y) + (lhs[8]*rhs.z) + (lhs[12]*rhs.w);
		float y = (lhs[1]*rhs.x) + (lhs[5]*rhs.y) + (lhs[9]*rhs.z) + (lhs[13]*rhs.w);
		float z = (lhs[2]*rhs.x) + (lhs[6]*rhs.y) + (lhs[10]*rhs.z) + (lhs[14]*rhs.w);
		float w = (lhs[3]*rhs.x) + (lhs[7]*rhs.y) + (lhs[11]*rhs.z) + (lhs[15]*rhs.w);
		
		result.x = x;
		result.y = y;
		result.z = z;
		result.w = w;
	}
	
	public static void transpose(float[] result, float[] m)
	{
		float m11 = m[0];
		float m12 = m[4];
		float m13 = m[8];
		float m14 = m[12];

		float m21 = m[1];
		float m22 = m[5];
		float m23 = m[9];
		float m24 = m[13];
		
		float m31 = m[2];
		float m32 = m[6];
		float m33 = m[10];
		float m34 = m[14];
		
		float m41 = m[3];
		float m42 = m[7];
		float m43 = m[11];
		float m44 = m[15];
		
		result[0] = m11;
		result[1] = m12;
		result[2] = m13;
		result[3] = m14;

		result[4] = m21;
		result[5] = m22;
		result[6] = m23;
		result[7] = m24;
		
		result[8] = m31;
		result[9] = m32;
		result[10] = m33;
		result[11] = m34;
		
		result[12] = m41;
		result[13] = m42;
		result[14] = m43;
		result[15] = m44;
	}
	
	// Methods
	public float determinant(float[] matrix)
	{
		float a11a22 = matrix[0] * matrix[5];
		float a11a23 = matrix[0] * matrix[6];
		float a11a24 = matrix[0] * matrix[7];

		float a12a21 = matrix[1] * matrix[4];
		float a12a23 = matrix[1] * matrix[6];
		float a12a24 = matrix[1] * matrix[7];

		float a13a21 = matrix[2] * matrix[4];
		float a13a22 = matrix[2] * matrix[5];
		float a13a24 = matrix[2] * matrix[7];

		float a14a21 = matrix[3] * matrix[4];
		float a14a22 = matrix[3] * matrix[5];
		float a14a23 = matrix[3] * matrix[6];

		float a31a42 = matrix[8] * matrix[13];
		float a31a43 = matrix[8] * matrix[14];
		float a31a44 = matrix[8] * matrix[15];

		float a32a41 = matrix[9] * matrix[12];
		float a32a43 = matrix[9] * matrix[14];
		float a32a44 = matrix[9] * matrix[15];

		float a33a41 = matrix[10] * matrix[12];
		float a33a42 = matrix[10] * matrix[13];
		float a33a44 = matrix[10] * matrix[15];

		float a34a41 = matrix[11] * matrix[12];
		float a34a42 = matrix[11] * matrix[13];
		float a34a43 = matrix[11] * matrix[14];
			
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
	
	public static void invert(float[] result, float[] m)
	{
		float a11a22 = m[0]*m[5];
		float a11a23 = m[0]*m[6];
		float a11a24 = m[0]*m[7];
		
		float a12a21 = m[1]*m[4];
		float a12a23 = m[1]*m[6];
		float a12a24 = m[1]*m[7];
		
		float a13a21 = m[2]*m[4];
		float a13a22 = m[2]*m[5];
		float a13a24 = m[2]*m[7];
		
		float a14a21 = m[3]*m[4];
		float a14a22 = m[3]*m[5];
		float a14a23 = m[3]*m[6];

		float a31a42 = m[8]*m[13];
		float a31a43 = m[8]*m[14];
		float a31a44 = m[8]*m[15];

		float a32a41 = m[9]*m[12];
		float a32a43 = m[9]*m[14];
		float a32a44 = m[9]*m[15];
		
		float a33a41 = m[10]*m[12];
		float a33a42 = m[10]*m[13];
		float a33a44 = m[10]*m[15];

		float a34a41 = m[11]*m[12];
		float a34a42 = m[11]*m[13];
		float a34a43 = m[11]*m[14];

		float a21a32a43 = m[4]*a32a43;
		float a21a32a44 = m[4]*a32a44;
		
		float a21a33a42 = m[4]*a33a42;
		float a21a33a44 = m[4]*a33a44;

		float a21a34a42 = m[4]*a34a42;
		float a21a34a43 = m[4]*a34a43;
		
		float a22a31a43 = m[5]*a31a43;
		float a22a31a44 = m[5]*a31a44;
		
		float a22a33a41 = m[5]*a33a41;
		float a22a33a44 = m[5]*a33a44;
		
		float a22a34a41 = m[5]*a34a41;
		float a22a34a43 = m[5]*a34a43;
		
		float a23a31a42 = m[6]*a31a42;
		float a23a31a44 = m[6]*a31a44;
		
		float a23a32a41 = m[6]*a32a41;
		float a23a32a44 = m[6]*a32a44;
		
		float a23a34a41 = m[6]*a34a41;
		float a23a34a42 = m[6]*a34a42;
		
		float a24a31a42 = m[7]*a31a42;
		float a24a31a43 = m[7]*a31a43;
		
		float a24a32a43 = m[7]*a32a43;
		float a24a32a41 = m[7]*a32a41;
		
		float a24a33a41 = m[7]*a33a41;
		float a24a33a42 = m[7]*a33a42;
		
		// Calculate DETERMINANT
		float det =   (m[0] * a22a33a44) + (m[0] * a23a34a42) + (m[0] * a24a32a43)
					+ (m[1] * a21a34a43) + (m[1] * a23a31a44) + (m[1] * a24a33a41)
					+ (m[2] * a21a32a44) + (m[2] * a22a34a41) + (m[2] * a24a31a42)
					+ (m[3] * a21a33a42) + (m[3] * a22a31a43) + (m[3] * a23a32a41)
					- (m[0] * a22a34a43) - (m[0] * a23a32a44) - (m[0] * a24a33a42)
					- (m[1] * a21a33a44) - (m[1] * a23a34a41) - (m[1] * a24a31a43)
					- (m[2] * a21a34a42) - (m[2] * a22a31a44) - (m[2] * a24a32a41)
					- (m[3] * a21a32a43) - (m[3] * a22a33a41) - (m[3] * a23a31a42);
		
		// Calculate ADJOINT
		float b11 = (a22a33a44) + (a23a34a42) + (a24a32a43) - (a22a34a43) - (a23a32a44) - (a24a33a42);
		float b12 = (m[1] * a34a43) + (m[2] * a32a44) + (m[3] * a33a42) - (m[1] * a33a44) - (m[2] * a34a42) - (m[3] * a32a43);
		float b13 = (a12a23 * m[15]) + (a13a24 * m[13]) + (a14a22 * m[14]) - (a12a24 * m[14]) - (a13a22 * m[15]) - (a14a23 * m[13]);
		float b14 = (a12a24 * m[10]) + (a13a22 * m[11]) + (a14a23 * m[9]) - (a12a23 * m[11]) - (a13a24 * m[9]) - (a14a22 * m[10]);
		
		float b21 = (a21a34a43) + (a23a31a44) + (a24a33a41) - (a21a33a44) - (a23a34a41) - (a24a31a43);
		float b22 = (m[0] * a33a44) + (m[2] * a34a41) + (m[3] * a31a43) - (m[0] * a34a43) - (m[2] * a31a44) - (m[3] * a33a41);
		float b23 = (a11a24 * m[14]) + (a13a21 * m[15]) + (a14a23 * m[12]) - (a11a23 * m[15]) - (a13a24 * m[12]) - (a14a21 * m[14]);
		float b24 = (a11a23 * m[11]) + (a13a24 * m[8]) + (a14a21 * m[10]) - (a11a24 * m[10]) - (a13a21 * m[11]) - (a14a23 * m[8]);
		
		float b31 = (a21a32a44) + (a22a34a41) + (a24a31a42) - (a21a34a42) - (a22a31a44) - (a24a32a41);
		float b32 = (m[0] * a34a42) + (m[1] * a31a44) + (m[3] * a32a41) - (m[0] * a32a44) - (m[1] * a34a41) - (m[3] * a31a42);
		float b33 = (a11a22 * m[15]) + (a12a24 * m[12]) + (a14a21 * m[13]) - (a11a24 * m[13]) - (a12a21 * m[15]) - (a14a22 * m[12]);
		float b34 = (a11a24 * m[9]) + (a12a21 * m[11]) + (a14a22 * m[8]) - (a11a22 * m[11]) - (a12a24 * m[8]) - (a14a21 * m[9]);
		
		float b41 = (a21a33a42) + (a22a31a44) + (a23a32a41) - (a21a32a43) - (a22a33a41) - (a23a31a42);
		float b42 = (m[0] * a32a43) + (m[1] * a33a41) + (m[2] * a31a42) - (m[0] * a33a42) - (m[1] * a31a43) - (m[2] * a32a41);
		float b43 = (a11a23 * m[13]) + (a12a21 * m[14]) + (a13a22 * m[12]) - (a11a22 * m[14]) - (a12a23 * m[12]) - (a13a21 * m[13]);
		float b44 = (a11a22 * m[10]) + (a12a23 * m[8]) + (a13a21 * m[9]) - (a11a23 * m[9]) - (a12a21 * m[10]) - (a13a22 * m[8]);

		// Calculate Invert Matrix 4x4
		result[0] = b11 / det;
		result[1] = b12 / det;
		result[2] = b13 / det;
		result[3] = b14 / det;
		result[4] = b21 / det;
		result[5] = b22 / det;
		result[6] = b23 / det;
		result[7] = b24 / det;
		result[8] = b31 / det;
		result[9] = b32 / det;
		result[10] = b33 / det;
		result[11] = b34 / det;
		result[12] = b41 / det;
		result[13] = b42 / det;
		result[14] = b43 / det;
		result[15] = b44 / det;
	}
	
	public static void createFromQuaternion(float[] result, Quaternion q)
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
	
	public static void createTranslation(float[] result, float x, float y, float z)
	{
		result[0] = 1;
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = 0;
		result[5] = 1;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = 0;
		result[9] = 0;
		result[10] = 1;
		result[11] = 0;
		
		result[12] = x;
		result[13] = y;
		result[14] = z;
		result[15] = 1;
	}
	
	public static void createRotationX(float[] result, float degree)
	{
		float cos = Mathf.cos(degree);
		float sin = Mathf.sin(degree);
		
		result[0] = 1;
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = 0;
		result[5] = cos;
		result[6] = sin;
		result[7] = 0;
		
		result[8] = 0;
		result[9] = -sin;
		result[10] = cos;
		result[11] = 0;
		
		result[12] = 0;
		result[13] = 0;
		result[14] = 0;
		result[15] = 1;
	}
	
	public static void createRotationY(float[] result, float degree)
	{
		float cos = Mathf.cos(degree);
		float sin = Mathf.sin(degree);
		
		result[0] = cos;
		result[1] = 0;
		result[2] = -sin;
		result[3] = 0;
		
		result[4] = 0;
		result[5] = 1;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = sin;
		result[9] = 0;
		result[10] = cos;
		result[11] = 0;
		
		result[12] = 0;
		result[13] = 0;
		result[14] = 0;
		result[15] = 1;
	}
	
	public static void createRotationZ(float[] result, float degree)
	{
		float cos = Mathf.cos(degree);
		float sin = Mathf.sin(degree);
		
		result[0] = cos;
		result[1] = sin;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = -sin;
		result[5] = cos;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = 0;
		result[9] = 0;
		result[10] = 1;
		result[11] = 0;
		
		result[12] = 0;
		result[13] = 0;
		result[14] = 0;
		result[15] = 1;
	}
	
	public static void createScale(float[] result, float sx, float sy, float sz)
	{
		result[0] = sx;
		result[5] = sy;
		result[10] = sz;
		result[15] = 1;
		
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = 0;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = 0;
		result[9] = 0;
		result[11] = 0;
		
		result[12] = 0;
		result[13] = 0;
		result[14] = 0;
		
	}
	
	public static void createTRS(float[] result, float tx, float ty, float tz, float ax, float ay, float az, float sx, float sy, float sz)
	{
//		// Heading ( Yaw :: Y Axis ) ; First
//		float ch = Mathf.cos(ay);
//		float sh = Mathf.sin(ay);
//			    
//		// Attitude ( Pitch :: Z Axis ) ; Second
//		float ca = Mathf.cos(az);
//		float sa = Mathf.sin(az);
//		    
//		// Bank ( Roll :: X Axis ) ; Last
//		float cb = Mathf.cos(ax);
//		float sb = Mathf.sin(ax);
//
//		result[0] = ( ch*ca ) * sx;
//		result[1] = sa * sy;
//		result[2] = ( -sh*ca ) * sz;
//		result[3] = 0;
//
//		result[4] = ( sh*sb - ch*sa*cb ) * sx;
//		result[5] = ( ca*cb ) * sy;
//		result[6] = ( sh*sa*cb + ch*sb ) * sz;
//		result[7] = 0;
//
//		result[8] = ( ch*sa*sb + sh*cb ) * sx;
//		result[9] = ( -ca*sb ) * sy;
//		result[10] = ( -sh*sa*sb + ch*cb ) * sz;
//		result[11] = 0;
//		
//		result[12] = tx*sx;
//		result[13] = ty*sy;
//		result[14] = tz*sz;
//		result[15] = 1;
		
		// Heading ( Yaw :: Z Axis ) ; First
		float ch = Mathf.cos(az);
		float sh = Mathf.sin(az);

		// Attitude ( Pitch :: Y Axis ) ; Second
		float ca = Mathf.cos(ay);
		float sa = Mathf.sin(ay);

		// Bank ( Roll :: X Axis ) ; Last
		float cb = Mathf.cos(ax);
		float sb = Mathf.sin(ax);
		//
		//
		result[0] = (ch * ca) * sx;
		result[1] = (sh * ca) * sy;
		result[2] = -sa * sz;
		result[3] = 0;

		result[4] = ( (-sh * cb) + (sb * (sa*ch)) ) * sx;
		result[5] = ( (ch * cb) + (sb * (sa*sh)) ) * sy;
		result[6] = (sb * ca) * sz;
		result[7] = 0;

		result[8] = ( (-sb * -sh) + (cb * (sa*ch)) ) * sx;
		result[9] = ( (-sb * ch) + (cb * (sa*sh)) ) * sy;
		result[10] = (cb * ca) * sz;
		result[11] = 0;

		result[12] = tx * sx;
		result[13] = ty * sy;
		result[14] = tz * sz;
		result[15] = 1;
	}
	
	
	
	public static void createFromEulerAngles(float[] result, float ax, float ay, float az)
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
	    
	    result[0] = ch*ca;
	    result[1] = sa;
	    result[2] = -sh*ca;
	    result[3] = 0;
	    
	    result[4] = sh*sb - ch*sa*cb;
	    result[5] = ca*cb;
	    result[6] = sh*sa*cb + ch*sb;
	    result[7] = 0;

	    result[8] = ch*sa*sb + sh*cb;
	    result[9] = -ca*sb;
	    result[10] = -sh*sa*sb + ch*cb;
	    result[11] = 0;

	    result[12] = 0;
	    result[13] = 0;
	    result[14] = 0;
	    
	    result[15] = 1;
	}
	
	public static void setLookAt(float[] result, float eyeX, float eyeY, float eyeZ, float targetX, float targetY, float targetZ, float upX, float upY, float upZ)
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
		
		result[0] = lx;
		result[1] = ux;
		result[2] = fx;
		result[3] = 0;
		
		result[4] = ly;
		result[5] = uy;
		result[6] = fy;
		result[7] = 0;

		result[8] = lz;
		result[9] = uz;
		result[10] = fz;
		result[11] = 0;
		
		result[12] = -tx;
		result[13] = -ty;
		result[14] = -tz;
		result[15] = 1;
	}

	public static void setLookAt(float[] result, Vector3 eye, Vector3 target, Vector3 up)
	{
		setLookAt(result, eye.x, eye.y, eye.z, target.x, target.y, target.z, up.x, up.y, up.z);
	}
	
	public static void createOrtho(float[] result, float left, float right, float bottom, float top, float near, float far)
	{
		float w = right-left;
		float h = top-bottom;
		float Zf_Zn = far-near;
		
		result[0] = 2/w;
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = 0;
		result[5] = 2/h;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = 0;
		result[9] = 0;
		result[10] = (-2) / Zf_Zn;
		result[11] = 0;
		
		result[12] = -(right + left) / w;
		result[13] = -(top + bottom) / h;
		result[14] = -(far + near) / Zf_Zn;
		result[15] = 1;
	}
	
	public static void createFrustum(float[] result, float left, float right, float bottom, float top, float near, float far)
	{
		float _2n = (2.0f*near);
		float RsubL = right - left;
		float TsubB = top - bottom;
		float FsubN = far - near;
		
		float A = - ( (far + near) / FsubN );
		float B = - ( (2 * far * near) / FsubN );
		
		result[0] = _2n / RsubL;
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = 0;
		result[5] = _2n / TsubB;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = (right + left) / RsubL;
		result[9] = (top + bottom) / TsubB;
		result[10] = A;
		result[11] = -1;
		
		result[12] = 0;
		result[13] = 0;
		result[14] = B;
		result[15] = 0;
	}
	
	public static void setIdentity(float[] result)
	{
		result[0] = 1;
		result[5] = 1;
		result[10] = 1;
		result[15] = 1;
		
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		
		result[4] = 0;
		result[6] = 0;
		result[7] = 0;
		
		result[8] = 0;
		result[9] = 0;
		result[11] = 0;
		
		result[12] = 0;
		result[13] = 0;
		result[14] = 0;
		
	}

	public static void createNormalMatrix(float[] result, float[] modelView)
	{
		float m11 = modelView[0];
		float m12 = modelView[1];
		float m13 = modelView[2];
		
		float m21 = modelView[4];
		float m22 = modelView[5];
		float m23 = modelView[6];
		
		float m31 = modelView[8];
		float m32 = modelView[9];
		float m33 = modelView[10];
		
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
		result[0] = m11;
		result[1] = m21;
		result[2] = m31;
		
		result[3] = m12;
		result[4] = m22;
		result[5] = m32;
		
		result[6] = m13;
		result[7] = m23;
		result[8] = m33;

	}
}
