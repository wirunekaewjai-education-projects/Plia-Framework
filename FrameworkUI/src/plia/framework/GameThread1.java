package plia.framework;

import java.lang.Thread.State;
import java.util.Vector;


import android.content.Context;
import android.util.Log;

public abstract class GameThread1 implements Runnable
{
	private Context mContext;
	
	private Thread gameThread = null;
	private volatile boolean isRun = false;
	private Vector<Runnable> runnables = null;
	
	public GameThread1(Context context)
	{
		instance = this;
		
		mContext = context;

		
		gameThread = new Thread(this);
		runnables = new Vector<Runnable>();

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
	
	public synchronized void stop()
	{
		isRun = false;

		while(gameThread.getState() != State.TERMINATED)
		{
			Log.e("Thread", "Waiting for terminate");
		}
		
		
		gameThread.stop();
		
		instance = null;
		
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
	
	public Context getContext()
	{
		return mContext;
	}

	private static GameThread1 instance = null;
	static GameThread1 getInstance()
	{
		return instance;
	}
}
