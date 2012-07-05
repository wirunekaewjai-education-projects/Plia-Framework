package plia.scene;

import plia.core.GameObject;

public class Camera extends Group
{
	private float range;
	private int projectionType = PERSPECTIVE;
	private Sky sky;

	public Camera()
	{
		setName("Camera");
	}
	
	public Camera(String name)
	{
		setName(name);
	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		Camera b = (Camera) gameObject;
		b.range = this.range;
		b.projectionType = this.projectionType;
		b.sky = sky;
	}

	@Override
	public Camera instantiate()
	{
		Camera copy = new Camera();
		this.copyTo(copy);
		return copy;
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
	
	public Sky getSky()
	{
		return sky;
	}
	
	public void setSky(Sky sky)
	{
		this.sky = sky;
	}

	public static final int PERSPECTIVE 	= 0;
	public static final int ORTHOGONAL 		= 1;
}
