package game.test;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import plia.core.Game;
import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Light;
import plia.core.scene.Scene;
import plia.core.scene.Terrain;
import plia.math.Vector3;

public class Game2 extends Game
{
	private Scene scene = new Scene();
	private Layer<Group> layer = new Layer<Group>();
	
	private Camera camera;
	private Group map, buffy;
	
	public void onInitialize(Bundle savedInstanceState)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		map = model("tscene.FBX");
		buffy = model("buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);

		layer.addChild(map, buffy);
		scene.addLayer(layer);
		
		this.setScene(scene);
		
		camera = Scene.getMainCamera();
		camera.setPosition(0, -100, 90);
		camera.rotate(-10, 0, 0);
		camera.setRange(600);
		camera.setSky(skydome("sky_sphere01.jpg"));

		buffy.addChild(camera);
		
	}

	public void onUpdate()
	{
		buffy.rotate(0.05f, 0, 0);
		buffy.translate(0, 1, 0);
	}

}
