package plia.test;

import android.util.Log;
import plia.framework.core.Game;
import plia.framework.core.GameObjectManager;
import plia.framework.core.GameTime;
import plia.framework.core.debug.Debug;
import plia.framework.core.math.Matrix;
import plia.framework.core.scene.Camera;
import plia.framework.core.scene.Group;
import plia.framework.core.scene.Light;
import plia.framework.core.scene.Model;
import plia.framework.core.scene.component.Animation;
import plia.framework.core.scene.component.AnimationClip;
import plia.framework.core.scene.component.BoundingBox;
import plia.framework.core.scene.component.Plane;
import plia.framework.core.scene.component.PlaybackMode;
import plia.framework.graphics.Color3;
import plia.framework.math.Vector2;
import plia.framework.math.Vector3;
import plia.framework.scene.Layer;
import plia.framework.scene.Scene;

public class Scene1 extends Scene
{
	private Layer layer1 = new Layer();
	Group model, model2, model3;
	Group group = new Group("Group1");
	Group group2 = new Group("Group2");

	public void onInitialize()
	{
		Game.enabledDebug = true;
		model = GameObjectManager.loadModel("elementalist31.FBX");
//		model2 = GameObjectManager.loadModel("buffylow.FBX");
//		model3 = GameObjectManager.loadModel("scene02.FBX");
		
		layer1.addChild(model);
//		layer1.addChild(model2);
	
		layer1.addChild(l1);
//		layer1.addChild(l2);
//		layer1.addChild(l3);
//		layer1.addChild(l4);
//		layer1.addChild(l5);
		
//		model.addChild(model3);
		
		l1.setForward(0, -1, 0);
		l1.setIntensity(1);
		
		this.addChild(layer1);
		
		Camera camera = Scene.mainCamera;
		camera.setPosition(100, 120, 100);
		camera.setLookAt(model);
		camera.setProjectionType(Camera.PERSPECTIVE);
		camera.setRange(1000);
		
		layer1.addChild(camera);
		
		model.getModel().getMaterial().setBaseTexture(GameObjectManager.loadTexture2D("diffuse.jpg"));
		
//		model2.addChild(model);
		
//		model2.setPosition(50, 50, 0);
//		model.setPosition(-50, -70, 0);
////		model.setEulerAngles(0, 45, 0);
//		
//		AnimationClip clip = model2.getAnimation().getAnimationClip("idle");
//		clip.setPlaybackMode(PlaybackMode.LOOP);
//		clip.setEnd(50);
//		clip.setStart(35);
//		
		AnimationClip clip2 = model.getAnimation().getAnimationClip("idle");
		clip2.setPlaybackMode(PlaybackMode.LOOP);
		clip2.setEnd(200);
//		
//		AnimationClip clip3 = model3.getAnimation().getAnimationClip("idle");
//		clip3.setPlaybackMode(PlaybackMode.LOOP);
//		clip3.setEnd(100);
//		
//		model2.getAnimation().play("idle");
		model.getAnimation().play("idle");
//		model3.getAnimation().play("idle");
//		
////		model2.setEulerAngles(0, 45, 0);
//		
//		BoundingBox bb = new BoundingBox();
//		bb.set(-20, -20, -50, 20, 20, 50);
//		
//		model.addBounds(bb);
////		model2.rotate(90, 0, 0);
//		
//		BoundingBox bb2 = model2.getModel().getGeometry().getMesh().getBounds();
//		bb2.setCenter(0, 20, 30);
////		bb2.setSize(40, 60, 40);
//		model3.setPosition(-100, 100, 0);
//		
//		for (int i = 0; i < model3.getChildCount(); i++)
//		{
//			model3.getChild(i).getModel().getMaterial().setBaseColor(1, i*0.25f, (float) Math.random());
//		}
	}

	public void onUpdate()
	{

		model.rotate(0, 0, 1);

//		model3.rotate(0, 0, -1, true);
		
//		Debug.drawLine(new Vector3(0, 0, 0), new Vector3(-100, 100, 0), new Color3(1, 0, 0));
		
//		Plane p = new Plane(new Vector3(), new Vector3(0, 1, 0), new Vector2(50, 100));
//		
//		Color3 c = new Color3(1, 0, 0);
//		Debug.drawLine(p.getP0(), p.getP1(), c);
//		Debug.drawLine(p.getP1(), p.getP2(), c);
//		Debug.drawLine(p.getP2(), p.getP3(), c);
//		Debug.drawLine(p.getP3(), p.getP0(), c);
		
//		Vector3 center = new Vector3(0,0,0);
//		
//		drawPlane(Vector3.add(model.getPosition(), Vector3.scale(model.getForward(), 100)), model.getForward(), new Vector2(50, 50));
//		drawBox(new Vector3(0, 0, 0), new Vector3(50, 100, 200)	);
	}
	
	private void drawBox(Vector3 center, Vector3 size)
	{
		drawPlane(new Vector3(center.x, center.y + size.y/2, center.z), forward, new Vector2(size.x, size.z));
		drawPlane(new Vector3(center.x, center.y - size.y/2, center.z), backward, new Vector2(size.x, size.z));
		
		drawPlane(new Vector3(center.x, center.y, center.z + size.z/2), up, new Vector2(size.x, size.y));
		drawPlane(new Vector3(center.x, center.y, center.z - size.z/2), down, new Vector2(size.x, size.y));
		
		drawPlane(new Vector3(center.x - size.x/2, center.y, center.z), left, new Vector2(size.z, size.y));
		drawPlane(new Vector3(center.x + size.x/2, center.y, center.z), right, new Vector2(size.z, size.y));
	}
	
//	private void drawPlane(Vector3 center, Vector3 up, Vector2 size)
//	{
//		
//	}
	
	private void drawPlane(Vector3 center, Vector3 up, Vector2 size)
	{
		Plane p = new Plane(center, up, size);
		Debug.drawLine(p.getP0(), p.getP1(), color);
		Debug.drawLine(p.getP1(), p.getP2(), color);
		Debug.drawLine(p.getP2(), p.getP3(), color);
		Debug.drawLine(p.getP3(), p.getP0(), color);
		
		Vector3 c = p.getCenter();
		Vector3 n = p.getNormal();
		
		Vector3.scale(n, n, 100);
		
		Vector3 end = new Vector3();
		Vector3.add(end, c, n);

		Debug.drawLine(c, end, color2);
	}
	
	Vector3 forward = new Vector3(0, 1, 0);
	Vector3 backward = new Vector3(0, -1, 0);
	Vector3 up = new Vector3(0, 0, 1);
	Vector3 down = new Vector3(0, 0, -1);
	Vector3 left = new Vector3(-1, 0, 0);
	Vector3 right = new Vector3(1, 0, 0);
	
	Color3 color = new Color3(0.5f, 1, 0.5f);
	Color3 color2 = new Color3(1, 0, 0);
	
	Light l1 = new Light();
	Light l2 = new Light();
	Light l3 = new Light();
	Light l4 = new Light();
	Light l5 = new Light();
}
