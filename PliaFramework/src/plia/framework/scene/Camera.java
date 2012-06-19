package plia.framework.scene;


public class Camera extends Object3D
{
	private float range;
	private int projectionType = PERSPECTIVE;
	

	public Camera()
	{
		setName("Camera");
	}
	
	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		boolean hasChanged = this.hasChanged;
		
		super.onUpdateHierarchy(parentHasChanged);
		
		if(isActive() && (hasChanged || parentHasChanged) && Scene.mainCamera == this)
		{
			Scene.hasChangedModelView = true;
		}
	}

	public float getRange()
	{
		return range;
	}
	
	public void setRange(float range)
	{
		this.range = range;
		if(Scene.mainCamera == this)
		{
			Scene.hasChangedProjection = true;
		}
	}
	
	public int getProjectionType()
	{
		return projectionType;
	}
	
	public void setProjectionType(int projectionType)
	{
		this.projectionType = projectionType;
		if(Scene.mainCamera == this)
		{
			Scene.hasChangedProjection = true;
		}
	}

	public static final int PERSPECTIVE 	= 0;
	public static final int ORTHOGONAL 		= 1;
}
