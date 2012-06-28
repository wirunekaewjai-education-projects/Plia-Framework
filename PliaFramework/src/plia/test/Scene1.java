package plia.test;

import plia.framework.core.GameObjectManager;
import plia.framework.math.Vector3;
import plia.framework.scene.Camera;
import plia.framework.scene.Layer;
import plia.framework.scene.Light;
import plia.framework.scene.Group;
import plia.framework.scene.Scene;
import plia.framework.scene.Terrain;
import plia.framework.scene.View;
import plia.framework.scene.group.animation.PlaybackMode;
import plia.framework.scene.view.ImageView;

public class Scene1 extends Scene
{
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();
	private Group model1, model2;
	private Terrain terrain;
	private Camera camera;
	private Light skylight;
	
	private ImageView view1;

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
		
		terrain = GameObjectManager.createTerrain("terrain/heightmap.png", 50, 400);
		terrain.setBaseTexture(GameObjectManager.loadTexture2D("terrain/diffusemap.jpg"));
		layer1.addChild(terrain);

		view1 = GameObjectManager.createImageView("btn_default.png");
		layer2.addChild(view1);
	}

	public void onUpdate()
	{
		model1.rotate(0, 0, 1);
		model2.rotate(0, 0, -0.5f);
	}

}
