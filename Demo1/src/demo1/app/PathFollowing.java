package demo1.app;

import java.util.ArrayList;

import demo1.main.Grid;
import plia.core.Game;
import plia.core.GameObjectManager;
import plia.core.debug.Debug;
import plia.core.scene.CurveCollider;
import plia.core.scene.Group;
import plia.core.scene.SphereCollider;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Color3;
import plia.core.scene.shading.Shader;
import plia.math.Vector2;
import plia.math.Vector3;
import wingkwai.core.AIScript;
import wingkwai.core.Checkpoint;
import wingkwai.core.Player;
import wingkwai.core.Vehicle;

public class PathFollowing extends BaseApplication
{
	private Group buffy;
	
	// Checkpoint
	private Checkpoint checkpoint;
	
	// Waypoint
	private ArrayList<Vector3> waypoints;
	
	// AI
	private ArrayList<AIScript> aiScripts;
	
	// Player
	private ArrayList<Player> players;
	
	// Race Track
	private CurveCollider trackOutside, trackInside;

	public PathFollowing()
	{
		super("Path Following");
		camera.setPosition(0, -12, 8);
		camera.setEulerAngles(0, 0, 0);
		camera.setLookAt(new Vector3());
		camera.setRange(2000);
		
		buffy = GameObjectManager.loadModel("model/buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);
		buffy.asModel().getMaterial().setShader(Shader.AMBIENT);

		SphereCollider collider = Game.collider(2);
		collider.translate(0, 0.5f, 1);
		
		buffy.setCollider(collider);
		
		Animation buffyAnimation = buffy.getAnimation();
		buffyAnimation.getAnimationClip("idle").set(0, 30, PlaybackMode.LOOP);
		buffyAnimation.addAnimationClip("run", 35, 50, PlaybackMode.LOOP);
		buffyAnimation.play("run");

		checkpoint = new Checkpoint();
		checkpoint.add(Game.collider(-1, 0, 0, 				100, 300, 	250, -834, 27));
		checkpoint.add(Game.collider(-1, 0, 0, 				100, 300, 	185, -834, 27));
		checkpoint.add(Game.collider(-1, 0, 0, 				100, 300, 	-322, -840, 27));
		checkpoint.add(Game.collider(-0.815f, 0.579f, 0, 	100, 300, 	-600, -765, 27));
		checkpoint.add(Game.collider(-0.124f, 0.992f, 0, 	100, 300, 	-700, -340, 27));
		checkpoint.add(Game.collider(-0.191f, 0.982f, 0, 	100, 300, 	-735, 100, 27));
		checkpoint.add(Game.collider(0.187f, 0.982f, 0, 		100, 300, 	-800, 528, 27));
		checkpoint.add(Game.collider(0.985f, 0.175f, 0, 		100, 300, 	-560, 716, 27));
		checkpoint.add(Game.collider(0.974f, -0.225f, 0, 	100, 300, 	-80, 750, 27));
		checkpoint.add(Game.collider(0.162f, -0.987f, 0, 	100, 300, 	90, 600, 27));
		checkpoint.add(Game.collider(0.582f, -0.813f, 0, 	100, 300, 	140, 440, 27));
		
		checkpoint.add(Game.collider(0.928f, -0.373f, 0, 	100, 300, 	385, 290, 27));
		checkpoint.add(Game.collider(0.644f, -0.765f, 0, 	100, 300, 	617, 128, 27));
		checkpoint.add(Game.collider(0.159f, -0.987f, 0, 	100, 300, 	850, -438, 27));
		checkpoint.add(Game.collider(-0.532f, -0.847f, 0, 	100, 300, 	787, -668, 27));
		
		checkpoint.add(Game.collider(-0.938f, -0.346f, 0, 	100, 300, 	527, -800, 27));
		checkpoint.add(Game.collider(-1, 0, 0, 				100, 300, 	433, -834, 27));
		checkpoint.setLab(1000000);
		
		init();
		
		waypoints = new ArrayList<Vector3>();
		aiScripts = new ArrayList<AIScript>();
		players = new ArrayList<Player>();
		
		// Waypoint
		for (int i = 0; i < 4; i++)
		{
			waypoints.add(new Vector3(270, -790 - (42*i), 23.899f));
		}

		for (int i = 0; i < 1; i++)
		{
			Group buffyClone = buffy.instantiate();
			buffyClone.setPosition(waypoints.get(i));
			buffyClone.setForward(-1, 0, 0);
			
			if(i == 0)
			{
				buffyClone.addChild(camera);
			}
			
			Vehicle vehicleClone = new Vehicle(buffyClone);
			Player pclone = new Player(vehicleClone);
			players.add(pclone);
			
			AIScript aiScript = new AIScript(pclone, checkpoint);
			
			aiScripts.add(aiScript);
			
			_3DLayer.addChild(buffyClone);
			
			trackInside.attachCollider(buffyClone.getCollider());
			trackOutside.attachCollider(buffyClone.getCollider());
		}
		
		for (AIScript aiScript : aiScripts)
		{
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

		
		for (int i = 0; i < checkpoint.size(); i++)
		{
			_3DLayer.addChild(checkpoint.get(i));
		}
		
		_3DLayer.addChild(trackOutside, trackInside);
	}
	
	private void init()
	{
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
		trackInside = CurveCollider.bSplineCurveCollider(0.25f, 100, false, inside);
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		super.resume();
		
//		for (int i = 0; i < players.size(); i++)
//		{
//			players.get(i).getVehicle().getObject().setPosition(waypoints.get(i));
//			players.get(i).getVehicle().getObject().setForward(-1, 0, 0);
//			players.get(i).setLab(1);
//			players.get(i).setCheckpointCount(0);
//			players.get(i).setCurrentCheckpoint(0);
//		}
	}
	
	public void update()
	{
		Game.enabledDebug = true;
		for (int i = 0; i < checkpoint.size(); i++)
		{
			Debug.drawBounds(checkpoint.get(i), new Color3(0, 1, 0));
		}
		
//		Debug.drawBounds(buffy.getCollider(), new Color3(1, 0, 0));
		
		Grid.draw();
		checkpoint.update();
		
		for (Player player : players)
		{
			player.update();
		}
		
		for (AIScript aiScript : aiScripts)
		{
			aiScript.update();
		}
		
		
//		Log.e("Position", buffy.getPosition().toString());
	}

}
