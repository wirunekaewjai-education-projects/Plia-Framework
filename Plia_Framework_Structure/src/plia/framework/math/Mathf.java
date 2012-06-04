package plia.framework.math;

public class Mathf
{
	public static final float E = 2.718282f;
	public static final float PI = 3.141593f;
	public static final float RadToDegree = 57.29578f;
	public static final float DegToRadian = 0.0174533f;

	public static final float cos(double degree)
	{
		return (float) Math.cos(degree * DegToRadian);
	}
	
	public static final float sin(double degree)
	{
		return (float) Math.sin(degree * DegToRadian);
	}
	
	public static final float tan(double degree)
	{
		return (float) Math.tan(degree * DegToRadian);
	}
	
	public static final float acos(double value)
	{
		return (float) Math.acos(value) * RadToDegree;
	}
	
	public static final float asin(double value)
	{
		return (float) Math.asin(value) * RadToDegree;
	}
	
	public static final float atan(double value)
	{
		return (float) Math.atan(value) * RadToDegree;
	}

	public static final float atan2(double y, double x)
	{
		return (float) Math.atan2(y, x) * RadToDegree;
	}
	
	public static final float cot(double degree)
	{
		return 1.0f/tan(degree);
	}

	public static final float sqrt(double value)
	{
		return (float) Math.sqrt(value);
	}
	
	public static final int round(double value)
	{
		return (int) Math.round(value);
	}
	
	public static final float round(double value, int scale)
	{
		double p = scale * 2.302585092994046;
		double scl = Math.exp(p);
		
		int v = (int) ((value*scl)+0.5f);
		
		return (float) ((float) v / scl);
	}
	
	public static final float clamp(float value, float min, float max)
	{
		float result = value;
		
		if(value > max)
		{
			result = max;
		}
		else if(value < min)
		{
			result = min;
		}
		
		return result;
	}
	
	public static final float min(double a, double b)
	{
		if(a < b)
		{
			return (float) a;
		}
		
		return (float) b;
	}
	
	public static final float max(double a, double b)
	{
		if(a > b)
		{
			return (float) a;
		}
		
		return (float) b;
	}
	
	
	public static final float pow(double value, int power)
	{
		return (float) Math.pow(value, power);
	}

}
