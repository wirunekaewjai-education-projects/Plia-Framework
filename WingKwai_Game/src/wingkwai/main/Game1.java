package wingkwai.main;

import java.util.ArrayList;

import plia.core.Game;
import plia.core.Screen;
import plia.core.debug.Debug;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.*;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Color3;
import plia.core.scene.shading.Texture2D;
import plia.math.Vector2;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Game1 extends Game
{
	private Scene scene = new Scene();
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();
	
	// Viewport
	private Camera camera = new Camera("Main Camera");
	
	// Character
	private Group buffy;
	
	// Terrain
	private Terrain terrain;
	
	// UI
	private Button controller;
	
	// Vehicle CTRL
	private Vehicle vehicle;
	
	// Race Track
	private CurveCollider trackOutside, trackInside;
	
	// Checkpoint
	private Checkpoint checkpoint = new Checkpoint();
	
	private Texture2D buffyDif;
	private Texture2D berserkerDif;
	
	private int currentCheckpoint = 0;
	private int checkpointCount = 0;
	
	private boolean isEnd = false;
	
	private Sprite endSprite;
	
	// Item DB
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public void onInitialize(Bundle arg0)
	{
		Log.e("Call", "OnInit");
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setScene(scene);
		
		loadContent();
		init();
		initItemDB();
	}
	
	private void loadContent()
	{
		terrain = terrain("terrain/heightmap.bmp", "terrain/diffusemap.jpg", 400, 2000);
		buffy = model("model/buffylow.FBX");
		
		controller = button("ui/controller.png");
	}

	private void init()
	{
		terrain.setName("Terrain");
		buffy.setName("Buffy");
		
		// Translate Terrain Center to (0, 0)
		terrain.setPosition(-1000, -1000, 0);

		// Set Buffy's Animation Clip
		Animation buffyAnimation = buffy.getAnimation();
		buffyAnimation.getAnimationClip("idle").set(0, 30, PlaybackMode.LOOP);
		buffyAnimation.addAnimationClip("run", 35, 50, PlaybackMode.LOOP);
		buffyAnimation.play("idle");
		
		// Setup Camera Position and Set Sky Dome
		camera.setPosition(0, -10, 9);
		camera.rotate(-10, 0, 0);
		camera.setRange(2500);
		camera.setSky(skydome("sky/sky_sphere01.jpg"));
		
		// Camera Follow Buffy
		buffy.addChild(camera);
		
		// Set Scale Buffy
		buffy.setScale(0.1f, 0.1f, 0.1f);
		
		berserkerDif = tex2D("model/superBuffy.jpg");
		buffyDif = buffy.asModel().getMaterial().getBaseTexture();
		
		vehicle = new Vehicle(buffy);
		
		// Create Collider for Buffy
		SphereCollider buffyCollider = vehicle.getCollider();
		buffyCollider.setRadius(3);
		buffyCollider.translate(0, 0.5f, 2);

		terrain.attachCollider(buffyCollider);

		float ratio = (float)Screen.getWidth() / Screen.getHeight();
		float scalef = 0.2f;
		controller.setScale(scalef, scalef * ratio);
		controller.setActive(false);
		controller.setOnTouchListener(new OnTouchListener()
		{
			
			public void onTouch(Button button, int action, float x, float y)
			{
				if(!isEnd)
				{
					Vector2 center = button.getCenter();
					
					float dx = center.x - x;
					float dy = center.y - y;
					
					Vector2 dir = vec2(dx, dy).getNormalized();

					if(dir.y != 0.0f && !Float.isNaN(dir.y))
					{
						vehicle.accelerate(0.03f * dir.y);
					}

					if(dir.x != 0.0f && !Float.isNaN(dir.x))
					{
						vehicle.turn(dir.x);
					}
				}
			}
		});
		
		Scene.setMainCamera(camera);
		
		Vector2[] outside = new Vector2[14];
		outside[0] = new Vector2(514, 190);
		outside[1] = new Vector2(791, 10);
		outside[2] = new Vector2(800, -172);
		outside[3] = new Vector2(621, -409);
		outside[4] = new Vector2(80, -658);
		outside[5] = new Vector2(-218, -631);
		outside[6] = new Vector2(-569, -427);
		outside[7] = new Vector2(-815, -158);
		outside[8] = new Vector2(-780, 95);
		outside[9] = new Vector2(-617, 188);
		outside[10] = new Vector2(317, 209);
		outside[11] = new Vector2(514, 190);
		outside[12] = new Vector2(791, 10);
		outside[13] = new Vector2(800, -172);
		
		Vector2[] inside = new Vector2[20];
		inside[0] = new Vector2(0, 0);
		inside[1] = new Vector2(-370, 2.7f);
		inside[2] = new Vector2(-504, -2.7f);
		inside[3] = new Vector2(-614, -110);
		inside[4] = new Vector2(-607, -191);
		inside[5] = new Vector2(-551, -288);
		inside[6] = new Vector2(-381, -374);
		inside[7] = new Vector2(-143, -485);
		inside[8] = new Vector2(-89, -483);
		inside[9] = new Vector2(110, -474);
		inside[10] = new Vector2(265, -364);
		inside[11] = new Vector2(512, -290);
		inside[12] = new Vector2(604, -183);
		inside[13] = new Vector2(604, -38);
		inside[14] = new Vector2(554, 20);
		inside[15] = new Vector2(435, 43);
		inside[16] = new Vector2(278, 36);
		inside[17] = new Vector2(0, 0);
		inside[18] = new Vector2(-370, 2.7f);
		inside[19] = new Vector2(-504, -2.7f);
		
		trackOutside = CurveCollider.bSplineCurveCollider(0.25f, 190, false, outside);
		trackOutside.attachCollider(buffyCollider);
		
		trackInside = CurveCollider.bSplineCurveCollider(0.25f, 190, false, inside);
		trackInside.attachCollider(buffyCollider);
		
		checkpoint.add(collider(0.99f, 0.01f, 0, 250, 100, 	134, 106, 140));
		checkpoint.add(collider(0.88f, -0.473f, 0, 250, 100, 	551, 90, 140));

		checkpoint.add(collider(0.23f, -0.973f, 0, 250, 100, 	683, -55, 140));
		checkpoint.add(collider(-0.836f, -0.548f, 0, 250, 100, 591, -327, 140));
		
		checkpoint.add(collider(-0.956f, -0.293f, 0, 250, 100, 68, -574, 140));
		checkpoint.add(collider(-0.852f, 0.524f, 0, 250, 100, 	-277, -521, 140));
		
		checkpoint.add(collider(-0.319f, 0.948f, 0, 250, 100, 	-657, -236, 140));
		checkpoint.add(collider(0.82f, 0.572f, 0, 250, 100, 	-652, 8, 140));
		
		checkpoint.add(collider(0.99f, 0.948f, 0, 250, 100, 	-404, 95, 140));
		checkpoint.add(collider(0.951f, 0.31f, 0, 250, 100, 	-71, 96, 140));
		
		endSprite = sprite("ui/goal.jpg");
		endSprite.setScale(0.25f, 0.25f);
		
		layer1.addChild(terrain, buffy, trackOutside, trackInside);
		layer2.addChild(controller);
		
		for (int i = 0; i < checkpoint.size(); i++)
		{
			layer1.addChild(checkpoint.get(i));
		}
		
		scene.addLayer(layer1);
		scene.addLayer(layer2);
	}
	
	private void initItemDB()
	{
		Item berserker = new Item("Berserker", 1.2f, 1.2f, -1, 0, 5, new OnItemEventListener()
		{
			
			public void onEffectStart(Player player)
			{
				player.getVehicle().getObject().asModel().getMaterial().setBaseTexture(berserkerDif);
			}
			
			public void onEffectEnd(Player player)
			{
				player.getVehicle().getObject().asModel().getMaterial().setBaseTexture(buffyDif);
			}
		});
		
		items.add(berserker);
	}

	public void onUpdate()
	{
		vehicle.update();

		PlaneCollider chp = checkpoint.get(currentCheckpoint);
		SphereCollider spr = vehicle.getCollider();
		
		if(Collider.intersect(chp, spr))
		{
			currentCheckpoint++;
			checkpointCount++;
			
			if(currentCheckpoint >= checkpoint.size())
			{
				currentCheckpoint -= checkpoint.size();
			}
			
			if(checkpointCount > checkpoint.size())
			{
				if(!isEnd)
				{
					layer2.addChild(endSprite);
					
					isEnd = true;
				}
			}
		}
		
		//
		debuging();
	}
	
	private void debuging()
	{
		Log.println(Log.ASSERT, buffy.getCollider().getForward().toString(), buffy.getCollider().getPosition().toString());
		
		for (int i = 0; i < checkpoint.size(); i++)
		{
			Debug.drawBounds(checkpoint.get(i), new Color3(0.5f, 1, 0.5f));
		}
		
		Debug.drawBounds(buffy.getCollider(), new Color3(0.5f, 1, 0.5f));
	}
	
	PlaneCollider collider;

	@Override
	public void onTouchEvent(int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_UP || action == TouchEvent.ACTION_NONE)
		{
			controller.setActive(false);
		}
		else if(action == TouchEvent.ACTION_DOWN)
		{
			controller.setActive(true);
			controller.setCenter(x, y);
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		items.clear();
	}
}