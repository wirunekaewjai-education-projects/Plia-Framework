package plia.framework;

import android.content.Context;
import android.widget.FrameLayout;

public class GameThread extends FrameLayout
{
	
	
	public GameThread(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDetachedFromWindow()
	{
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		stop();
	}

	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}
}
