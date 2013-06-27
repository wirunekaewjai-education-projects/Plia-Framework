package game.test;

import java.io.IOException;

import plia.core.FbxDroid;
import plia.plugin.fbx.fileio.FbxImporter;
import game.test.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			long start = System.nanoTime();
			FbxDroid.importScene("buffylow.FBX", this);
			float end = (System.nanoTime() - start) / 1000000f;
			Log.e("Usage Time", end+" ms");
		}
		
		return super.onTouchEvent(event);
	}
}