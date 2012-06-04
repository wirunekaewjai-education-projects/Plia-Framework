package plia.framework.gameobject.fbxobject;

public class FbxCluster extends FbxNode
{
	private float[] transform;
	private float[] transformLink;
	
	public float[] getTransform()
	{
		return transform;
	}
	
	public float[] getTransformLink()
	{
		return transformLink;
	}
	
	public void setTransform(float[] transform)
	{
		this.transform = transform;
	}
	
	public void setTransformLink(float[] transformLink)
	{
		this.transformLink = transformLink;
	}
}
