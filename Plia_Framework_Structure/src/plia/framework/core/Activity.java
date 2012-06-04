package plia.framework.core;

import android.os.Bundle;

public class Activity extends android.app.Activity
{
	private OpenGLSurfaceView surfaceView;
	private Framework framework;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		framework = Framework.getInstance();
		surfaceView = new OpenGLSurfaceView(this);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		framework.linkSurfaceView(surfaceView);
		framework.start();
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		
		framework.stop();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		framework.resume();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		framework.pause();
	}
}
