package wingkwai.main;

import java.util.ArrayList;

import plia.core.Screen;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Camera;
import plia.core.scene.Collider;
import plia.core.scene.CurveCollider;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Light;
import plia.core.scene.Scene;
import plia.core.scene.SphereCollider;
import plia.core.scene.Sprite;
import plia.core.scene.Terrain;
import plia.core.scene.View;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Material;
import plia.core.scene.shading.Shader;
import plia.core.scene.shading.Texture2D;
import plia.math.Vector2;
import plia.math.Vector3;

public class RaceScene extends Scene
{
	private Layer<Group> layer1;
	private Layer<View> layer2;
	
	// Light
	private Light light1;
	private Light light2;
	private Light light3;
	
	// Shadow
	private Group shadowPlaneRef;
	private ArrayList<ShadowPlane> shadowPlanes;
	
	// Viewport
	private Camera camera;
	
	// Character
	private Group buffy_statue;
	private Group terrain1;
	private Group buffy;
	private Texture2D buffyDif;
	private Texture2D berserkerDif;
	
	// Terrain
	private Terrain terrain;
	
	// UI
	private Button controller;
	
	// //
	private Group itemBox;
	// //
	
	// Vehicle
	private Vehicle vehicle;
	
	// Race Track
	private CurveCollider trackOutside, trackInside;
	
	// Checkpoint
	private Checkpoint checkpoint;

	
	// Item DB
	private ArrayList<Item> items;
	private ArrayList<Group> itemBoxes;
	
	// Waypoint
	private ArrayList<Vector3> waypoints;
	
	// AI
	private ArrayList<AIScript> aiScripts;
	
	// Player
	private ArrayList<Player> players;
	
	//
	private boolean isStarted = false;
	private boolean isEnd = false;
	
	private Sprite endSprite;
	
	private int aiCount = 3;
	
	@Override
	public void onInitialize()
	{
		layer1 = new Layer<Group>();
		layer2 = new Layer<View>();
		
		light1 = directionalLight(0, 0, -1, 1);
		light2 = directionalLight(0.707f, 0.707f, 0, 0.5f);
		light3 = directionalLight(0, -0.707f, 0.707f, 0.5f);
		
		shadowPlanes = new ArrayList<ShadowPlane>();
		camera = new Camera("Main Camera");
		
		checkpoint = new Checkpoint();
		
		items = new ArrayList<Item>();
		itemBoxes = new ArrayList<Group>();

		waypoints = new ArrayList<Vector3>();
		aiScripts = new ArrayList<AIScript>();
		players = new ArrayList<Player>();
		
		loadContent();
		init();
		initItem();
	}
	
	
	private void loadContent()
	{
		terrain = terrain("terrain/heightmap.bmp", "terrain/diffusemap.jpg", 400, 2000);
		buffy = model("model/player/buffylow.FBX");
		terrain1 = model("model/terrain/terrain.FBX");
		
		itemBox = model("model/item/itembox.FBX");
		itemBox.asModel().getMaterial().setLightAbsorbMultipler(12);
		
		shadowPlaneRef = model("model/shadow/shadow_plane.FBX");
		
		controller = button("ui/controller.png");
	}

