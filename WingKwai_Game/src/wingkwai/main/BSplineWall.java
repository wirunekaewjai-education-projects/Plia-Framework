package wingkwai.main;

import plia.core.scene.PlaneCollider;
import plia.math.Vector3;

public class BSplineWall
{
	private PlaneCollider[] colliders;
	
	public BSplineWall(Vector3...p)
	{
		int length = p.length;
		
		if(length >= 4)
		{
			colliders = new PlaneCollider[length];
		}
	}
}
