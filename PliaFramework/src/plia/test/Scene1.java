package plia.test;

import plia.framework.core.GameObjectManager;
import plia.framework.math.Vector3;
import plia.framework.scene.Camera;
import plia.framework.scene.Layer;
import plia.framework.scene.Light;
import plia.framework.scene.Object3D;
import plia.framework.scene.Scene;
import plia.framework.scene.obj3d.animation.PlaybackMode;

public class Scene1 extends Scene
{
	private Layer<Object3D> layer1 = new Layer<Object3D>();
	private Object3D model;
	private Camera camera;
	private Light skylight;

	public void onInitialize()
	{
		long start = System.nanoTime();
		model = GameObjectManager.loadModel("elementalist31.FBX");
		float end = (System.nanoTime() - start)/ 1000000f;
		log("Load Time : "+end+" ms");

		skylight = new Light();
		skylight.setForward(0, -1, 0);

		camera = Scene.getMainCamera();
		camera.setPosition(100, 120, 100);
		camera.setLookAt(new Vector3());
		camera.setProjectionType(Camera.PERSPECTIVE);
		camera.setRange(1000);
		
		addLayer(layer1);
		layer1.addChild(model);
		layer1.addChild(camera);
		layer1.addChild(skylight);
		
		model.getAnimation().getAnimationClip("idle").setPlaybackMode(PlaybackMode.LOOP);
		model.getAnimation().getAnimationClip("idle").setEnd(100);
		model.getAnimation().play("idle");
	}

	public void onUpdate()
	{
		model.rotate(0, 0, 1);
	}

}