	private void init()
	{
		terrain.setName("Terrain");
		buffy.setName("Buffy");
		itemBox.setName("ItemBox");
		
		buffy_statue = buffy.instantiate();
		buffy_statue.setScale(30, 30, 30);
		buffy_statue.setPosition(0, -200, 0);

		// Translate Terrain Center to (0, 0)
		terrain.setPosition(-1000, -1000, 0);

//		terrain1.rotate(0, 0, 180);
		
		// Waypoint
		waypoints.add(vec3(270, -790, 23.899f));
		waypoints.add(vec3(270, -832, 23.899f));
		waypoints.add(vec3(270, -874, 23.899f));
		waypoints.add(vec3(270, -916, 23.899f));
		//

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
		float radius = 3;
		
		Texture2D shadowTex = tex2D("model/shadow/shadow_tex.png");
		shadowTex.setEnabledAlpha(true);
		shadowPlaneRef.asModel().getMaterial().setShader(Shader.AMBIENT);
		shadowPlaneRef.asModel().getMaterial().setBaseTexture(shadowTex);
		shadowPlaneRef.setScale(4, 4, 4);
		shadowPlanes.add(new ShadowPlane(shadowPlaneRef.instantiate(), buffy, 24));
		
		buffy.setScale(0.1f, 0.1f, 0.1f);
		buffy.setPosition(waypoints.get(0));
		buffy.setForward(-1, 0, 0);

		berserkerDif = tex2D("model/player/superBuffy.jpg");
		buffyDif = buffy.asModel().getMaterial().getBaseTexture();
		
		Material bsm = new Material();
		bsm.setBaseColor(1, 1, 1);
		bsm.setLightAbsorbMultipler(30);
		bsm.setShader(Shader.DIFFUSE);
		
		buffy_statue.asModel().setMaterial(bsm);
		buffy.asModel().getMaterial().setLightAbsorbMultipler(0.25f);
		
		// Create Collider for Buffy
		SphereCollider buffyCollider = collider(radius);
		buffyCollider.translate(0, 0.5f, 2);
		
		buffy.setCollider(buffyCollider);
		
		vehicle = new Vehicle(buffy);

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
		
		// Item
		itemBox.setCollider(collider(7));
		itemBox.setScale(10, 10, 10);
//		itemBox.setPosition(0, 15, 144);
//		itemBox.rotate(10, 10, 0);
		//

		Vector2[] outside = new Vector2[31];
		outside[0] = new Vector2(171, -961);
		outside[1] = new Vector2(-300, -954);
		outside[2] = new Vector2(-574, -916);
		outside[3] = new Vector2(-743, -811);
		outside[4] = new Vector2(-824, -638);
		outside[5] = new Vector2(-823, -296);
		
		outside[6] = new Vector2(-835, -22);
		outside[7] = new Vector2(-891, 271);
		outside[8] = new Vector2(-928, 527);
		outside[9] = new Vector2(-877, 709);
		outside[10] = new Vector2(-727, 812);
		
		outside[11] = new Vector2(-514, 843);
		outside[12] = new Vector2(-76, 877);
		outside[13] = new Vector2(88, 862);
		outside[14] = new Vector2(172, 824);
		outside[15] = new Vector2(249, 693);
		
		outside[16] = new Vector2(245, 524);
		outside[17] = new Vector2(261, 478);
		outside[18] = new Vector2(388, 431);
		outside[19] = new Vector2(523, 383);
		outside[20] = new Vector2(713, 233);
		
		outside[21] = new Vector2(829, 60);
		outside[22] = new Vector2(948, -254);
		outside[23] = new Vector2(1004, -511);
		outside[24] = new Vector2(991, -661);
		outside[25] = new Vector2(854, -812);
		
		outside[26] = new Vector2(642, -913);
		outside[27] = new Vector2(353, -962);

		outside[28] = new Vector2(171, -961);
		outside[29] = new Vector2(-300, -954);
		outside[30] = new Vector2(-574, -916);
		
		Vector2[] inside = new Vector2[47];
		inside[0] = new Vector2(166, -725);
		inside[1] = new Vector2(361, -723);
		inside[2] = new Vector2(408, -723);
		inside[3] = new Vector2(486, -710);
		inside[4] = new Vector2(539, -693);
		inside[5] = new Vector2(607, -667);
		inside[6] = new Vector2(680, -624);
		inside[7] = new Vector2(737, -558);
		inside[8] = new Vector2(745, -471);
		inside[9] = new Vector2(726, -360);
		inside[10] = new Vector2(710, -311);
		inside[11] = new Vector2(632, -90);
		inside[12] = new Vector2(600, -20);
		inside[13] = new Vector2(550, 54);
		inside[14] = new Vector2(461, 140);
		inside[15] = new Vector2(352, 190);
		inside[16] = new Vector2(240, 225);
		inside[17] = new Vector2(113, 272);
		inside[18] = new Vector2(37, 361);
		inside[19] = new Vector2(16, 437);
		inside[20] = new Vector2(11, 493);
		inside[21] = new Vector2(-1, 579);
		inside[22] = new Vector2(-9, 597);
		inside[23] = new Vector2(-40, 630);
		inside[24] = new Vector2(-69, 641);
		inside[25] = new Vector2(-74, 639);
		inside[26] = new Vector2(-136, 638);
		inside[27] = new Vector2(-335, 616);
		inside[28] = new Vector2(-551, 596);
		inside[29] = new Vector2(-639, 573);
		inside[30] = new Vector2(-675, 541);
		inside[31] = new Vector2(-680, 532);
		inside[32] = new Vector2(-682, 494);
		inside[33] = new Vector2(-656, 316);
		inside[34] = new Vector2(-604, 88);
		inside[35] = new Vector2(-604, -176);
		inside[36] = new Vector2(-600, -490);
		inside[37] = new Vector2(-595, -540);
		inside[38] = new Vector2(-580, -572);
		inside[39] = new Vector2(-561, -609);
		inside[40] = new Vector2(-550, -624);
		inside[41] = new Vector2(-498, -678);
		inside[42] = new Vector2(-441, -701);
		inside[43] = new Vector2(-277, -723);
		
		inside[44] = new Vector2(166, -725);
		inside[45] = new Vector2(361, -723);
		inside[46] = new Vector2(408, -723);

		
		trackOutside = CurveCollider.bSplineCurveCollider(0.25f, 100, false, outside);
		trackOutside.attachCollider(buffyCollider);
		
		trackInside = CurveCollider.bSplineCurveCollider(0.25f, 100, false, inside);
		trackInside.attachCollider(buffyCollider);
		
		checkpoint.add(collider(-1, 0, 0, 				100, 300, 	250, -834, 27));
		checkpoint.add(collider(-1, 0, 0, 				100, 300, 	185, -834, 27));
		checkpoint.add(collider(-1, 0, 0, 				100, 300, 	-322, -840, 27));
		checkpoint.add(collider(-0.815f, 0.579f, 0, 	100, 300, 	-600, -765, 27));
		checkpoint.add(collider(-0.124f, 0.992f, 0, 	100, 300, 	-700, -340, 27));
		checkpoint.add(collider(-0.191f, 0.982f, 0, 	100, 300, 	-735, 100, 27));
		checkpoint.add(collider(0.187f, 0.982f, 0, 		100, 300, 	-800, 528, 27));
		checkpoint.add(collider(0.985f, 0.175f, 0, 		100, 300, 	-560, 716, 27));
		checkpoint.add(collider(0.974f, -0.225f, 0, 	100, 300, 	-80, 750, 27));
		checkpoint.add(collider(0.162f, -0.987f, 0, 	100, 300, 	90, 600, 27));
		checkpoint.add(collider(0.582f, -0.813f, 0, 	100, 300, 	140, 440, 27));
		
		checkpoint.add(collider(0.928f, -0.373f, 0, 	100, 300, 	385, 290, 27));
		checkpoint.add(collider(0.644f, -0.765f, 0, 	100, 300, 	617, 128, 27));
		checkpoint.add(collider(0.159f, -0.987f, 0, 	100, 300, 	850, -438, 27));
		checkpoint.add(collider(-0.532f, -0.847f, 0, 	100, 300, 	787, -668, 27));
		
		checkpoint.add(collider(-0.938f, -0.346f, 0, 	100, 300, 	527, -800, 27));
		checkpoint.add(collider(-1, 0, 0, 				100, 300, 	433, -834, 27));
		
		players.add(new Player(vehicle));
		AIScript aiScript1 = new AIScript(vehicle, checkpoint);
		aiScripts.add(aiScript1);

		for (int i = 1; i < aiCount+1; i++)
		{
			Group buffyClone = buffy.instantiate();
			Group shadowClone = shadowPlaneRef.instantiate();
			
			buffyClone.setPosition(waypoints.get(i));
			
			Vehicle vehicleClone = new Vehicle(buffyClone);
			players.add(new Player(vehicleClone));
			
			ShadowPlane shadowPlane = new ShadowPlane(shadowClone, buffyClone, 24);
			AIScript aiScript = new AIScript(vehicleClone, checkpoint);
			
			shadowPlanes.add(shadowPlane);
			aiScripts.add(aiScript);
			
			layer1.addChild(buffyClone);
			
			trackInside.attachCollider(buffyClone.getCollider());
			trackOutside.attachCollider(buffyClone.getCollider());
		}
		
		for (AIScript aiScript : aiScripts)
		{
//			aiScript.addObjectAvoidance(buffyCollider);
			for (AIScript aiScript2 : aiScripts)
			{
				if(aiScript != aiScript2)
				{
					aiScript.addObjectAvoidance((SphereCollider) aiScript2.getVehicle().getObject().getCollider());
				}
			}
		}
		
		for (int i = 0; i < players.size(); i++)
		{
			checkpoint.addPlayer(players.get(i));
		}
		
		endSprite = sprite("ui/goal.jpg");
		endSprite.setScale(0.25f, 0.25f);
		
		layer1.addChild(light1, light2, light3, buffy_statue, terrain1, buffy, trackOutside, trackInside);
		layer2.addChild(controller);
		
		for (int i = 0; i < checkpoint.size(); i++)
		{
			layer1.addChild(checkpoint.get(i));
		}
		
		for (int i = 0; i < shadowPlanes.size(); i++)
		{
			layer1.addChild(shadowPlanes.get(i).getPlane());
		}
		
		addLayer(layer1);
		addLayer(layer2);
	}
	
