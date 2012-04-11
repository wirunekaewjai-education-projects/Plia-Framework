package plugin.fbx.reader;

import java.util.HashMap;

import plugin.fbx.data.Geometry;


public class FbxData
{
	public HashMap<String, Geometry> geometries = null;
	
	public FbxData()
	{
		geometries = new HashMap<String, Geometry>();
	}
	
	
}