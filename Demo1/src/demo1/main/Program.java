package demo1.main;

import java.util.ArrayList;

import demo1.app.BaseApplication;
import demo1.app.FbxViewer;
import demo1.app.Lighting;

import plia.core.Game;
import plia.core.event.TouchEvent;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Scene;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Program extends Game
{
	private Scene scene;
	private ArrayList<BaseApplication> apps;

	private int state = -1;

	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		scene = new Scene();
		apps = new ArrayList<BaseApplication>();
		apps.add(new FbxViewer());
		apps.add(new Lighting());

		set(0);
		setScene(scene);
	}
	
	private void set(int index)
	{
		for (int i = 0; i < apps.size(); i++)
		{
			Layer<Group> layer = apps.get(i).getLayer();
			scene.removeLayer(layer);
		}
		scene.addLayer(apps.get(index).getLayer());
	}

	public void onUpdate()
	{
		if(state >= 0)
		{
			apps.get(state).update();
		}
	}
	
	@Override
	public void onTouchEvent(int action, float x, float y)
	{
		super.onTouchEvent(action, x, y);
		
		if(action == TouchEvent.ACTION_DOWN)
		{
			if(state == 0)
			{
				state = 1;
			}
			else
			{
				state = 0;
			}
			apps.get(state).resume();
			set(state);
		}
	}
}