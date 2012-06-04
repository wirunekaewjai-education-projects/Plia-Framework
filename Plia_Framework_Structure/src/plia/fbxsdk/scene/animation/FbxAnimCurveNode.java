package plia.fbxsdk.scene.animation;

import plia.fbxsdk.core.FbxObject;
import plia.fbxsdk.core.math.FbxVector3;

public class FbxAnimCurveNode extends FbxObject
{
	private FbxAnimCurve X;
	private FbxAnimCurve Y;
	private FbxAnimCurve Z;

	public FbxAnimCurveNode(long uniqueID)
	{
		super(uniqueID);
	}
	
	public void set(FbxAnimCurve x, FbxAnimCurve y, FbxAnimCurve z)
	{
		X = x;
		Y = y;
		Z = z;
	}
	
	public FbxVector3 getValue(long frame)
	{
		FbxVector3 v = new FbxVector3();
		
		if(X != null)
		{
			v.x = X.getValue(frame);
		}
		
		if(Y != null)
		{
			v.y = Y.getValue(frame);
		}
		
		if(Z != null)
		{
			v.z = Z.getValue(frame);
		}
		
		return v;
	}
	
	public void getValue(float[] value, int frame)
	{
		
		if(X == null)
		{
			value[0] = 0;
		}
		else
		{
			value[0] = X.getValue(frame);
		}
		
		if(Y == null)
		{
			value[1] = 0;
		}
		else
		{
			value[1] = Y.getValue(frame);
		}
		
		if(Z == null)
		{
			value[2] = 0;
		}
		else
		{
			value[2] = Z.getValue(frame);
		}
		
	}
}
