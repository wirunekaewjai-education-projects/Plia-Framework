package plia.racing;

import plia.math.Curve;
import plia.math.Vector2;

public class BezierCollider extends CurveCollider
{
	
	public BezierCollider(float step, float height, boolean extrudeHeightFromCenter, Vector2...p)
	{
		super(step, height, extrudeHeightFromCenter, p);

		if(p.length == 4)
		{
			for (float t = 0; t < 1.0001f; t+=step)
			{
				float x = Curve.bezierBlendingFunction(p[0].x, p[1].x, p[2].x, p[3].x, t);
				float y = Curve.bezierBlendingFunction(p[0].y, p[1].y, p[2].y, p[3].y, t);
				
				corners.add(new Vector2(x, y));
			}
		}
	}
	
	
}
