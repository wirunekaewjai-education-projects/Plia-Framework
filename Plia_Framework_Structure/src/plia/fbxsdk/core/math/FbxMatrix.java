package plia.fbxsdk.core.math;

public class FbxMatrix
{
	public static void createTRS(float[] result, float tx, float ty, float tz, float rx, float ry, float rz, float sx, float sy, float sz)
	{
		float ax = rx * 57.29578f;
		float ay = ry * 57.29578f;
		float az = rz * 57.29578f;

		// Heading ( Yaw :: Z Axis ) ; First
		float ch = (float) Math.cos(az);
		float sh = (float) Math.sin(az);

		// Attitude ( Pitch :: Y Axis ) ; Second
		float ca = (float) Math.cos(ay);
		float sa = (float) Math.sin(ay);

		// Bank ( Roll :: X Axis ) ; Last
		float cb = (float) Math.cos(ax);
		float sb = (float) Math.sin(ax);
		//
		//
		result[0] = (ch * ca) * sx;
		result[1] = (sh * ca) * sy;
		result[2] = -sa * sz;
		result[3] = 0;

		result[4] = ((-sh * cb) + (sb * (sa * ch))) * sx;
		result[5] = ((ch * cb) + (sb * (sa * sh))) * sy;
		result[6] = (sb * ca) * sz;
		result[7] = 0;

		result[8] = ((-sb * -sh) + (cb * (sa * ch))) * sx;
		result[9] = ((-sb * ch) + (cb * (sa * sh))) * sy;
		result[10] = (cb * ca) * sz;
		result[11] = 0;

		result[12] = tx * sx;
		result[13] = ty * sy;
		result[14] = tz * sz;
		result[15] = 1;
	}
}
