package plugin.fbx.reader;

import java.util.ArrayList;
import java.util.Collection;

import library.graphics.Mesh;

import plugin.fbx.data.Geometry;

import android.content.Context;
import android.util.Log;

public class FbxFile
{
	private FbxPlugin plugin;
	
	public FbxFile(Context context, String file)
	{
		plugin = new FbxPlugin(context);
		plugin.load(file);
	}
	
	public Mesh createMesh()
	{
		Mesh mesh = null;
		
		Collection<Geometry> geometries = plugin.data.geometries.values();
		int size = geometries.size();
		if(size == 1)
		{
			Log.e("Create Mesh", "Size = 1");
			Geometry geo = null;
			for (Geometry geometry : geometries)
			{
				geo = geometry;
			}
			
			mesh = new Mesh(geo.vertices, geo.normals, geo.uv, geo.verticesIndices);
		}
		else
		{
			mesh = new Mesh();

			for (Geometry geometry : geometries)
			{
				Mesh m = new Mesh(geometry.vertices, geometry.normals, geometry.uv, geometry.verticesIndices);
				mesh.addChild(m);
			}
		}
		
		return mesh;
	}
	
	public Collection<Geometry> getGeometries()
	{
		return plugin.data.geometries.values();
	}
}
