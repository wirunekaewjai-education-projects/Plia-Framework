package plia.framework;

import java.lang.Thread.State;
import java.util.Vector;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public abstract class Framework implements IFramework, Runnable
{
	private Context mContext;
	
	private FrameLayout baseLayout = null;
	
	private Thread gameThread = null;
	private volatile boolean isRun = false;
	private Vector<Runnable> runnables = null;
	
	public Framework(Context context)
	{
		instance = this;
		
		mContext = context;
		baseLayout = new FrameLayout(context);
		
		gameThread = new Thread(this);
		runnables = new Vector<Runnable>();
	}
	
	@Override
	public void destroy()
	{
		instance = null;
	}
	
	@Override
	public void run()
	{
		while(isRun)
		{
			try
			{
				// TODO Something
				for (Runnable runnable : runnables)
				{
					runnable.run();
				}
				
				//
				Thread.sleep(15);
			} catch (InterruptedException e)
			{
				Log.e("Error", e.getMessage());
			}
		}
	}
	
	public void start()
	{
		isRun = true;
		gameThread.start();
	}
	
	public void stop()
	{
		destroy();
		
		isRun = false;

		while(gameThread.getState() != State.TERMINATED)
		{
			Log.e("Thread", "Waiting for terminate");
		}
		
		gameThread.stop();
		Log.e("Thread", "Thread is TERMINATED");
	}
	
	public void addRunnable(Runnable runnable)
	{
		if(!runnables.contains(runnable))
		{
			runnables.add(runnable);
		}
	}
	
	public void removeRunnable(Runnable runnable)
	{
		if(runnables.contains(runnable))
		{
			runnables.remove(runnable);
		}
	}
	

	private static Framework instance = null;
	static Framework getInstance()
	{
		return instance;
	}
}

interface IFramework
{
	void initialize();
	void update();
	void render();
	void destroy();
}