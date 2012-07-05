package game.test;

import java.io.IOException;

import plia.fbxplugin.fileio.FbxImporter;
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
			try
			{
				FbxImporter.importScene(getAssets().open("elementalist31.FBX"));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			float end = (System.nanoTime() - start) / 1000000f;
			Log.e("Usage Time", end+" ms");
		}
		
		return super.onTouchEvent(event);
	}
}