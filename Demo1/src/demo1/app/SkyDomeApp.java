package demo1.app;

import demo1.main.Program;
import plia.core.Game;
import plia.core.scene.SkyDome;

public class SkyDomeApp extends BaseApplication
{
	private SkyDome dome;
	private float turnDir = 0;
	
	public SkyDomeApp()
	{
		super("Sky Dome");
		
		dome = Game.skydome("model/sky_sphere01.jpg");
		camera.setSky(dome);
		camera.setPosition(0, 0, 5);
		camera.setRange(1000);
		
		_3DLayer.addChild(camera);
	}
	
	public void update()
	{
		camera.rotate(0, 0, turnDir);
		
		turnDir = -Program.ACCEL.y / 5f;
		if(Float.isNaN(turnDir))
		{
			turnDir = 0;
		}
	}
}
