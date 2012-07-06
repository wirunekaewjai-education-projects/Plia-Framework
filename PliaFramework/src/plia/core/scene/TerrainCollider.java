package plia.core.scene;

import android.util.Log;
import plia.core.debug.Debug;
import plia.core.scene.geometry.Plane;
import plia.core.scene.shading.Color3;
import plia.core.scene.shading.Color4;
import plia.core.scene.shading.Heightmap;
import plia.core.scene.shading.NormalMap;
import plia.core.scene.shading.Texture2D;
import plia.math.Matrix4;
import plia.math.Vector3;

public final class TerrainCollider extends Collider
{
	private Terrain terrain;

	protected TerrainCollider(Terrain terrain)
	{
		this.terrain = terrain;
	}
	
	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		// TODO Auto-generated method stub
		super.onUpdateHierarchy(parentHasChanged);
		
		for (Collider bounds : terrain.attached)
		{
			glueObject(bounds);
		}	
	}
	
	private void glueObject(Collider bounds)
	{
		if(bounds.calTerrainChanged)
		{
			float scale = terrain.getTerrainScale();
			float height = terrain.getTerrainMaxHeight();
			Texture2D normalmap = terrain.getNormalmap();
			Texture2D heightmap = terrain.getHeightmap();
			
			Vector3 bPos = bounds.getWorldMatrix().getTranslation();
			
			// find uv by normalize object position with terrain scale
			Vector3 terrainPosition = getWorldMatrix().getTranslation();
			
//			if(terrain instanceof MeshTerrain)
//			{
//				terrainPosition = terrainPosition.subtract(new Vector3(scale /2f, scale /2f, scale /2f));
//			}
	
			float npx = Math.min(scale, Math.max(0f, bPos.x - terrainPosition.x));
			float npy = Math.min(scale, Math.max(0f, bPos.y - terrainPosition.y));
			
			float u = npx / (float)scale;
			float v = npy / (float)scale;

			float nw = (normalmap.getWidth() - 1);
			float nh = (normalmap.getHeight() - 1);
			
			float hw = (heightmap.getWidth() - 1);
			float hh = (heightmap.getHeight() - 1);
	
			// scale 'uv' up to bitmap size
			float ns = (u * nw);
			float nt = (v * nh);
			
			int inS = (int) ns;
			int inT = (int) nt;
			
			float hs = (u * hw);
			float ht = (v * hh);
			
			int ihS = (int) hs;
			int ihT = (int) ht;
	
			Vector3 normalSurface = new Vector3();
			
			Vector3[] normals = new Vector3[4];
			normals[0] = new Vector3(inS, inT, 0); // b
			normals[1] = new Vector3(inS+1, inT, 0); // c
			normals[2] = new Vector3(inS+1, inT+1, 0); // d
			normals[3] = new Vector3(inS, inT+1, 0); // a

//			Log.e(s1+", "+t1, ihS+", "+ihT);
			int u00 = (int) Math.min(hw, Math.max(0, ihS-1));
			int u01 = (int) Math.min(hw, Math.max(0, ihS));
			int u02 = (int) Math.min(hw, Math.max(0, ihS+1));
			
			int v00 = (int) Math.min(hh, Math.max(0, ihT-1));
			int v01 = (int) Math.min(hh, Math.max(0, ihT));
			int v02 = (int) Math.min(hh, Math.max(0, ihT+1));
			
			float z0 = Heightmap.getHeightFromPixel(heightmap, u00, v00) * (float)height;
			float z1 = Heightmap.getHeightFromPixel(heightmap, u01, v00) * (float)height;
			float z2 = Heightmap.getHeightFromPixel(heightmap, u02, v00) * (float)height;
			
			float z3 = Heightmap.getHeightFromPixel(heightmap, u00, v01) * (float)height;
			float z4 = Heightmap.getHeightFromPixel(heightmap, u01, v01) * (float)height;
			float z5 = Heightmap.getHeightFromPixel(heightmap, u02, v01) * (float)height;
			
			float z6 = Heightmap.getHeightFromPixel(heightmap, u00, v02) * (float)height;
			float z7 = Heightmap.getHeightFromPixel(heightmap, u01, v02) * (float)height;
			float z8 = Heightmap.getHeightFromPixel(heightmap, u02, v02) * (float)height;
			
			float h0 = (z0 + z1 + z3 + z4) / 4f;
			float h1 = (z1 + z2 + z4 + z5) / 4f;
			float h2 = (z4 + z5 + z7 + z8) / 4f;
			float h3 = (z3 + z4 + z6 + z7) / 4f;
			
			float segment = Plane.getInstance().getSegment();
			float segSize = scale / segment;

			Vector3[] plane = new Vector3[4];
			plane[0] = new Vector3(ihS, ihT, 0); // b
			plane[1] = new Vector3(ihS+1, ihT, 0); // c
			plane[2] = new Vector3(ihS+1, ihT+1, 0); // d
			plane[3] = new Vector3(ihS, ihT+1, 0); // a

			float[] ph = new float[4];

//			float fhs = (int)(npx / segSize) * segSize;
//			float fht = (int)(npy / segSize) * segSize;
//			
//			plane[0] = new Vector3(fhs, fht, 0); // b
//			plane[1] = new Vector3(fhs+segSize, fht, 0); // c
//			plane[2] = new Vector3(fhs+segSize, fht+segSize, 0); // d
//			plane[3] = new Vector3(fhs, fht+segSize, 0); // a

			for (int i = 0; i < plane.length; i++)
			{
				float nx = (plane[i].x / (float)hw);
				float ny = (plane[i].y / (float)hh);
//				
				plane[i].x = (nx * (float)scale) + terrainPosition.x;
				plane[i].y = (ny * (float)scale) + terrainPosition.y;

				int nxx = (int) Math.min(nw, normals[i].x);
				int nyy = (int) Math.min(nh, normals[i].y);
				
				normals[i] = NormalMap.getNormalFromPixel(normalmap, nxx, nyy);
			}
			
			Vector3 p = new Vector3(bPos.x, bPos.y, 0);
			
			boolean pabc = pointInTriangle(p, plane[3], plane[0], plane[1]);
			boolean pacd = pointInTriangle(p, plane[3], plane[1], plane[2]);

			plane[0].z = h0;
			plane[1].z = h1;
			plane[2].z = h2;
			plane[3].z = h3;
			
//			Vector3 vh0 = Vector3.add(plane[0], new Vector3(0, 0, 0.1f));
//			Vector3 vh1 = Vector3.add(plane[1], new Vector3(0, 0, 0.1f));
//			Vector3 vh2 = Vector3.add(plane[2], new Vector3(0, 0, 0.1f));
//			Vector3 vh3 = Vector3.add(plane[3], new Vector3(0, 0, 0.1f));
//			
//			Debug.drawLine(vh0, vh1, new Color3(1, 1, 0));
//			Debug.drawLine(vh1, vh2, new Color3(1, 1, 0));
//			Debug.drawLine(vh0, vh2, new Color3(1, 1, 0));
//			
//			Debug.drawLine(vh0, vh3, new Color3(1, 1, 0));
//			Debug.drawLine(vh3, vh2, new Color3(1, 1, 0));
			
//			for (int i = 0; i < plane.length; i++)
//			{
//				plane[i].z = ph[i];
//			}
			
			float iH = -1;

			Vector3 v0 = Vector3.add(plane[0], new Vector3(0, 0, 50));
			Vector3 v1 = Vector3.add(plane[1], new Vector3(0, 0, 50));
			Vector3 v2 = Vector3.add(plane[2], new Vector3(0, 0, 50));
			Vector3 v3 = Vector3.add(plane[3], new Vector3(0, 0, 50));
			
			Debug.drawLine(plane[0], v0, new Color3(1, 0, 0));
			Debug.drawLine(plane[1], v1, new Color3(0, 1, 0));
			Debug.drawLine(plane[2], v2, new Color3(0, 0, 1));
			Debug.drawLine(plane[3], v3, new Color3(1, 1, 1));

			for (int i = 0; i < 4; i++)
			{
//				Log.e("Plane["+i+"]", plane[i].toString());
			}
			
			if(pabc)
			{
				Vector3 ss1 = Vector3.add(plane[0], new Vector3(0, 0, 0.1f));
				Vector3 ss2 = Vector3.add(plane[1], new Vector3(0, 0, 0.1f));
				Vector3 ss3 = Vector3.add(plane[3], new Vector3(0, 0, 0.1f));
				
				Color3 color = new Color3(0.5f, 1, 0.5f);
				Debug.drawLine(ss1, ss2, color);
				Debug.drawLine(ss2, ss3, color);
				Debug.drawLine(ss3, ss1, color);
				
				iH = interpolateHeight(bPos , plane[3], plane[0], plane[1]);
				normalSurface = Vector3.scale(Vector3.add(Vector3.add(normals[3], normals[0]), normals[1]), 0.333333f).getNormalized();
			}
			else if(pacd)
			{
				Vector3 ss1 = Vector3.add(plane[1], new Vector3(0, 0, 0.1f));
				Vector3 ss2 = Vector3.add(plane[2], new Vector3(0, 0, 0.1f));
				Vector3 ss3 = Vector3.add(plane[3], new Vector3(0, 0, 0.1f));
				
				Color3 color = new Color3(0.5f, 1, 0.5f);
				Debug.drawLine(ss1, ss2, color);
				Debug.drawLine(ss2, ss3, color);
				Debug.drawLine(ss3, ss1, color);
				
				iH = interpolateHeight(bPos, plane[1], plane[2], plane[3]);
				normalSurface = Vector3.scale(Vector3.add(Vector3.add(normals[1], normals[2]), normals[3]), 0.333333f).getNormalized();
			}
			
//			Log.e("Max Height", iH+"");
			
			if(iH > -1)
			{
				float bottomlength = 0;
				
				if(bounds instanceof SphereCollider)
				{
					bottomlength = ((SphereCollider) bounds).getRadius();
				}
				
				float oldZ = bounds.getWorldMatrix().m43;
				float newZ = iH + bottomlength;

				if(bounds.parent != null)
				{
					Group par = bounds.parent;
					
					float range = newZ - oldZ;
					
					Matrix4 parentWorld = par.getWorldMatrix();
					
					float currentZ = parentWorld.m43;
					parentWorld.m43 = currentZ + range;
					
//					parentWorld.m41 += (range * parentWorld.m31);
//					parentWorld.m42 += (range * parentWorld.m32);
//					parentWorld.m43 += (range * parentWorld.m33);
					
					par.hasChanged = true;
					
//					Vector3 end = Vector3.add(bPos, Vector3.scale(normalSurface, 100));
//					Debug.drawLine(bPos, end, new Color3(1, 1, 1));
//					
//					par.setUp(Vector3.lerp(par.getUp(), normalSurface, 0.25f));
				}
				else
				{
					Matrix4 world = bounds.getWorldMatrix();
					
					world.m43 = newZ;
					
					bounds.hasChanged = true;
					
//					Vector3 oldUp = world.getUp();
//					world.setUp(Vector3.lerp(oldUp, normalSurface, 0.25f));
					
//					Matrix4 inv = Matrix4.multiply(bounds.parent.invParent, world);
//					bounds.localRotation = inv.toMatrix3();
//					bounds.localTranslation = inv.getTranslation();
				}
				
				bounds.calTerrainChanged = false;

			}

		}
	}
	
	private boolean pointInTriangle(Vector3 p, Vector3 a, Vector3 b, Vector3 c)
	{
		boolean pabc = sameSide(p, a, b, c);
		boolean pbac = sameSide(p, b, a, c);
		boolean pcab = sameSide(p, c, a, b);
		
		return (pabc && pbac && pcab);
	}
	
	private boolean sameSide(Vector3 p1, Vector3 p2, Vector3 a, Vector3 b)
	{
		Vector3 v1 = Vector3.subtract(b, a);
		Vector3 v2 = Vector3.subtract(p1, a);
		Vector3	v3 = Vector3.subtract(p2, a);
		
		Vector3 r1 = Vector3.cross(v1, v2);
		Vector3 r2 = Vector3.cross(v1, v3);
		
		if(Vector3.dot(r1, r2) >= 0)
		{
			return true;
		}
		
		return false;
	}
	
	private float interpolateHeight(Vector3 p, Vector3 a, Vector3 b, Vector3 c)
	{
		Vector3 horizontal = Vector3.lerp(b, c, calcT(b.x, c.x, p.x));
		Vector3 bias = Vector3.lerp(a, c, calcT(a.x, c.x, p.x));	

		float h = horizontal.z+( (bias.z-horizontal.z) * calcT(horizontal.y, bias.y, p.y));
		
		return h;
	}
	
	private float calcT(float start, float end, float p)
	{
		return (p-start)/(end-start+0.0000001f);
	}
}
