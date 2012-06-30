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
		model1 = model("buffylow.FBX");

		terrain = terrain("terrain/heightmap.png", "terrain/diffusemap.jpg", 60, 500);

		keyLight = directionalLight(-1, -1, -1);
		backLight = directionalLight(3);
		fillLight = directionalLight(-1, 0, -0.45f, 1.4f);

		pointLight1 = pointLight(-100, 0, 30, 40, 2, 1, 0, 0);
		pointLight2 = pointLight(100, 0, 30, 40, 2, 0, 0, 1);

		camera = camera(Camera.PERSPECTIVE, 350, 350, 100, 250, 250, 0, 1000);
		Scene.setMainCamera(camera);

		Animation animation1 = model1.getAnimation();
		AnimationClip idle1 = animation1.getAnimationClip("idle");
		animation1.play("idle");
		idle1.setPlaybackMode(PlaybackMode.LOOP);
		idle1.setStart(35);
		idle1.setEnd(50);

		model1.setPosition(250, 250, 40);
		model1.addChild(pointLight1, pointLight2);

		layer1.addChild(model1, camera, keyLight, fillLight, backLight, terrain);

		view1 = imageView("diffuse.jpg");
		layer2.addChild(view1);

		addLayer(layer1);
		addLayer(layer2);
		
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
