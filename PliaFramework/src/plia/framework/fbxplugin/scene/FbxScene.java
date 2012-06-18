package plia.framework.fbxplugin.scene;

import java.util.ArrayList;

import plia.framework.fbxplugin.fileio.FbxGlobalSetting;
import plia.framework.fbxplugin.scene.geometry.FbxGeometry;
import plia.framework.fbxplugin.scene.geometry.FbxNode;



public class FbxScene
{
	private FbxNode rootnodes = new FbxNode(0);
	private ArrayList<FbxNode> nodes = new ArrayList<FbxNode>();
	private ArrayList<FbxGeometry> geometries = new ArrayList<FbxGeometry>();
	private FbxGlobalSetting globalSetting = new FbxGlobalSetting();
	
	private int modelCount = 0;
//	private int geometryCount = 0;
	private int nodeAttributeCount = 0;
	private int animCurveCount = 0;
	private int animCurveNodeCount = 0;
	private int deformerCount = 0;
	
	private int startKeyframe;
	private int stopKeyframe;
	private int totalKeyframe;

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
	
	public void setObjectDefinitions(int modelCount, int geometryCount, int nodeAttributeCount, int animCurveCount, int animCurveNodeCount, int deformerCount)
	{
		this.modelCount = modelCount;
//		this.geometryCount = geometryCount;
		this.nodeAttributeCount = nodeAttributeCount;
		this.animCurveCount = animCurveCount;
		this.animCurveNodeCount = animCurveNodeCount;
		this.deformerCount = deformerCount;
	}

	public int getModelCount()
	{
		return modelCount;
	}
	
	public int getNodeAttributeCount()
	{
		return nodeAttributeCount;
	}
	
	public int getAnimCurveCount()
	{
		return animCurveCount;
	}
	
	public int getAnimCurveNodeCount()
	{
		return animCurveNodeCount;
	}
	
	public int getDeformerCount()
	{
		return deformerCount;
	}
	
	public void setKeyframe(int start, int stop, int total)
	{
		this.startKeyframe = start;
		this.stopKeyframe = stop;
		this.totalKeyframe = total;
	}
	
	public int getStartKeyframe()
	{
		return startKeyframe;
	}
	
	public int getStopKeyframe()
	{
		return stopKeyframe;
	}
	
	public int getTotalKeyframe()
	{
		return totalKeyframe;
	}
}
