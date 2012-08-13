package demo1.main;

import plia.core.GameObjectManager;
import plia.core.scene.Group;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Shader;
import plia.math.Vector3;

public class FbxViewer extends App
{
	private Group buffy;

	public FbxViewer()
	{
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
		
		getLayer().addChild(camera, buffy);
	}

	public void update()
	{
		camera.rotate(0, 0, 0.25f, true);
		Grid.draw();
	}
}
