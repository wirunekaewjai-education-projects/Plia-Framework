package plia.core.scene;

import plia.core.scene.geometry.Plane;
import plia.core.scene.shading.Heightmap;
import plia.core.scene.shading.Texture2D;

public class DisplacementTerrain extends Terrain
{
	float[] heights;
	
	public DisplacementTerrain(Texture2D heightmap, int maxHeight, int scale)
	{
		super(heightmap, maxHeight, scale);
		
		int segment = Plane.getInstance().getSegment();
//		heightBuffer = ByteBuffer.allocateDirect(segment * segment * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		heights = new float[segment * segment];
		for (int y = 0; y < segment; y++)
		{
			for (int x = 0; x < segment; x++)
			{
				int row = y * segment;
				int i = row + x;
				
				int xx = (int) ((x / (float)segment) * heightmap.getWidth());
				int yy = (int) ((y / (float)segment) * heightmap.getHeight());
				
				heights[i] = Heightmap.getHeightFromPixel(heightmap, xx, yy);
			}
		}
		
//		heightBuffer.put(heights).position(0);
	}
	
	public float[] getHeights()
	{
		return heights;
	}
}
