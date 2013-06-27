package game.test;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import plia.core.FbxDroid;
import plia.core.Game;
import plia.core.event.TouchEvent;

public class MyGame extends Game
{

	public void onInitialize(Bundle savedInstanceState)
	{
		
	}

	public void onUpdate()
	{
		
	}

	@Override
	public void onTouchEvent(int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_DOWN)
		{
			long start = System.nanoTime();
			FbxDroid.importScene("elementalist31.FBX", this);
			float end = (System.nanoTime() - start) / 1000000f;
			Log.e("Usage Time", end+" ms");
		}
		
		super.onTouchEvent(action, x, y);
	}
}



