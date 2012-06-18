package plia.framework.scene;

import plia.framework.math.Matrix4;
import plia.framework.math.Vector3;
import plia.framework.math.Vector4;

public final class BoundingPlane extends Bounds
{
	private final Vector4[] p = new Vector4[4];
	private final Vector4[] corner = new Vector4[4];
	
	public BoundingPlane()
	{
		for (int i = 0; i < p.length; i++)
		{
			p[i] = new Vector4();
			corner[i] = new Vector4();
		}
		this.updateCorner();
	}
	
	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		boolean hasChanged = this.hasChanged;
		
		super.onUpdateHierarchy(parentHasChanged);
		
		if(isActive() && hasChanged)
		{
			for (int i = 0; i < p.length; i++)
			{
				Matrix4.multiply(p[i], getWorldMatrix(), corner[i]);
			}
		}
	}

	@Override
	public void setScale(Vector3 scale)
	{
		super.setScale(scale);
		this.updateCorner();
	}
	
	private void updateCorner()
	{
		float ex = localScaling.x / 2f;
		float ey = localScaling.y / 2f;

		corner[0].set(-ex, -ey, 0, 1);
		corner[1].set(-ex, ey, 0, 1);
		corner[2].set(ex, ey, 0, 1);
		corner[3].set(ex, -ey, 0, 1);
	}
	
	Vector4 getP0()
	{
		return p[0];
	}
	
	Vector4 getP1()
	{
		return p[1];
	}
	
	Vector4 getP2()
	{
		return p[2];
	}
	
	Vector4 getP3()
	{
		return p[3];
	}
}
