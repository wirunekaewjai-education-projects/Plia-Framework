package library.graphics;

public class Quaternion
{	
	public float w, x, y, z;
	
	public Quaternion()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
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
	
	
	public final Quaternion getConjugate()
	{
		return new Quaternion(-x, -y, -z, w);
	}
	
	public final Quaternion getNormalise()
	{
		float norm = getMagnitude();
		Quaternion qNorm = new Quaternion(x/norm, y/norm, z/norm, w/norm);
		return qNorm;
	}
	
	public final Quaternion getInverse()
	{
		Quaternion conjugate = getConjugate();
		float length = (w*w)+(x*x)+(y*y)+(z*z);
	
		return new Quaternion(conjugate.x/length, conjugate.y/length, conjugate.z/length, conjugate.w/length);
	}
	
	public final float getMagnitude()
	{
		return (float) Math.sqrt((w*w)+(x*x)+(y*y)+(z*z));
	}
	
	public final float[] toMatrix()
	{
		float[] m = new float[16];

		m[0] = 1.0f-(2*y*y)-(2*z*z);
		m[1] = (2*x*y)+(2*z*w);
		m[2] = (2*x*z)-(2*y*w);
		m[3] = 0;
		
		m[4] = (2*x*y)-(2*z*w);
		m[5] = 1.0f-(2*x*x)-(2*z*z);
		m[6] = (2*y*z)+(2*x*w);
		m[7] = 0;

		m[8] = (2*x*z)+(2*y*w);
		m[9] = (2*y*z)-(2*x*w);
		m[10] = 1.0f-(2*x*x)-(2*y*y);
		m[11] = 0;
		
		m[12] = 0;
		m[13] = 0;
		m[14] = 0;
		m[15] = 1;
		
		return m;
	}
	
	@Override
	public String toString()
	{
		return "X : "+x+", Y : "+y+", Z : "+z+", W : "+w;
	}
	
	///////////////////
	public void rotate(float degree, float x, float y, float z)
	{
		Quaternion q = this;
		Quaternion p1 = Quaternion.createFromAxisAngle(new Vector3(x, y, z), degree);
		Quaternion conj = getConjugate();
		
		Quaternion p2 = multiply(multiply(q, p1), conj);
		
		this.w = p2.w;
		this.x = p2.x;
		this.y = p2.y;
		this.z = p2.z;
	}
	
	//////////////////
	
	public static Quaternion add(Quaternion a, Quaternion b)
	{
		return new Quaternion(new Vector3(a.x+b.x, a.y+b.y, a.z+b.z), a.w+a.w);
	}
	
	public static Quaternion subtract(Quaternion a, Quaternion b)
	{
		return new Quaternion(new Vector3(a.x-b.x, a.y-b.y, a.z-b.z), a.w-a.w);
	}
	
	public static Quaternion multiply(Quaternion a, Quaternion b)
	{
		Vector3 va = new Vector3(a.x, a.y, a.z);
		Vector3 vb = new Vector3(b.x, b.y, b.z);
		
		Vector3 s1 = Vector3.multiply(vb , a.w);
		Vector3 s2 = Vector3.multiply(va, b.w);
		Vector3 s3 = Vector3.cross(va, vb);
		
		Vector3 xyz = Vector3.add(s1, Vector3.add(s2, s3));
		
		float w = (a.w*b.w)-Vector3.dot(va, vb);
		
		Quaternion q = new Quaternion(xyz, w);
		
		return q;
	}
	
	public static Quaternion normalise(Quaternion q)
	{
		return q.getNormalise();
	}
	
	public static Quaternion conjugate(Quaternion q)
	{
		return q.getConjugate();
	}
	
	public static Quaternion inverse(Quaternion q)
	{
		return q.getInverse();
	}
	
