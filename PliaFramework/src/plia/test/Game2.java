package plia.test;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import plia.framework.core.Game;
import plia.framework.math.Vector3;
import plia.framework.scene.Camera;
import plia.framework.scene.Group;
import plia.framework.scene.Layer;
import plia.framework.scene.Light;
import plia.framework.scene.Scene;
import plia.framework.scene.Terrain;

public class Game2 extends Game
{
	private Scene scene = new Scene();
	private Layer<Group> layer = new Layer<Group>();
	
	private Camera camera;
	private Terrain terrain;
	private Light backLight, keyLight, fillLight;
	private Group group;
	
	public void onInitialize(Bundle savedInstanceState)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		group = model("tscene.FBX");

		layer.addChild(group);
		scene.addLayer(layer);
		
		this.setScene(scene);
		
		camera = Scene.getMainCamera();
		camera.setPosition(0, 0, 150);
		camera.setLookAt(vec3(300, -300, 0));
		camera.setRange(600);
		camera.setSky(skydome("sky_sphere01.jpg"));
		
		layer.addChild(camera);
		
		keyLight = directionalLight(1, -1, -1, 0.7f, 1, 1);
		backLight = directionalLight(3);
		fillLight = directionalLight(0, 0, -1, 0.65f);
		
		layer.addChild(keyLight, fillLight);
	}

	public void onUpdate()
	{
		camera.rotate(0, 0, 0.25f);
	}

}
