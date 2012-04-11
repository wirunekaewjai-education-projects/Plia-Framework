package plugin.fbx.reader;

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
