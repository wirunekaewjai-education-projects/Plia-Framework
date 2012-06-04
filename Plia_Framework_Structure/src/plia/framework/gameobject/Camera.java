package plia.framework.gameobject;

public class Camera extends Object3D
{
	private float range;
	private int projectionType = PERSPECTIVE;
	
	public float getRange()
	{
		return range;
	}
	
	public void setRange(float range)
	{
		this.range = range;
	}
	
	public int getProjectionType()
	{
		return projectionType;
	}
	
	public void setProjectionType(int projectionType)
	{
		this.projectionType = projectionType;
	}
	
	public static final int PERSPECTIVE 	= 0;
	public static final int ORTHOGONAL 		= 1;
}