	public static Quaternion convertMatrixToQuaternion(float[] m)
	{
		float x = 0, y = 0, z = 0, w = 0;
		
		if(m[0]+m[5]+m[10] > 0)
		{
			float S = (float) (Math.sqrt(m[0]+m[5]+m[10]+1.0)*2.0f);
			w = 0.25f*S;
			x = (m[6]-m[9])/S;
			y = (m[8]-m[2])/S;
			z = (m[1]-m[4])/S;
		}
		else if(m[0] > m[5] && m[0] > m[10])
		{
			float S = (float) (Math.sqrt(1.0+m[0]-m[5]-m[10])*2.0f);
			w = (m[6]-m[9])/S;
			x = 0.25f*S;
			y = (m[4]+m[1])/S;
			z = (m[8]+m[2])/S;
		}
		else if(m[5] > m[10])
		{
			float S = (float) (Math.sqrt(1.0+m[5]-m[0]-m[10])*2.0f);
			w = (m[8]-m[2])/S;
			x = (m[4]+m[1])/S;
			y = 0.25f*S;
			z = (m[9]+m[6])/S;
		}
		else
		{
			float S = (float) (Math.sqrt(1.0+m[10]-m[0]-m[5])*2.0f);
			w = (m[1]-m[4])/S;
			x = (m[8]+m[2])/S;
			y = (m[9]+m[6])/S;
			z = 0.25f*S;
		}
		
		return new Quaternion(x, y, z, w);
	}
	
	public static Quaternion createFromYawPitchRoll(float yaw, float pitch, float roll)
	{
		float Roll, Pitch, Yaw;
		Roll = (float) Math.toRadians(roll);
		Pitch = (float) Math.toRadians(pitch);
		Yaw = (float) Math.toRadians(yaw);
		
		Quaternion a = new Quaternion();
//	    float num9 = Roll * 0.5f;
//	    float num6 = (float) Math.sin((double) num9);
//	    float num5 = (float) Math.cos((double) num9);
//	    float num8 = Pitch * 0.5f;
//	    float num4 = (float) Math.sin((double) num8);
//	    float num3 = (float) Math.cos((double) num8);
//	    float num7 = Yaw * 0.5f;
//	    float num2 = (float) Math.sin((double) num7);
//	    float num = (float) Math.cos((double) num7);
//	    a.x = ((num * num4) * num5) + ((num2 * num3) * num6);
//	    a.y = ((num2 * num3) * num5) - ((num * num4) * num6);
//	    a.z = ((num * num3) * num6) - ((num2 * num4) * num5);
//	    a.w = ((num * num3) * num5) + ((num2 * num4) * num6);
	    
//	    double c1 = Math.cos(Yaw);
//	    double s1 = Math.sin(Yaw);
//	    double c2 = Math.cos(Pitch);
//	    double s2 = Math.sin(Pitch);
//	    double c3 = Math.cos(Roll);
//	    double s3 = Math.sin(Roll);
//	    
//	    a.w = (float) (Math.sqrt(1.0 + c1 * c2 + c1*c3 - s1 * s2 * s3 + c2*c3) / 2.0);
//	    double w4 = (4.0 * a.w);
//	    a.x = (float) ((c2 * s3 + c1 * s3 + s1 * s2 * c3) / w4) ;
//	    a.y = (float) ((s1 * c2 + s1 * c3 + c1 * s2 * s3) / w4) ;
//	    a.z = (float) ((-s1 * s3 + c1 * s2 * c3 +s2) / w4) ;
		
		double c1 = Math.cos(Yaw/2);
	    double s1 = Math.sin(Yaw/2);
	    double c2 = Math.cos(Pitch/2);
	    double s2 = Math.sin(Pitch/2);
	    double c3 = Math.cos(Roll/2);
	    double s3 = Math.sin(Roll/2);
	    double c1c2 = c1*c2;
	    double s1s2 = s1*s2;
	    a.w =(float) (c1c2*c3 - s1s2*s3);
	  	a.x =(float) (c1c2*s3 + s1s2*c3);
		a.y =(float) (s1*c2*c3 + c1*s2*s3);
		a.z =(float) (c1*s2*c3 - s1*c2*s3);
	    
	    return a;
	}
	
	public static Quaternion createFromAxisAngle(Vector3 axis, float degree)
	{
		Quaternion q = new Quaternion();
		float radian = (float) (Math.toRadians(degree));
		q.w = (float) Math.cos(radian/2.0f);
		q.x = (float) (axis.x*Math.sin(radian/2.0f));
		q.y = (float) (axis.y*Math.sin(radian/2.0f));
		q.z = (float) (axis.z*Math.sin(radian/2.0f));

		return q;
	}
	
	
}
