package plia.core.scene;

//import plia.framework.debug.Debug;
import android.util.Log;
import plia.core.debug.Debug;
import plia.core.scene.shading.Color3;
import plia.math.Matrix4;
import plia.math.Vector3;
import plia.math.Vector4;

public class Collider extends Group
{
	boolean calTerrainChanged = true;
	private boolean isFirst = true;
	
	protected Collider()
	{
		calTerrainChanged = true;
	}
	
	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		if(isFirst)
		{
			if(!isRoot())
			{
				parent.hasChanged = true;
			}
			isFirst = false;
		}
		super.onUpdateHierarchy(parentHasChanged);
	}

	@Override
	public Collider instantiate()
	{
		Collider bounds = new Collider();
		return bounds;
	}
	
	public static final boolean intersect(Collider a, Collider b)
	{
		if(a instanceof PlaneCollider)
		{
			if(b instanceof PlaneCollider)
			{
				return intersect((PlaneCollider)a, (PlaneCollider)b);
			}
			else if(b instanceof SphereCollider)
			{
				return intersect((PlaneCollider)a, (SphereCollider)b);
			}
		}
		else if(a instanceof SphereCollider)
		{
			if(b instanceof PlaneCollider)
			{
				return intersect((SphereCollider)a, (PlaneCollider)b);
			}
			else if(b instanceof SphereCollider)
			{
				return intersect((SphereCollider)a, (SphereCollider)b);
			}
		}
		
		return false;
	}

	
	private static final boolean intersect(SphereCollider a, PlaneCollider b)
	{
		return intersect(b, a);
	}
	
	private static final boolean intersect(PlaneCollider a, SphereCollider b)
	{
		Vector4 ap0 = a.getP0();
		Vector4 ap1 = a.getP1();
		Vector4 ap2 = a.getP2();
		Vector4 ap3 = a.getP3();
		
		p0.set(ap0.x, ap0.y, ap0.z);
		p1.set(ap1.x, ap1.y, ap1.z);
		p2.set(ap2.x, ap2.y, ap2.z);
		p3.set(ap3.x, ap3.y, ap3.z);
		
		Matrix4 aw = a.getWorldMatrix();
		va.set(aw.m41, aw.m42, aw.m43);
		
		return intersect(b, va, p0, p1, p2, p3);
	}
	
	static final boolean intersect(SphereCollider b, Vector3 center, Vector3 p0, Vector3 p1, Vector3 p2, Vector3 p3)
	{
		p0 = Vector3.subtract(p0, center);
		p1 = Vector3.subtract(p1, center);
		p2 = Vector3.subtract(p2, center);
		p3 = Vector3.subtract(p3, center);
		
		Matrix4 bw = b.getWorldMatrix();
		vb.set(bw.m41, bw.m42, bw.m43);
		
		// Sphere Center
		float x0 = bw.m41 - center.x;
		float y0 = bw.m42 - center.y;
		float z0 = bw.m43 - center.z;
		
		float r = b.getRadius();
		
		// Plane Equation
		float A = (p1.y*p2.z)+(p0.y*p1.z)+(p0.z*p2.y) - (p1.y*p0.z)-(p2.y*p1.z)-(p2.z*p0.y);
		float B = (p0.x*p2.z)+(p1.z*p2.x)+(p0.z*p1.x) - (p2.x*p0.z)-(p1.z*p0.x)-(p2.z*p1.x);
		float C = (p0.x*p1.y)+(p0.y*p2.x)+(p1.x*p2.y) - (p2.x*p1.y)-(p2.y*p0.x)-(p1.x*p0.y);
		float D = (p0.x*p1.y*p2.z)+(p0.y*p1.z*p2.x)+(p0.z*p1.x*p2.y) - (p2.x*p1.y*p0.z)-(p2.y*p1.z*p0.x)-(p2.z*p1.x*p0.y);
		
		float AABBCC = ((A*A)+(B*B)+(C*C));
		float AxBxCxD = ((A*x0)+(B*y0)+(C*z0)+(D));
		
		float AAxBXCxD = (A * AxBxCxD) / AABBCC;
		float BAxBXCxD = (B * AxBxCxD) / AABBCC;
		float CAxBXCxD = (C * AxBxCxD) / AABBCC;
		
		//Intersected circle center
		float xc = x0 - AAxBXCxD;
		float yc = y0 - BAxBXCxD;
		float zc = z0 - CAxBXCxD;
		
		float d = (float) (Math.abs(AxBxCxD) / Math.sqrt(AABBCC));
		
		// Radius of Circle
		float R = (float) Math.sqrt((r*r) - (d*d));
		
//		Log.e(d+"", r+"");
		
		if(d <= r)
		{
			// Circle Center inside Plane
			circleCenter.set(xc, yc, zc);
			boolean ccip = pointInPlane(circleCenter, p0, p1, p2, p3);

			if(ccip)
			{
				Vector3 start = Vector3.add(center, circleCenter);
				Vector3 e0 = Vector3.add(p0, center);
				Vector3 e1 = Vector3.add(p1, center);
				Vector3 e2 = Vector3.add(p2, center);
				Vector3 e3 = Vector3.add(p3, center);

				Color3 color = new Color3(1, 0, 0);
				Debug.drawLine(start, e0, color);
				Debug.drawLine(start, e1, color);
				Debug.drawLine(start, e2, color);
				Debug.drawLine(start, e3, color);
				return true;
			}
			
			// Circle Center outside plane
			Vector3.subtract(p[0], p0, circleCenter);
			Vector3.subtract(p[1], p1, circleCenter);
			Vector3.subtract(p[2], p2, circleCenter);
			Vector3.subtract(p[3], p3, circleCenter);
			Vector3.subtract(pc, new Vector3(), circleCenter); //va = plane origin
			
			Vector3.normalize(pc, pc);
			
			int start = 0;
			int end = 0;
			
			float[] dot = new float[4];
			
			for (int i = 0; i < 4; i++)
			{
				p[i] = p[i].getNormalized();
				dot[i] = Vector3.dot(p[i], pc);			
			}
			
			// Find 2 min dot
			for (int i = 0; i < dot.length; i++)
			{
				if(dot[i] <= dot[start])
				{
					end = start;
					start = i;
				}
			}

			for (float t = 0; t < 1.001f; t+=0.1f)
			{
				Vector3.lerp(lerp, p[start], p[end], t);
				Vector3.normalize(lerp, lerp);
				Vector3.scale(dir, lerp, R);
				Vector3.add(point, circleCenter, dir);
				
				if(pointInPlane(point, p0, p1, p2, p3))
				{
					Vector3 s1 = Vector3.add(center, point);
					Vector3 e0 = Vector3.add(p0, center);
					Vector3 e1 = Vector3.add(p1, center);
					Vector3 e2 = Vector3.add(p2, center);
					Vector3 e3 = Vector3.add(p3, center);
					
					Color3 color = new Color3(1, 1, 0);
					Debug.drawLine(s1, e0, color);
					Debug.drawLine(s1, e1, color);
					Debug.drawLine(s1, e2, color);
					Debug.drawLine(s1, e3, color);
					return true;
				}
			}
		}
		
		return false;
		
//		Matrix4 bw = b.getWorldMatrix();
////		vb.set(bw.m41, bw.m42, bw.m43);
//		
//		// Sphere Center
//		float x0 = bw.m41;
//		float y0 = bw.m42;
//		float z0 = bw.m43;
//		
//		float r = b.getRadius();
//		
//		// Plane Equation
//		float A = (p1.y*p2.z)+(p0.y*p1.z)+(p0.z*p2.y) - (p1.y*p0.z)-(p2.y*p1.z)-(p2.z*p0.y);
//		float B = (p0.x*p2.z)+(p1.z*p2.x)+(p0.z*p1.x) - (p2.x*p0.z)-(p1.z*p0.x)-(p2.z*p1.x);
//		float C = (p0.x*p1.y)+(p0.y*p2.x)+(p1.x*p2.y) - (p2.x*p1.y)-(p2.y*p0.x)-(p1.x*p0.y);
//		float D = (p0.x*p1.y*p2.z)+(p0.y*p1.z*p2.x)+(p0.z*p1.x*p2.y) - (p2.x*p1.y*p0.z)-(p2.y*p1.z*p0.x)-(p2.z*p1.x*p0.y);
//		
//		float AABBCC = ((A*A)+(B*B)+(C*C));
//		float AxBxCxD = ((A*x0)+(B*y0)+(C*z0)+(D));
//		
//		float AAxBXCxD = (A * AxBxCxD) / AABBCC;
//		float BAxBXCxD = (B * AxBxCxD) / AABBCC;
//		float CAxBXCxD = (C * AxBxCxD) / AABBCC;
//		
//		//Intersected circle center
//		float xc = AAxBXCxD - x0;
//		float yc = BAxBXCxD - y0;
//		float zc = CAxBXCxD - z0;
//		
//		float d = (float) (Math.abs(AxBxCxD) / Math.sqrt(AABBCC));
//		
//		// Radius of Circle
//		float R = (float) Math.sqrt((r*r) - (d*d));
//		
//		Log.e(d+"", r+"");
//		
//		if(d <= r)
//		{
//			// Circle Center inside Plane
//			circleCenter.set(xc, yc, zc);
//			boolean ccip = pointInPlane(circleCenter, p0, p1, p2, p3);
//
//			if(ccip)
//			{
//				Color3 color = new Color3(1, 0, 0);
//				Debug.drawLine(circleCenter, p0, color);
//				Debug.drawLine(circleCenter, p1, color);
//				Debug.drawLine(circleCenter, p2, color);
//				Debug.drawLine(circleCenter, p3, color);
//				return true;
//			}
//			
//			// Circle Center outside plane
//			Vector3.subtract(p[0], p0, circleCenter);
//			Vector3.subtract(p[1], p1, circleCenter);
//			Vector3.subtract(p[2], p2, circleCenter);
//			Vector3.subtract(p[3], p3, circleCenter);
//			Vector3.subtract(pc, center, circleCenter); //va = plane origin
//			
//			Vector3.normalize(pc, pc);
//			
//			int start = 0;
//			int end = 0;
//			
//			float[] dot = new float[4];
//			
//			for (int i = 0; i < 4; i++)
//			{
//				p[i] = p[i].getNormalized();
//				dot[i] = Vector3.dot(p[i], pc);			
//			}
//			
//			// Find 2 min dot
//			for (int i = 0; i < dot.length; i++)
//			{
//				if(dot[i] <= dot[start])
//				{
//					end = start;
//					start = i;
//				}
//			}
//
//			for (float t = 0; t < 1.001f; t+=0.1f)
//			{
//				Vector3.lerp(lerp, p[start], p[end], t);
//				Vector3.normalize(lerp, lerp);
//				Vector3.scale(dir, lerp, R);
//				Vector3.add(point, circleCenter, dir);
//				
//				if(pointInPlane(point, p0, p1, p2, p3))
//				{
//					Color3 color = new Color3(1, 1, 0);
//					Debug.drawLine(point, p0, color);
//					Debug.drawLine(point, p1, color);
//					Debug.drawLine(point, p2, color);
//					Debug.drawLine(point, p3, color);
//					return true;
//				}
//			}
//		}
//		
//		return false;
	}
	
	private static final boolean intersect(PlaneCollider a, PlaneCollider b)
	{
		Vector4 ap0 = a.getP0();
		Vector4 ap1 = a.getP1();
		Vector4 ap2 = a.getP2();
		Vector4 ap3 = a.getP3();
		
		p0.set(ap0.x, ap0.y, ap0.z);
		p1.set(ap1.x, ap1.y, ap1.z);
		p2.set(ap2.x, ap2.y, ap2.z);
		p3.set(ap3.x, ap3.y, ap3.z);

		///////////////////////////
		return false;
	}

	private static final boolean intersect(SphereCollider a, SphereCollider b)
	{
		Matrix4 aw = a.getWorldMatrix();
		Matrix4 bw = b.getWorldMatrix();
		
		va.set(aw.m41, aw.m42, aw.m43);
		vb.set(bw.m41, bw.m42, bw.m43);

		float ar = Math.abs(a.getRadius());
		float br = Math.abs(b.getRadius());

		float radius =  ar + br;
		float distance = Vector3.distance(va, vb);

		return distance <= radius;
	}
	
	private static Vector3 circleCenter = new Vector3(), pc = new Vector3(), dir = new Vector3(), lerp = new Vector3(), point = new Vector3();
	private static final Vector3[] p = { new Vector3(), new Vector3(), new Vector3(), new Vector3() };
	private static final Vector3 va = new Vector3(), vb = new Vector3();
	private static final Vector3 p0 = new Vector3(), p1 = new Vector3(), p2 = new Vector3(), p3 = new Vector3();
	private static final Vector3 p5 = new Vector3(), p6 = new Vector3(), p7 = new Vector3(), p8 = new Vector3();

	static final boolean pointInPlane(Vector3 point, Vector3 p0, Vector3 p1, Vector3 p2, Vector3 p3)
	{
		Vector3.subtract(p5, p0, point);
		Vector3.subtract(p6, p1, point);
		Vector3.subtract(p7, p2, point);
		Vector3.subtract(p8, p3, point);
		
		Vector3.normalize(p5, p5);
		Vector3.normalize(p6, p6);
		Vector3.normalize(p7, p7);
		Vector3.normalize(p8, p8);
		
		float d1 = Vector3.dot(p5, p6);
		float d2 = Vector3.dot(p6, p7);
		float d3 = Vector3.dot(p7, p8);
		float d4 = Vector3.dot(p8, p5);

		float sum = (float) (Math.acos(d1)+Math.acos(d2)+Math.acos(d3)+Math.acos(d4));
		return sum >= 6.283185307f;
	}
}
