package plia.framework.scene;

import java.util.ArrayList;

import plia.framework.math.Matrix4;
import plia.framework.math.Vector3;
import plia.framework.scene.group.shading.Heightmap;
import plia.framework.scene.group.shading.NormalMap;
import plia.framework.scene.group.shading.Texture2D;

public class Terrain extends Group
{
	private Texture2D heightmap;
	private Texture2D normalmap;
	private Texture2D baseTexture;
	
	private int scale;
	private int height;
	
	private ArrayList<Collider> attached = new ArrayList<Collider>();

	public Terrain(Texture2D heightmap, int maxHeight, int scale)
	{
		this.heightmap = heightmap;
		this.height = maxHeight;
		this.scale = scale;
	}
	
	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		super.onUpdateHierarchy(parentHasChanged);
		
		for (Collider bounds : attached)
		{
			glueObject(bounds);
		}		
	}
	
	private void glueObject(Collider bounds)
	{
		if(bounds.calTerrainChanged)
		{
			
			Vector3 bPos = bounds.getWorldMatrix().getTranslation();
			
			// find uv by normalize object position with terrain scale
			Vector3 terrainPosition = getWorldMatrix().getTranslation();
	
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
			
			Vector3[] plane = new Vector3[4];
			plane[0] = new Vector3(ihS, ihT, 0); // b
			plane[1] = new Vector3(ihS+1, ihT, 0); // c
			plane[2] = new Vector3(ihS+1, ihT+1, 0); // d
			plane[3] = new Vector3(ihS, ihT+1, 0); // a
			
			float[] ph = new float[4];
	
			for (int i = 0; i < plane.length; i++)
			{
				int hxx = (int) Math.min(hw, plane[i].x);
				int hyy = (int) Math.min(hh, plane[i].y);
	
				plane[i].x = ((plane[i].x / (float)hw) * scale) + terrainPosition.x;
				plane[i].y = ((plane[i].y / (float)hh) * scale) + terrainPosition.y;
	//			log("xx : "+xx+", yy : "+yy);
				ph[i] = Heightmap.getHeightFromPixel(heightmap, hxx, hyy) * (float)height;
				
				int nxx = (int) Math.min(nw, normals[i].x);
				int nyy = (int) Math.min(nh, normals[i].y);
				
				normals[i] = NormalMap.getNormalFromPixel(normalmap, nxx, nyy);
			}
			
			Vector3 p = new Vector3(bPos.x, bPos.y, 0);
			
			boolean pabc = pointInTriangle(p, plane[3], plane[0], plane[1]);
			boolean pacd = pointInTriangle(p, plane[3], plane[1], plane[2]);
			
			for (int i = 0; i < plane.length; i++)
			{
				plane[i].z = ph[i];
			}
			
			float iH = -1;
			
			if(pabc)
			{
				iH = interpolateHeight(bPos , plane[3], plane[0], plane[1]);
				normalSurface = Vector3.scale(Vector3.add(Vector3.add(normals[3], normals[0]), normals[1]), 0.333333f).getNormalized();
			}
			else if(pacd)
			{
				iH = interpolateHeight(bPos, plane[1], plane[2], plane[3]);
				normalSurface = Vector3.scale(Vector3.add(Vector3.add(normals[1], normals[2]), normals[3]), 0.333333f).getNormalized();
			}
			
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
					float range = newZ - oldZ;
					
					Matrix4 parentWorld = bounds.parent.getWorldMatrix().clone();
					
					float currentZ = parentWorld.m43;
					parentWorld.m43 = currentZ + range;
					
					Vector3 oldUp = parentWorld.getUp();
					parentWorld.setUp(Vector3.lerp(oldUp, normalSurface, 0.25f));
					
					Matrix4 inv = Matrix4.multiply(bounds.parent.invParent, parentWorld);
					bounds.parent.localRotation = inv.toMatrix3();
					bounds.parent.localTranslation = inv.getTranslation();
				}
				else
				{
					Matrix4 world = bounds.getWorldMatrix();
					
					world.m43 = newZ;
					
					Vector3 oldUp = world.getUp();
					world.setUp(Vector3.lerp(oldUp, normalSurface, 0.25f));
					
					Matrix4 inv = Matrix4.multiply(bounds.parent.invParent, world);
					bounds.localRotation = inv.toMatrix3();
					bounds.localTranslation = inv.getTranslation();
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
	
	public void setTerrainScale(int scale)
	{
		this.scale = scale;
	}
	
	public void setTerrainMaxHeight(int height)
	{
		this.height = height;
	}
	
	public int getTerrainScale()
	{
		return scale;
	}
	
	public int getTerrainMaxHeight()
	{
		return height;
	}
	
	public Texture2D getHeightmap()
	{
		return heightmap;
	}
	
	public Texture2D getNormalmap()
	{
		return normalmap;
	}
	
	public Texture2D getBaseTexture()
	{
		return baseTexture;
	}
	
	public void setBaseTexture(Texture2D baseTexture)
	{
		this.baseTexture = baseTexture;
	}

	public boolean attachBounds(Collider bounds)
	{
		attached.add(bounds);
		
		return true;
	}
	
	public boolean detachBounds(Collider bounds)
	{
		attached.remove(bounds);
		
		return true;
	}
	
	public static void setNormalMapTo(Terrain terrain, Texture2D normalMap)
	{
		terrain.normalmap = normalMap;
	}
	
	private static int[] terrainBuffers = new int[2];
	public static void setTerrainBuffer(int[] terrainBuffers)
	{
		Terrain.terrainBuffers = terrainBuffers;
	}
	
	public static int getTerrainBuffer(int index)
	{
		return terrainBuffers[index];
	}
}
