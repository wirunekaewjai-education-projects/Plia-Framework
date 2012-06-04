package plia.application;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import plia.framework.core.Activity;
import plia.framework.core.Framework;
import plia.framework.gameobject.Layer;
import plia.framework.gameobject.Scene;

public class MainActivity extends Activity
{
	private Scene scene1;
	private GameLayer layer1;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		scene1 = new Scene();
		layer1 = new GameLayer();
		scene1.addChild(layer1);
		Framework.getInstance().setScene(scene1);
	}
	
	private class GameLayer extends Layer
	{

		@Override
		public void initialize()
		{
			
		}

		@Override
		public void update()
		{
			
		}
		
	}
}