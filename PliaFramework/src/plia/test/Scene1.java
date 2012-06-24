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
	private Object3D model1, model2;
	private Camera camera;
	private Light skylight;

	public void onInitialize()
	{
		long start = System.nanoTime();
		model1 = GameObjectManager.loadModel("buffylow.FBX");
		model2 = GameObjectManager.loadModel("elementalist31.FBX");
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
		layer1.addChild(model1);
		layer1.addChild(model2);
		layer1.addChild(camera);
		layer1.addChild(skylight);
		
		model1.getAnimation().getAnimationClip("idle").setPlaybackMode(PlaybackMode.LOOP);
		model1.getAnimation().getAnimationClip("idle").setStart(35);
		model1.getAnimation().getAnimationClip("idle").setEnd(50);
		model1.getAnimation().play("idle");
		
		model2.setPosition(80, 0, 0);
		
		model2.getAnimation().getAnimationClip("idle").setPlaybackMode(PlaybackMode.LOOP);
		model2.getAnimation().getAnimationClip("idle").setEnd(200);
		model2.getAnimation().play("idle");
	}

	public void onUpdate()
	{
		model1.rotate(0, 0, 1);
		model2.rotate(0, 0, -0.5f);
	}

}
