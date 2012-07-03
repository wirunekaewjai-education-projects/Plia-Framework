package plia.test;

import plia.framework.debug.Debug;
import plia.framework.event.OnTouchListener;
import plia.framework.event.TouchEvent;
import plia.framework.math.Vector3;
import plia.framework.scene.BoundingPlane;
import plia.framework.scene.BoundingSphere;
import plia.framework.scene.Bounds;
import plia.framework.scene.Camera;
import plia.framework.scene.Layer;
import plia.framework.scene.Light;
import plia.framework.scene.Group;
import plia.framework.scene.Scene;
import plia.framework.scene.Terrain;
import plia.framework.scene.View;
import plia.framework.scene.group.animation.Animation;
import plia.framework.scene.group.animation.PlaybackMode;
import plia.framework.scene.group.shading.Color3;
import plia.framework.scene.group.shading.NormalMap;
import plia.framework.scene.view.Button;

public class Scene1 extends Scene implements OnTouchListener
{
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();
	private Group model1, model2, model3, model4, model5, model6, model7;
	private Terrain terrain;
	private Camera camera;
	
	private Button view1, view2;
	
	private Light backLight, keyLight, fillLight;
	private Light pointLight1, pointLight2;
	
	private Button screenButton, hierarchy;

	public void onInitialize()
	{
		long start = System.nanoTime();
		model1 = model("buffylow.FBX");

		terrain = terrain("terrain/heightmap.png", "terrain/diffusemap.jpg", 60, 1200);
		terrain.setPosition(-600, -600, 0);

		keyLight = directionalLight(-1, -1, -1, 0.2f, 1, 1);
		backLight = directionalLight(3);
		fillLight = directionalLight(-1, 0, -0.45f, 1.4f);

		pointLight1 = pointLight(-100, 0, 30, 40, 2, 1, 0, 0);
		pointLight2 = pointLight(100, 0, 30, 40, 2, 0, 0, 1);

		camera = camera(Camera.PERSPECTIVE, 350, 350, 100, 250, 250, 50, 600);
		camera.setSky(skydome("sky_sphere01.jpg"));
		Scene.setMainCamera(camera);

		Animation animation1 = model1.getAnimation();
		animation1.play("idle");
		animation1.getAnimationClip("idle").set(35, 50, PlaybackMode.LOOP);

		model2 = model1.instantiate();
		model3 = model1.instantiate();
		model4 = model1.instantiate();
		model5 = model1.instantiate();
		model6 = model1.instantiate();
		model7 = model1.instantiate();
		
		model1.setPosition(250, 250, 40);
		model1.addChild(pointLight1, pointLight2);
		
		BoundingSphere p = new BoundingSphere(30);
//		p.setScale(50, 50, 0);
//		p.setForward(0, 1, 0);
		
		BoundingPlane p2 = new BoundingPlane();
		p2.setScale(50, 50, 0);
		p2.setForward(0, 1, 0);
		
		BoundingSphere bs = new BoundingSphere(30);
		bs.translate(0, 5, 20);
		
		model1.setBounds(p);
		model2.setBounds(p2);
		
		model3.setBounds(bs);
		
		model2.setPosition(300, 250, 40);
		model3.setPosition(200, 250, 40);
		
		model4.setPosition(350, 250, 40);
		model5.setPosition(350, 200, 40);
		model6.setPosition(400, 200, 40);
		model7.setPosition(400, 250, 40);

		view1 = button("sprite3.png", 12);
		view1.setName("Human");
		view1.setPosition(0.25f, 0.5f);
		view1.setScale(0.25f, 0.25f);
		view1.setOnTouchListener(this);

		Animation animation2 = view1.getAnimation();
		animation2.play("idle");
		animation2.setFrameRate(12);
		animation2.getAnimationClip("idle").setPlaybackMode(PlaybackMode.LOOP);
		
		view2 = view1.instantiate();
		view2.setPosition(0, 0);
		view2.getAnimation().stop();
		view2.setOnTouchListener(this);
		
		screenButton = button();
		hierarchy = button();
		
		screenButton.addChild(hierarchy);
		hierarchy.addChild(view1, view2);

		screenButton.setOnTouchListener(this);
		
		hierarchy.setActive(false);

		layer1.addChild(model1, model2, model3, model4, model5, model6, model7, camera, keyLight, fillLight, backLight, terrain);
		layer2.addChild(screenButton);

		addLayer(layer1);
		addLayer(layer2);
		
		float end = (System.nanoTime() - start)/ 1000000f;
		log("Load Time : "+end+" ms");
	}

	public void onUpdate()
	{
//		camera.rotate(0, 0, 0.25f);
//		camera.translate(0, 0.25f, 0);
		
		Vector3 camForward = camera.getForward();
		backLight.setForward(-camForward.x, -camForward.y, -camForward.z);
		
		model1.rotate(0, 0, 1);
		model3.rotate(1, 0, 1);
		model5.rotate(0, 1, 1);
		model6.rotate(0, 1, 0);
		
//		Debug.drawLine(model1.getPosition(), vec3(250, 250, 100), new Color3(0.5f, 1, 0.5f));
		
//		Debug.drawBounds(model1.getBounds(), new Color3(0.5f, 1, 0.5f));
//		Debug.drawBounds(model2.getBounds(), new Color3(0.5f, 1, 0.5f));
//		Debug.drawBounds(model3.getBounds(), new Color3(0.5f, 1, 0.5f));
//		
//		if(Bounds.intersect(model1.getBounds(), model3.getBounds()))
//		{
////			log("Intersected");
//		}
	}

	public void onTouch(Button button, int action, float x, float y)
	{
		if(button == screenButton)
		{
			if(action == TouchEvent.ACTION_UP)
			{
				hierarchy.setActive(false);
			}
			else 
				hierarchy.setActive(true);
		}
		
		if(button == view1)
		{
			button.setCenter(x, y);
			
			if(action == TouchEvent.ACTION_UP)
			{
				view2.getAnimation().play("idle");
			}
		}

		if(button == view2)
		{
			model2.rotate(1, 0, -1);
		}
	}
}
