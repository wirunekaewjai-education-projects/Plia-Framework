package plia.core.scene;

import plia.math.Curve;
import plia.math.Vector2;

public class BSplineCollider extends CurveCollider
{
	
	public BSplineCollider(float step, float height, boolean extrudeHeightFromCenter, Vector2...p)
	{
		super(step, height, extrudeHeightFromCenter, p);

		int length = p.length;
		if(length >= 4)
		{
			int count = length - 3;
			int max = length-1;
			
			int indx0 = 0;
			int indx1 = 1;
			int indx2 = 2;
			int indx3 = 3;
			
			for (int i = 0; i < count; i++)
			{
				Vector2 p0 = p[indx0++];
				Vector2 p1 = p[indx1++];
				Vector2 p2 = p[indx2++];
				Vector2 p3 = p[indx3++];
				
				indx0 = (indx0 > max) ? 0 : indx0;
				indx1 = (indx1 > max) ? 0 : indx1;
				indx2 = (indx2 > max) ? 0 : indx2;
				indx3 = (indx3 > max) ? 0 : indx3;
				
				for (float t = 0; t < 1.0001f; t+=step)
				{
					float x = Curve.bSplineBlendingFunction(p0.x, p1.x, p2.x, p3.x, t);
					float y = Curve.bSplineBlendingFunction(p0.y, p1.y, p2.y, p3.y, t);
					
					corners.add(new Vector2(x, y));
				}
			}
			
			
		}
	}
}