	private void initItem()
	{
		// Init Default ItemBox Location
		for (int i = 0; i < 5; i++)
		{
			Group box = itemBox.instantiate();
			layer1.addChild(box);
			itemBoxes.add(box);
		}

		itemBoxes.get(0).setPosition(-322, -740, 26.5f);
		itemBoxes.get(1).setPosition(-322, -780, 26.5f);
		itemBoxes.get(2).setPosition(-322, -820, 26.5f);
		itemBoxes.get(3).setPosition(-322, -860, 26.5f);
		itemBoxes.get(4).setPosition(-322, -900, 26.5f);
		
		
		// Init Item DB
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
		
//		Game.enabledDebug = true;
	}
	
	@Override
	protected void onUpdate()
	{
		if(!isStarted)
		{
			isStarted = true;
		}
		else
		{
			checkpoint.update();
			
			for (Player player : players)
			{
				player.update();
			}
			
			for (int i = 0; i < shadowPlanes.size(); i++)
			{
				shadowPlanes.get(i).update();
			}
			
			if(!isEnd)
			{
				for (AIScript aiScript : aiScripts)
				{
					aiScript.update();
				}

				//checkpoint.isEnd() || 
				if(players.get(0).isEnd())
				{
					layer2.addChild(endSprite);
					isEnd = true;
				}
			}
			
			for (Group itemb : itemBoxes)
			{
				itemb.rotate(0, 0, 1);
				
				if(itemb.isActive())
				{
					for (Player player : players)
					{
						if(Collider.intersect(itemb.getCollider(), player.getVehicle().getObject().getCollider()))
						{
							itemb.setActive(false);
							player.setItem(items.get(0));
							player.useItem();
						}
					}
					
//					Log.e("III", itemb+"");
				}
			}
		}
	}
	
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
	public void onDestroy()
	{
		super.onDestroy();
		items.clear();
	}
}
