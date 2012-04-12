package plugin.fbx.reader;

import java.util.ArrayList;
import java.util.HashMap;

import plugin.fbx.data.Geometry;


public class FbxData
{
	public HashMap<String, Geometry> geometries = null;
	
	ArrayList<Integer> oldIndice = null;
	ArrayList<Integer> newIndice = null;
	
	public FbxData()
	{
		geometries = new HashMap<String, Geometry>();
		oldIndice = new ArrayList<Integer>();
		newIndice = new ArrayList<Integer>();
	}
	
	
}