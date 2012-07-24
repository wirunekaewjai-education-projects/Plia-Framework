package bvh.main;

import java.io.IOException;

import plia.core.Game;
import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Scene;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Game 
{
	private Scene scene;
	private Layer<Group> layer;
	private Camera camera;
	
	private Joint j1, j2, j3;

	@Override
	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		scene = new Scene();
		layer = new Layer<Group>();
		
		camera = camera(Camera.PERSPECTIVE, 40, 50, 0, 0, 0, 0, 100);
		Scene.setMainCamera(camera);
		
		try
		{
			j1 = (Joint) BVH.parse(getAssets().open("example1.bvh"));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scene.addLayer(layer);
		layer.addChild(camera, j1);
		
		setScene(scene);
		
		Game.enabledDebug = true;

		printName(j1, "");
	}
	
	private void printName(Group joint, String space)
	{
		Log.e("", space + joint.getName());
		
		for (int i = 0; i < joint.getChildCount(); i++)
		{
			printName(joint.getChild(i), space + "  ");
		}
	}

	@Override
	public void onUpdate()
	{
		j1.draw();
	}
}