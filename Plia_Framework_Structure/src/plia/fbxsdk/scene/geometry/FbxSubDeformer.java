package plia.fbxsdk.scene.geometry;

import plia.fbxsdk.core.FbxObject;

public class FbxSubDeformer extends FbxObject
{
	private final int subDeformerType;
	
	public FbxSubDeformer(long uniqueID, int subDeformerType)
	{
		super(uniqueID);
		this.subDeformerType = subDeformerType;
	}

	public int getSubDeformerType()
	{
		return subDeformerType;
	}

	public static final int Cluster = 10001;
}
