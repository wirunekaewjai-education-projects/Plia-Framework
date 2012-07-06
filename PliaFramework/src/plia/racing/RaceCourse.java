package plia.racing;

import plia.core.scene.Collider;
import plia.core.scene.Group;
import plia.core.scene.Terrain;
import plia.core.scene.shading.Color3;
import plia.core.scene.shading.Color4;
import plia.core.scene.shading.Texture2D;
import plia.math.Vector3;

public class RaceCourse extends Terrain
{
	private Group terrainModel;
	private Texture2D raceTrackBounds;
	private boolean isChanged = true;

	public RaceCourse(Group terrainModel, Texture2D raceTrackBounds, Texture2D heightmap, int maxHeight, int scale)
	{
		super(heightmap, maxHeight, scale);
		
		this.terrainModel = terrainModel;
		this.raceTrackBounds = raceTrackBounds;
	}

	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		isChanged = parentHasChanged || hasChanged;
		
		super.onUpdateHierarchy(parentHasChanged);
		
		for (Collider collider : attached)
		{
			collisionTrack(collider);
		}
		
		isChanged = false;
	}
	
	private void collisionTrack(Collider collider)
	{
		if(isChanged)
		{
			float scale = getTerrainScale();
			
			Vector3 bPos = collider.getWorldMatrix().getTranslation();
			
			// find uv by normalize object position with terrain scale
			Vector3 terrainPosition = getWorldMatrix().getTranslation();
			
			float npx = Math.min(scale, Math.max(0f, bPos.x - terrainPosition.x));
			float npy = Math.min(scale, Math.max(0f, bPos.y - terrainPosition.y));
			
			float u = npx / (float)scale;
			float v = npy / (float)scale;
			
			float rtw = (raceTrackBounds.getWidth() - 1);
			float rth = (raceTrackBounds.getHeight() - 1);
			
			// scale 'uv' up to bitmap size
			int rts = (int) (u * rtw);
			int rtt = (int) (v * rth);
			
			Color4 color = raceTrackBounds.getPixel(rts, rtt);
			
			
		}
	}
	
	private void getColor(Vector3 position)
	{
		
	}
}
