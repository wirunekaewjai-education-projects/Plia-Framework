package demo1.app;

import demo1.main.Grid;
import plia.core.Game;
import plia.core.GameObjectManager;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Group;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Shader;
import plia.math.Vector3;

public class FBX extends BaseApplication implements OnTouchListener
{
	private Group buffy;
	private Button idle;
	private Button run;

	public FBX()
	{
		super("FBX File Format");
		camera.setPosition(10, 10, 5);
		camera.setEulerAngles(0, 0, 0);
		camera.setLookAt(new Vector3());
		camera.setRange(1000);
		
		buffy = GameObjectManager.loadModel("model/buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);
		buffy.asModel().getMaterial().setShader(Shader.AMBIENT);
		
		Animation buffyAnimation = buffy.getAnimation();
		buffyAnimation.getAnimationClip("idle").set(0, 30, PlaybackMode.LOOP);
		buffyAnimation.addAnimationClip("run", 35, 50, PlaybackMode.LOOP);
		buffyAnimation.play("idle");
		
		idle = Game.button("ui/idle.png");
		idle.setScale(0.25f, 0.1f);
		idle.setPosition(0.05f, 0.895f);
		idle.setOnTouchListener(this);
		
		run = Game.button("ui/run.png");
		run.setScale(0.25f, 0.1f);
		run.setPosition(0.35f, 0.895f);
		run.setOnTouchListener(this);
		
		_2DLayer.addChild(idle, run);
		_3DLayer.addChild(camera, buffy);
	}

	public void update()
	{
		camera.rotate(0, 0, 0.25f, true);
		Grid.draw();
	}

	public void onTouch(Button btn, int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_DOWN)
		{
			if(btn == idle)
			{
				buffy.getAnimation().play("idle");
			}
			else if(btn == run)
			{
				buffy.getAnimation().play("run");
			}
		}
	}
}
