package plia.test;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import plia.framework.core.Game;

public class Game1 extends Game
{
	public void onInitialize(Bundle savedInstanceState)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Game.runWithScene(new Scene1());
	}
}
