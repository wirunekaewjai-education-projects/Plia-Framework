package game.test;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import plia.core.Game;
import plia.core.Screen;
import plia.core.debug.Debug;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Light;
import plia.core.scene.MeshTerrain;
import plia.core.scene.PlaneCollider;
import plia.core.scene.Scene;
import plia.core.scene.SphereCollider;
import plia.core.scene.Terrain;
import plia.core.scene.View;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Color3;
import plia.math.Vector2;
import plia.math.Vector3;

public class Game2 extends Game
{
	private Scene scene = new Scene();
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();
	
	private Camera camera;
	private Terrain map;
	private Group buffy, guard;
	
	private Button padButton;

	
	public void onInitialize(Bundle savedInstanceState)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setScene(scene);

		guard = model("woodguard.FBX");
		
		map = terrain("terrain2/heightmap32.jpg", "terrain2/diffusemap.jpg", 200, 600);
		map.setPosition(-300, -300, 0);
		
		buffy = model("buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);
		
		SphereCollider bs = collider(3);
		bs.translate(0, 0.5f, 3);
		buffy.setCollider(bs);

		
		
		Animation animation1 = buffy.getAnimation();
		animation1.play("idle");
		animation1.getAnimationClip("idle").set(35, 50, PlaybackMode.LOOP);

		camera = Scene.getMainCamera();
//		camera.setPosition(-300, 300, 200);
//		camera.setLookAt(vec3());
		camera.setPosition(0, -10, 9);
		camera.rotate(-10, 0, 0);
		camera.setRange(2500);
		camera.setSky(skydome("sky_sphere01.jpg"));

//		layer1.addChild(camera);
		buffy.addChild(camera);

		padButton = button("pad.png");
		
		float ratio = (float)Screen.getWidth() / Screen.getHeight();
		float scalef = 0.15f;
		padButton.setScale(scalef, scalef * ratio);
		padButton.setActive(false);
		padButton.setOnTouchListener(new OnTouchListener()
		{
			
			public void onTouch(Button button, int action, float x, float y)
			{
				Vector2 center = button.getCenter();
				
				float dx = center.x - x;
				float dy = center.y - y;
				
				Vector2 dir = vec2(dx, dy).getNormalized();
				
				buffy.translate(0, 0.5f * dir.y, 0);

				if(dir.x != 0.0f)
				{
					buffy.rotate(0, 0, 1.5f * dir.x);
				}
			}
		});

		layer1.addChild(map, buffy);
		layer2.addChild(padButton);
		
		scene.addLayer(layer1);
		scene.addLayer(layer2);
		
		map.attachCollider(bs);
		
		
	}
	PlaneCollider collider;

	public void onUpdate()
	{

		Debug.drawBounds(buffy.getCollider(), new Color3(0.5f, 1, 0.5f));
	}

	@Override
	public void onTouchEvent(int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_UP || action == TouchEvent.ACTION_NONE)
		{
			padButton.setActive(false);
		}
		else if(action == TouchEvent.ACTION_DOWN)
		{
			padButton.setActive(true);
			padButton.setCenter(x, y);
		}
	}
}
