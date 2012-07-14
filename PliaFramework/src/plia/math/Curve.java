package plia.math;

public class Curve
{
	public static float bezierBlendingFunction(float p0, float p1, float p2, float p3, float t)
	{
		float o_t = 1-t;
		float tt = t*t;
		float ttt = tt*t;
		
		float b0 = (o_t * o_t *o_t);
		float b1 = 3f * (o_t * o_t) * t;
		float b2 = 3f * o_t * t * t;
		float b3 = ttt;
		
		return (b0 * p0) + (b1 * p1) + (b2 * p2) + (b3 * p3);
	}
	
	public static float bSplineBlendingFunction(float p0, float p1, float p2, float p3, float t)
	{
		float o_t = 1-t;
		float tt = t*t;
		float ttt = tt*t;
		
		float b0 = (o_t * o_t *o_t) / 6f;
		float b1 = ((3*ttt) - (6*tt) + 4) / 6f;
		float b2 = ((-3*ttt) + (3*tt) + (3*t) + 1) / 6f;
		float b3 = ttt / 6f;
		
		return (b0 * p0) + (b1 * p1) + (b2 * p2) + (b3 * p3);
	}
}
