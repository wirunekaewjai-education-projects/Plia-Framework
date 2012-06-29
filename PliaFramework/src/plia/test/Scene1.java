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
import plia.framework.scene.group.animation.Animation;
import plia.framework.scene.group.animation.AnimationClip;
import plia.framework.scene.group.animation.PlaybackMode;
import plia.framework.scene.view.ImageView;

public class Scene1 extends Scene
{
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();
	private Group model1;
	private Terrain terrain;
	private Camera camera;
	
	private ImageView view1;
	
	private Light backLight, keyLight, fillLight;
	private Light pointLight1, pointLight2;

	public void onInitialize()
	{
		long start = System.nanoTime();
		model1 = GameObjectManager.loadModel("buffylow.FBX");

		terrain = GameObjectManager.createTerrain("terrain/heightmap.png", 60, 500);
		terrain.setBaseTexture(GameObjectManager.loadTexture2D("terrain/diffusemap.jpg"));

		keyLight = new Light();
		keyLight.setForward(-1, -1, -1);
		keyLight.setIntensity(1);
		
		backLight = new Light();
		backLight.setIntensity(3);
		
		fillLight = new Light();
		fillLight.setForward(-1, 0, -0.45f);
		fillLight.setIntensity(1.4f);
		
		pointLight1 = new Light(Light.POINT_LIGHT, 40, 2f, 1,0,0);
		pointLight1.setPosition(-100, 0, 30);
		
		pointLight2 = new Light(Light.POINT_LIGHT, 40, 2f, 0,0,1);
		pointLight2.setPosition(100, 0, 30);

		camera = Scene.getMainCamera();
		camera.setPosition(350, 350, 100);
		camera.setLookAt(new Vector3(250, 250, 0));
		camera.setProjectionType(Camera.PERSPECTIVE);
		camera.setRange(1000);

		Animation animation1 = model1.getAnimation();
		AnimationClip idle1 = animation1.getAnimationClip("idle");
		animation1.play("idle");
		idle1.setPlaybackMode(PlaybackMode.LOOP);
		idle1.setStart(35);
		idle1.setEnd(50);
		
		model1.setPosition(250, 250, 40);
		model1.addChild(pointLight1, pointLight2);

		addLayer(layer1);
		layer1.addChild(model1, camera, keyLight, fillLight, backLight, terrain);

		view1 = GameObjectManager.createImageView("btn_default.png");
		layer2.addChild(view1);
		
		float end = (System.nanoTime() - start)/ 1000000f;
		log("Load Time : "+end+" ms");
	}

	public void onUpdate()
	{
		Vector3 camForward = camera.getForward();
		backLight.setForward(-camForward.x, -camForward.y, -camForward.z);
		
		model1.rotate(0, 0, 1);
	}
}
