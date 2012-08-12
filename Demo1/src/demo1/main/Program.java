package demo1.main;

import java.util.ArrayList;

import plia.core.Game;
import plia.core.event.TouchEvent;
import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Light;
import plia.core.scene.Scene;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Shader;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Program extends Game
{
	private Scene scene;
	private ArrayList<Layer<Group>> layers;
	
	private Camera camera;
	
	private int state = -1;

	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		scene = new Scene();
		layers = new ArrayList<Layer<Group>>();
		layers.add(new Layer<Group>());
		layers.add(new Layer<Group>());

		fbxViewerInit();
		lightingInit();

		set(0);
		setScene(scene);
	}
	
	private void set(int index)
	{
		for (int i = 0; i < layers.size(); i++)
		{
			scene.removeLayer(layers.get(i));
			layers.get(i).removeChild(camera);
		}
		scene.addLayer(layers.get(index));
		layers.get(index).addChild(camera);
	}

	public void onUpdate()
	{
		switch (state)
		{
			case 0: fbxViewerUpdate(); break;
			case 1: lightingUpdate(); break;
			default: break;
		}
	}
	
	@Override
	public void onTouchEvent(int action, float x, float y)
	{
		// TODO Auto-generated method stub
		super.onTouchEvent(action, x, y);
		
		if(action == TouchEvent.ACTION_DOWN)
		{
			if(state == 0)
			{
				state = 1;
				lightingResume();
			}
			else
			{
				state = 0;
				fbxViewerResume();
			}
			set(state);
		}
	}

	// FBX Viewer
	private Group buffy;
	private void fbxViewerInit()
	{
		buffy = model("model/buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);
		buffy.asModel().getMaterial().setShader(Shader.AMBIENT);
		
		Animation buffyAnimation = buffy.getAnimation();
		buffyAnimation.getAnimationClip("idle").set(0, 30, PlaybackMode.LOOP);
		buffyAnimation.addAnimationClip("run", 35, 50, PlaybackMode.LOOP);
		buffyAnimation.play("idle");
		
		layers.get(0).addChild(buffy);
	}
	private void fbxViewerResume()
	{
		camera = Scene.getMainCamera();
		camera.setPosition(10, 10, 5);
		camera.setEulerAngles(0, 0, 0);
		camera.setLookAt(vec3());
		camera.setRange(1000);
	}
	private void fbxViewerUpdate()
	{
		camera.rotate(0, 0, 0.25f, true);
		Grid.draw();
	}

	// Lighting
	private Group geosphere;
	private Group blackBox;
	private Group movingLightPoint;
	private Light movingLight;
	private void lightingInit()
	{
		geosphere = model("model/geosphere.FBX");
		
		blackBox = model("model/blackbox.FBX");
		blackBox.setPosition(0, 0, -5);
		blackBox.setScale(100, 100, 100);
		
		Group redPoint = model("model/redpoint.FBX");
		redPoint.setPosition(18, 0, 0);
		redPoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		redPoint.asModel().getMaterial().setBaseColor(1, 0, 0);
		
		Group greenPoint = redPoint.instantiate();
		greenPoint.setPosition(0, 18, 0);
		greenPoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		greenPoint.asModel().getMaterial().setBaseColor(0, 1, 0);
		
		Group bluePoint = redPoint.instantiate();
		bluePoint.setPosition(0, 0, 18);
		bluePoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		bluePoint.asModel().getMaterial().setBaseColor(0, 0, 1);
		
		movingLightPoint = redPoint.instantiate();
		movingLightPoint.setPosition(12, 0, 5);
		movingLightPoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		movingLightPoint.asModel().getMaterial().setBaseColor(1, 1, 1);

		movingLight = pointLight(12, 0, 5, 5);
		Light l1 = pointLight(18, 0, 0, 5, 1, 0, 0);
		Light l2 = pointLight(0, 18, 0, 5, 0, 1, 0);
		Light l3 = pointLight(0, 0, 18, 5, 0, 0, 1);
		
		layers.get(1).addChild(geosphere, blackBox, l1, l2, l3, redPoint, greenPoint, bluePoint, movingLight, movingLightPoint);
	}
	private void lightingResume()
	{
		camera = Scene.getMainCamera();
		camera.setPosition(20, 20, 12);
		camera.setEulerAngles(0, 0, 0);
		camera.setLookAt(vec3(0, 0, 4));
		camera.setRange(1000);
	}
	private void lightingUpdate()
	{
		geosphere.rotate(0, 0, 1);
		movingLightPoint.rotate(0, 0, -0.5f, true);
		movingLight.rotate(0, 0, -0.5f, true);
	}

	// Displacement Mapping
	
	// 
}