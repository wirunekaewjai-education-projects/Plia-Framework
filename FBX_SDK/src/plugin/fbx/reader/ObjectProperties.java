package plugin.fbx.reader;

import java.util.HashMap;

import plugin.fbx.data.Geometry;
import android.util.Log;


public class ObjectProperties implements FbxReadState
{
	public static final ObjectProperties instance = new ObjectProperties();

	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		while(!(line = fbx.raw.dequeue()).contains(FbxPlugin.TAGS[4]))
		{
			if(line.contains("Geometry: "))
			{
				String geometry_id = line.replaceFirst("\\W*Geometry: ", "").split(",")[0];
				Geometry geometry = new Geometry(geometry_id);
				
				while((line = fbx.raw.dequeue()) != null)
				{
					if(line.contains("Vertices: *"))
					{
						geometry.vertices = readFloatAttribute(fbx);
					}
					else if(line.contains("Normals: *"))
					{
						geometry.normals = readFloatAttribute(fbx);
					}
					else if(line.contains("UV: *"))
					{
						geometry.uv = readFloatAttribute(fbx);
					}
					else if(line.contains("PolygonVertexIndex: *"))
					{
						geometry.verticesIndices = readIntAttribute(fbx);
					}
					else if(line.contains("UVIndex: *"))
					{
						geometry.uvIndices = readIntAttribute(fbx);
						
						break;
					}
				}
				
				float[] vertices1 = geometry.vertices;
				float[] vertices2 = null;
				float[] normals = null;
				float[] uv = null;
				int[] verticesIndices = geometry.verticesIndices;
				int[] uvIndices = geometry.uvIndices;
				
				//Identify Vertices Indices
				if(verticesIndices != null)
				{
					for (int i = 2; i < verticesIndices.length; i += 3)
					{
						verticesIndices[i] = ((verticesIndices[i] * -1) - 1);
					}
				}
				
				int last = vertices1.length/3;
				int count = 0;
				int[] t = new int[uvIndices.length];
				for (int i = 0; i < t.length; i++)
				{
					t[verticesIndices[i]] = uvIndices[i];
				}
				HashMap<Integer, Integer> traveled = new HashMap<Integer, Integer>();
				HashMap<Integer, Integer> afterTravel = new HashMap<Integer, Integer>();
				for (int i = 0; i < t.length; i++)
				{
					if(t[verticesIndices[i]] != uvIndices[i])
					{
						if(traveled.containsKey(uvIndices[i]))
						{
							if(traveled.get(uvIndices[i]) == t[verticesIndices[i]])
							{
								verticesIndices[i] = afterTravel.get(verticesIndices[i]);
								continue;
							}
								
						}
						count++;
						Log.e("Count : "+count, "T : "+t[verticesIndices[i]]+", UV : "+uvIndices[i]);
						Log.e("Count : "+count, "Old : " + verticesIndices[i]+" ||| New : "+ last);
						
						traveled.put(uvIndices[i], t[verticesIndices[i]]);
						afterTravel.put(verticesIndices[i], last);
						
						verticesIndices[i] = last;
						last++;
					}
				}
				Log.e("Count", count+"");
//				//Flip Y of UV
//				if(uv != null)
//				{
//					for (int i = 1; i < uv.length; i += 2)
//					{
//						uv[i] = 1 - uv[i];
//					}
//				}
				
				
				
				
				fbx.data.geometries.put(geometry_id, geometry);
			}
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

		
		String[] attributes = sb.toString().split(",");
		float[] items = new float[attributes.length];
		
		items[1] = Float.parseFloat(attributes[0].split("a: ")[1]);
		for (int i = 1; i < items.length; i++)
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
		
		String[] attributes = sb.toString().split(",");
		int[] items = new int[attributes.length];
		
		items[1] = Integer.parseInt(attributes[0].split("a: ")[1]);
		for (int i = 1; i < items.length; i++)
		{
			items[i] = Integer.parseInt(attributes[i]);
		}
		
		return items;
	}
	
}
