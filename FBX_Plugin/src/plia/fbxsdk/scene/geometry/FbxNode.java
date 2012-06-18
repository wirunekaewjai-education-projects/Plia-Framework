package plia.fbxsdk.scene.geometry;

import java.util.ArrayList;

import plia.framework.math.*;
import plia.fbxsdk.core.FbxObject;
import plia.fbxsdk.scene.animation.FbxAnimCurveNode;
import plia.fbxsdk.scene.shading.FbxSurfaceMaterial;

public class FbxNode extends FbxObject
{
	private FbxNode parent = null;
	private final ArrayList<FbxNode> children = new ArrayList<FbxNode>();
	private FbxNodeAttribute nodeAttribute = null;

	private Vector3 defaultLclTranslation = new Vector3();
	private Vector3 defaultLclRotation = new Vector3();
	private Vector3 defaultLclScaling = new Vector3(1,1,1);
	
	private FbxAnimCurveNode animCurveNodeT;
	private FbxAnimCurveNode animCurveNodeR;
	private FbxAnimCurveNode animCurveNodeS;
	
	private FbxSurfaceMaterial material;

	public FbxNode(long uniqueID)
	{
		super(uniqueID);
	}
	
	public boolean isRoot()
	{
		return parent == null;
	}

	public FbxNode getParent()
	{
		return parent;
	}

	public void setParent(FbxNode parent)
	{
		this.parent = parent;
	}
	
	public ArrayList<FbxNode> getChildren()
	{
		return children;
	}
	
	public boolean addChild(FbxNode child)
	{
		return children.add(child);
	}
	
	public boolean removeChild(FbxNode child)
	{
		return children.remove(child);
	}
	
	public FbxNode getChild(int index)
	{
		return children.get(index);
	}
	
	public int getChildCount()
	{
		return children.size();
	}
	
	public Vector3 getLclTranslation()
	{
		return defaultLclTranslation;
	}
	
	public Vector3 getLclTranslation(long frame)
	{
		if(animCurveNodeT != null)
		{
			return animCurveNodeT.getValue(frame);
		}
		return defaultLclTranslation;
	}
	
	public void setLclTranslation(Vector3 lclTranslation)
	{
		this.defaultLclTranslation = lclTranslation;
	}
	
	public void setLclTranslation(FbxAnimCurveNode lclTranslation)
	{
		this.animCurveNodeT = lclTranslation;
	}
	
	public Vector3 getLclRotation()
	{
		return defaultLclRotation;
	}
	
	public Vector3 getLclRotation(long frame)
	{
		if(animCurveNodeR != null)
		{
			return animCurveNodeR.getValue(frame);
		}
		
		return defaultLclRotation;
	}
	
	public void setLclRotation(Vector3 lclRotation)
	{
		this.defaultLclRotation = lclRotation;
	}
	
	public void setLclRotation(FbxAnimCurveNode lclRotation)
	{
		this.animCurveNodeR = lclRotation;
	}
	
	public Vector3 getLclScaling()
	{
		return defaultLclScaling;
	}
	
	public Vector3 getLclScaling(long frame)
	{
		if(animCurveNodeS != null)
		{
			return animCurveNodeS.getValue(frame);
		}
		
		return defaultLclScaling;
	}
	
	public void setLclScaling(Vector3 lclScaling)
	{
		this.defaultLclScaling = lclScaling;
	}
	
	public void setLclScaling(FbxAnimCurveNode lclScaling)
	{
		this.animCurveNodeS = lclScaling;
	}
	
	public FbxAnimCurveNode getAnimCurveNodeT()
	{
		return animCurveNodeT;
	}
	
	public FbxAnimCurveNode getAnimCurveNodeR()
	{
		return animCurveNodeR;
	}
	
	public FbxAnimCurveNode getAnimCurveNodeS()
	{
		return animCurveNodeS;
	}

	public FbxNodeAttribute getNodeAttribute()
	{
		return nodeAttribute;
	}

	public void setNodeAttribute(FbxNodeAttribute nodeAttribute)
	{
		this.nodeAttribute = nodeAttribute;
		this.nodeAttribute.addNode(this);
	}
	
	public FbxGeometry getGeometry()
	{
		return (FbxGeometry) this.nodeAttribute;
	}
	
	public FbxMesh getMesh()
	{
		return (FbxMesh) this.nodeAttribute;
	}
	
	public FbxSkeleton getSkeleton()
	{
		return (FbxSkeleton) this.nodeAttribute;
	}
	
	public FbxSurfaceMaterial getMaterial()
	{
		return material;
	}
	
	public void setMaterial(FbxSurfaceMaterial material)
	{
		this.material = material;
	}
}
