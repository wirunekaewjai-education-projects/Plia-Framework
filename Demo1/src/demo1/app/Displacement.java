package demo1.app;

import demo1.main.Grid;
import plia.core.Game;
import plia.core.GameObjectManager;
import plia.core.scene.Terrain;
import plia.math.Vector3;

public class Displacement extends BaseApplication
{
	private Terrain terrain;
	private int max = 32;
	private float currentHeight = 2;
	private int dir = 1;
	
	private int delay = 0;

	public Displacement()
	{
		super("Displacement Mapping");
		camera.setPosition(40, 40, 40);
		camera.setLookAt(new Vector3());
		camera.setRange(1000);
		
		terrain = GameObjectManager.createTerrain("terrain/heightmap.jpg", max, 80);
		terrain.setBaseTexture(Game.tex2D("terrain/heightmap.jpg"));
		terrain.setPosition(-40, -40, 0);

		_3DLayer.addChild(camera, terrain);
	}

	public void update()
	{
		Grid.draw();
		camera.rotate(0, 0, 0.1f, true);
		
		if(currentHeight >= max || currentHeight < 2)
		{
			delay++;
		}
		
		if(delay == 20)
		{
			delay = 0;
			dir *= -1;
		}
		
		if(delay == 0)
		{
			currentHeight += 0.5f * dir;
		}
		
		terrain.setTerrainMaxHeight((int) currentHeight);
	}

}
