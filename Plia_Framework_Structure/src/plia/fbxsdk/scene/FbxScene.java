package plia.fbxsdk.scene;

import java.util.ArrayList;

import plia.fbxsdk.fileio.FbxGlobalSetting;
import plia.fbxsdk.scene.geometry.FbxGeometry;
import plia.fbxsdk.scene.geometry.FbxNode;

public class FbxScene
{
	private FbxNode rootnodes = new FbxNode(0);
	private ArrayList<FbxNode> nodes = new ArrayList<FbxNode>();
	private ArrayList<FbxGeometry> geometries = new ArrayList<FbxGeometry>();
	private FbxGlobalSetting globalSetting = new FbxGlobalSetting();

	public FbxNode getRootnodes()
	{
		return rootnodes;
	}
	
	public FbxGlobalSetting globalSetting()
	{
		return globalSetting;
	}
	
	public void addGeometry(FbxGeometry geometry)
	{
		geometries.add(geometry);
	}
	
	public void removeGeometry(FbxGeometry geometry)
	{
		geometries.remove(geometry);
	}
	
	public FbxGeometry getGeometry(int index)
	{
		return geometries.get(index);
	}
	
	public int getGeometryCount()
	{
		return geometries.size();
	}
	
	public void addNode(FbxNode node)
	{
		nodes.add(node);
	}
	
	public void removeNode(FbxNode node)
	{
		nodes.remove(node);
	}
	
	public FbxNode getNode(int index)
	{
		return nodes.get(index);
	}
	
	public int getNodeCount()
	{
		return nodes.size();
	}
}
