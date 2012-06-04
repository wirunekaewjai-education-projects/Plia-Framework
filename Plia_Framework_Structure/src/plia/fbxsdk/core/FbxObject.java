package plia.fbxsdk.core;

public class FbxObject
{
	public String name;
	public final long uniqueID;
	
	public FbxObject(long uniqueID)
	{
		this.uniqueID = uniqueID;
	}
	
	
//	// SubDeformerType
//	public static final int Cluster 		= 10001;
//
//	// Deformer Type
//	public static final int Unidentified 	= 20001;
//	public static final int Skin 			= 20002;
//	public static final int VertexCache 	= 20003;
//	public static final int DeformerCount 	= 20004;
//
//	// Skeleton Type
//	public static final int Root 			= 30001;
//	public static final int Limb 			= 30002;
//	public static final int LimbNode 		= 30003;
//	public static final int Effector 		= 30004;
//
//	// Node Attribute Type
//	public static final int Mesh 			= 40001;
//	public static final int Skeleton 		= 40002;
//	public static final int Null 			= 40003;
}