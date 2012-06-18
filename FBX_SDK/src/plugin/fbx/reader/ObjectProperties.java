package plugin.fbx.reader;

import java.util.ArrayList;

import plugin.fbx.data.Geometry;
import android.util.Log;


public class ObjectProperties implements FbxReadState
{
	public static final ObjectProperties instance = new ObjectProperties();

	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		String geo_id = "";
		while(!(line = fbx.raw.dequeue()).contains(FbxPlugin.TAGS[4]))
		{
			if(line.contains("Geometry: "))
			{
				String geometry_id = line.replaceFirst("\\W*Geometry: ", "").split(",")[0];
				Geometry geometry = new Geometry(geometry_id);
				fbx.data.geometries.put(geometry_id, geometry);

				geo_id = geometry_id;
			}
			else if(line.contains("Vertices: *"))
			{
				fbx.data.geometries.get(geo_id).vertices = readFloatAttribute(fbx);
			}
			else if(line.contains("PolygonVertexIndex: *"))
			{
				fbx.data.geometries.get(geo_id).verticesIndices = readIntAttribute(fbx);
			}
			else if(line.contains("Normals: *"))
			{
				fbx.data.geometries.get(geo_id).normals = readFloatAttribute(fbx);
			}
			else if(line.contains("UV: *"))
			{
				fbx.data.geometries.get(geo_id).uv = readFloatAttribute(fbx);
			}
			else if(line.contains("UVIndex: *"))
			{
				fbx.data.geometries.get(geo_id).uvIndices = readIntAttribute(fbx);
			}
		}
		
		for (Geometry geometry : fbx.data.geometries.values())
		{
			float[] vertices1 = geometry.vertices;
			float[] vertices2 = null;
			float[] normals1 = geometry.normals;
			float[] normals2 = null;
			float[] uv1 = geometry.uv;
			float[] uv2 = null;
			int[] verticesIndices = geometry.verticesIndices;
			int[] uvIndices = geometry.uvIndices;
			
			//Identify Vertices Indices
			for (int i = 2; i < verticesIndices.length; i += 3)
			{
				verticesIndices[i] = ((verticesIndices[i] * -1) - 1);
			}
			
			//Flip Y of UV
			for (int i = 1; i < uv1.length; i += 2)
			{
				uv1[i] = 1 - uv1[i];
			}
			
			int last = vertices1.length/3;
			int count = 0;
			int[] t = new int[uvIndices.length];
			for (int i = 0; i < t.length; i++)
			{
				t[verticesIndices[i]] = uvIndices[i];
			}
			
			ArrayList<Float> nv = new ArrayList<Float>();

			for (int i = 0; i < t.length; i++)
			{
				if(t[verticesIndices[i]] != uvIndices[i])
				{
					count++;
					
					nv.add(vertices1[verticesIndices[i] * 3]);
					nv.add(vertices1[(verticesIndices[i] * 3) + 1]);
					nv.add(vertices1[(verticesIndices[i] * 3) + 2]);
					
					fbx.data.oldIndice.add(verticesIndices[i]);
					fbx.data.newIndice.add(last);
					
					verticesIndices[i] = last;
					last++;
				}
			}
			
			int length = vertices1.length+(count*3);
			vertices2 = new float[length];
			normals2 = new float[length];
			uv2 = new float[vertices1.length+(count*2)];
			
			System.arraycopy(vertices1, 0, vertices2, 0, vertices1.length);
			for (int i = vertices1.length; i < vertices2.length; i++)
			{
				vertices2[i] = nv.get(i - vertices1.length);
			}
			
			for (int i = 0; i < verticesIndices.length; i++)
			{
				int index1 = verticesIndices[i]*3;
				int index2 = i*3;
				
				normals2[index1] = normals1[index2];
				normals2[index1+1] = normals1[index2+1];
				normals2[index1+2] = normals1[index2+2];
				
				int index3 = verticesIndices[i]*2;
				int index4 = uvIndices[i]*2;
				
				uv2[index3] = uv1[index4];
				uv2[index3 + 1] = uv1[index4 + 1];
			}

			geometry.vertices = vertices2;
			geometry.normals = normals2;
			geometry.uv = uv2;
		}

		fbx.fsm.changeState(ObjectConnections.instance);
	}
	
	@Override
	public void start(FbxPlugin fbx)
	{
		Log.e("State", FbxPlugin.TAGS[3]);
	}
	
	private float[] readFloatAttribute(FbxPlugin fbx)
	{
		String line = "";
		StringBuilder sb = new StringBuilder();
		while(!(line = fbx.raw.dequeue()).contains("}"))
		{
			sb.append(line);
		}

		
		String s = sb.toString().replaceFirst("\\W*a: ", "");
		String[] attributes = s.split(",");

		float[] items = new float[attributes.length];
		for (int i = 0; i < items.length; i++)
		{
			items[i] = Float.parseFloat(attributes[i]);
		}
		
		
		return items;
	}
	
	private int[] readIntAttribute(FbxPlugin fbx)
	{
		String line = "";
		StringBuilder sb = new StringBuilder();
		while(!(line = fbx.raw.dequeue()).contains("}"))
		{
			sb.append(line);
		}
		
		String s = sb.toString().replaceFirst("\\W*a: ", "");
		String[] attributes = s.split(",");
		
		int[] items = new int[attributes.length];
		for (int i = 0; i < items.length; i++)
		{
			items[i] = Integer.parseInt(attributes[i]);
		}
		
		return items;
	}
	
}
