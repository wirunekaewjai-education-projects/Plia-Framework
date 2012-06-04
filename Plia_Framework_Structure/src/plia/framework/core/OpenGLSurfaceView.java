package plia.framework.core;

import plia.framework.core.TouchEventManager;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class OpenGLSurfaceView extends GLSurfaceView
{
	private TouchEventManager touchEventManager;
	
	public OpenGLSurfaceView(Context context)
	{
		super(context);
		this.setEGLContextClientVersion(2);
		this.touchEventManager = TouchEventManager.getInstance();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.touchEventManager.onTouchEvent(event);
		return true;
	}
}
