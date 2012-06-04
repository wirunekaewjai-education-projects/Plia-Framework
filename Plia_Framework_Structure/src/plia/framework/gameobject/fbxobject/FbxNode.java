package plia.framework.gameobject.fbxobject;

import plia.fbxsdk.core.math.FbxVector3;
import plia.fbxsdk.scene.animation.FbxAnimCurveNode;
import plia.framework.gameobject.Object3D;

public class FbxNode extends Object3D
{
	private FbxVector3 defaultLclTranslation;
	private FbxVector3 defaultLclRotation;
	private FbxVector3 defaultLclScaling;
	
	private FbxAnimCurveNode animCurveNodeT;
	private FbxAnimCurveNode animCurveNodeR;
	private FbxAnimCurveNode animCurveNodeS;
	
	public FbxVector3 getLclTranslation()
	{
		return defaultLclTranslation;
	}
	
	public FbxVector3 getLclTranslation(long frame)
	{
		if(animCurveNodeT != null)
		{
			return animCurveNodeT.getValue(frame);
		}
		return defaultLclTranslation;
	}
	
	public void setLclTranslation(FbxVector3 lclTranslation)
	{
		this.defaultLclTranslation = lclTranslation;
	}
	
	public void setLclTranslation(FbxAnimCurveNode lclTranslation)
	{
		this.animCurveNodeT = lclTranslation;
	}
	
	public FbxVector3 getLclRotation()
	{
		return defaultLclRotation;
	}
	
	public FbxVector3 getLclRotation(long frame)
	{
		if(animCurveNodeR != null)
		{
			return animCurveNodeR.getValue(frame);
		}
		
		return defaultLclRotation;
	}
	
	public void setLclRotation(FbxVector3 lclRotation)
	{
		this.defaultLclRotation = lclRotation;
	}
	
	public void setLclRotation(FbxAnimCurveNode lclRotation)
	{
		this.animCurveNodeR = lclRotation;
	}
	
	public FbxVector3 getLclScaling()
	{
		return defaultLclScaling;
	}
	
	public FbxVector3 getLclScaling(long frame)
	{
		if(animCurveNodeS != null)
		{
			return animCurveNodeS.getValue(frame);
		}
		
		return defaultLclScaling;
	}
	
	public void setLclScaling(FbxVector3 lclScaling)
	{
		this.defaultLclScaling = lclScaling;
	}
	
	public void setLclScaling(FbxAnimCurveNode lclScaling)
	{
		this.animCurveNodeS = lclScaling;
	}
}
