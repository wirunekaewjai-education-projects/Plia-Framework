package plia.scene.shading;

import plia.math.Vector3;
import android.graphics.Color;

public final class NormalMap extends Texture2D
{
	private NormalMap(String name, int texBuffer, int[] pixels, int width, int height)
	{
		super(name, texBuffer, pixels, width, height);
		// TODO Auto-generated constructor stub
	}

	public static Vector3 getNormalFromPixel(Texture2D texture2d, int x, int y)
	{
		int row = y * texture2d.getHeight();
		int i = row + x;
		
		float r = Color.red(texture2d.pixels[i]) / 255f;
		float g = Color.green(texture2d.pixels[i]) / 255f;
		float b = Color.blue(texture2d.pixels[i]) / 255f;
		
		float nx = (r - 0.5f) * 2f;
		float ny = (g - 0.5f) * 2f;
		float nz = (b - 0.5f) * 2f;
		
		return new Vector3(nx, ny, nz);
	}
}