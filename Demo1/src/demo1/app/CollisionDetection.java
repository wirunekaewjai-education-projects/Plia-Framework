package demo1.app;

import demo1.main.Program;
import plia.core.Game;
import plia.core.debug.Debug;
import plia.core.scene.CurveCollider;
import plia.core.scene.SphereCollider;
import plia.core.scene.shading.Color3;
import plia.math.Vector2;
import plia.math.Vector3;

public class CollisionDetection extends BaseApplication
{
	private Color3 sphereColor = new Color3(0.5f, 1, 0.5f);
	private Color3 color = new Color3(1, 1, 0);
	
	private SphereCollider collider;
	private CurveCollider curveCollider;

	private float turnDir = 0;

	public CollisionDetection()
	{
		super("Collision Detection");
		
		collider = new SphereCollider(20);
		collider.setPosition(0, 0, 10);
		
		camera.setPosition(0, -60, 30);
		camera.setLookAt(collider);
		camera.setRange(1000);
		
		Vector2[] p = new Vector2[8];
		p[0] = new Vector2(0, 300);
		p[1] = new Vector2(300, 100);
		p[2] = new Vector2(150, -300);
		p[3] = new Vector2(-150, -300);
		p[4] = new Vector2(-300, 100);
		
		p[5] = new Vector2(0, 300);
		p[6] = new Vector2(300, 100);
		p[7] = new Vector2(150, -300);
		curveCollider = CurveCollider.bSplineCurveCollider(1, 80, false, p);
		curveCollider.attachCollider(collider);
		
		collider.addChild(camera);
		_3DLayer.addChild(collider, curveCollider);
	}

	public void update()
	{
		Game.enabledDebug = true;
		//
		
		collider.translate(0, 0.8f, 0);
		collider.rotate(0, 0, turnDir);
		
		turnDir = -Program.ACCEL.y / 5f;
		if(Float.isNaN(turnDir))
		{
			turnDir = 0;
		}
		
		Debug.drawBounds(collider, sphereColor);
		
		int indx = curveCollider.getPlaneCount();
		for (int i = 0; i < indx; i++)
		{
			Vector3[] c = curveCollider.getCorner(i);
			
			Debug.drawLine(c[0], c[1], color);
			Debug.drawLine(c[1], c[2], color);
			Debug.drawLine(c[2], c[3], color);
			Debug.drawLine(c[3], c[0], color);
		}
		
		//
		Game.enabledDebug = false;
	}

}
