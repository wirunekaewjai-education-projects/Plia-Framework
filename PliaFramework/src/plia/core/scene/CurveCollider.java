package plia.core.scene;

import java.util.ArrayList;

import android.util.Log;

import plia.core.debug.Debug;
import plia.core.scene.shading.Color3;
import plia.math.Matrix4;
import plia.math.Vector2;
import plia.math.Vector3;
import plia.math.Vector4;

public class CurveCollider extends Collider
{
	private ArrayList<SphereCollider> colliders = new ArrayList<SphereCollider>();
	
	protected ArrayList<Vector2> corners = new ArrayList<Vector2>();
	protected float z0, z1;
	
	protected CurveCollider(float step, float height, boolean extrudeHeightFromCenter, Vector2...p)
	{
		if(extrudeHeightFromCenter)
		{
			float hh = height/2f;
			z0 = -hh;
			z1 = hh;
		}
		else
		{
			z0 = 0;
			z1 = height;
		}
	}
	
	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		super.onUpdateHierarchy(parentHasChanged);
		
		for (SphereCollider collider : colliders)
		{
			collision(collider);
		}
	}
	
	private static int[] indx = new int[3];
	
	private void collision(SphereCollider b)
	{
		indx[0] = getClosetPlaneIndex(b);
		indx[1] = indx[0]-1;
		indx[2] = indx[0]+1;
		
		if(indx[1] < 0)
		{
			indx[1] = getPlaneCount()-1;
		}
		
		if(indx[2] == getPlaneCount())
		{
			indx[2] = 0;
		}
		
		Log.e("Indx", indx[0]+", "+indx[1]+", "+indx[2]);
		
		Color3 color = new Color3(1, 0, 0);
		
		for (int i = 0; i < indx.length; i++)
		{
			Vector3[] plane = getCorner(indx[i]);
			
			Vector3 center = Vector3.scale(Vector3.add(Vector3.add(plane[0], plane[1]), Vector3.add(plane[2], plane[3])), 0.25f);
			
			Debug.drawLine(plane[0], plane[1], color);
			Debug.drawLine(plane[1], plane[2], color);
			Debug.drawLine(plane[2], plane[3], color);
			Debug.drawLine(plane[3], plane[0], color);
			
			boolean isOverlap = Collider.intersect(b, center, plane[0], plane[3], plane[2], plane[1]);
			
			if(isOverlap)
			{
				Log.e("Is Overlap", b.getName());
				
				Vector3 v1 = Vector3.subtract(plane[2], plane[0]);
				Vector3 v2 = Vector3.subtract(plane[3], plane[0]);
				
				Vector3 n = Vector3.cross(v1, v2);
				Vector3 forward = b.getForward();
				
				Vector3 reflect = Vector3.reflect(forward, n);
				
				b.setForward(reflect);
			}
		}
	}
	
	public float getZ0()
	{
		return z0 + getPosition().z;
	}
	
	public float getZ1()
	{
		return z1 + getPosition().z;
	}
	
	public int getPlaneCount()
	{
		return corners.size() - 1;
	}
	
	public Vector3[] getCorner(int index)
	{
		Vector2 a = corners.get(index);
		Vector2 b = corners.get(index+1);

		Matrix4 world = getWorldMatrix();
		
		float z0 = getZ0();
		float z1 = getZ1();
		
		Vector4 P0 = Matrix4.multiply(world, new Vector4(a.x, a.y, z0, 1));
		Vector4 P1 = Matrix4.multiply(world, new Vector4(b.x, b.y, z0, 1));
		Vector4 P2 = Matrix4.multiply(world, new Vector4(b.x, b.y, z1, 1));
		Vector4 P3 = Matrix4.multiply(world, new Vector4(a.x, a.y, z1, 1));
		
		Vector3[] corner = { new Vector3(P0), new Vector3(P1), new Vector3(P2), new Vector3(P3) };
		return corner;
	}
	
	public int getClosetPlaneIndex(Group obj)
	{
		Vector3 pos3 = obj.getPosition();
		Vector2 pos = new Vector2(pos3);
		
		int indx = 0;
		float distMin = -1;
		
		for (int i = 0; i < corners.size(); i++)
		{
			float dist = Vector2.distance(pos, corners.get(i));
			if(distMin == -1 || dist < distMin)
			{
				distMin = dist;
				indx = i;
			}
		}
		
		return Math.min(getPlaneCount(), Math.max(0, indx-1));
	}
	
	public void addCollider(SphereCollider collider)
	{
		colliders.add(collider);
	}
	
	public void removeCollider(SphereCollider collider)
	{
		colliders.remove(collider);
	}
	
	
}
