package demo1.main;

import plia.core.Game;
import plia.core.debug.Debug;
import plia.core.scene.shading.Color3;
import plia.math.Vector3;

public class Grid
{
	public static final void draw()
	{
		if(!Game.enabledDebug)
		{
			Game.enabledDebug = true;
		}
		
		
		for (int i = -100; i <= 100; i+=10)
		{
			Vector3 a = new Vector3(i, -100, 0);
			Vector3 b = new Vector3(i, 100, 0);
			
			Vector3 c = new Vector3(-100, i, 0);
			Vector3 d = new Vector3(100, i, 0);
			
			Debug.drawLine(a, b, grey);
			Debug.drawLine(c, d, grey);
		}
		
		Debug.drawLine(origin, x, r);
		Debug.drawLine(origin, y, g);
		Debug.drawLine(origin, z, b);
		
		Game.enabledDebug = false;
	}
	
	static final Vector3 origin = new Vector3(0, 0, 0.02f);
	static final Vector3 x = new Vector3(10, 0, 0.02f);
	static final Vector3 y = new Vector3(0, 10, 0.02f);
	static final Vector3 z = new Vector3(0, 0, 10);
	
	static final Color3 grey = new Color3(0.5f, 0.5f, 0.5f);
	static final Color3 r = new Color3(1, 0, 0);
	static final Color3 g = new Color3(0, 1, 0);
	static final Color3 b = new Color3(0, 0, 1);
}
