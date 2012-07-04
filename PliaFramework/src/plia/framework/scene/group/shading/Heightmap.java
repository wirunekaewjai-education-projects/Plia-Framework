package plia.framework.scene.group.shading;

import android.graphics.Color;

public final class Heightmap extends Texture2D
{
	private Heightmap(String name, int texBuffer, int[] pixels, int width, int height)
	{
		super(name, texBuffer, pixels, width, height);
		// TODO Auto-generated constructor stub
	}

	public static float getHeightFromPixel(Texture2D texture2d, int x, int y)
	{
		int row = y * texture2d.getHeight();
		int i = row + x;

		float r = Color.red(texture2d.pixels[i]) / 255f;
		return r;
	}
	
	
}
