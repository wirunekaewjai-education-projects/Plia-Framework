package plia.framework;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public abstract class ViewController extends Activity implements IController
{
	private FrameLayout baseLayout = null;
	private Vector<View> touchHoldList = null;
	
	
	private Thread thread = null;
	private volatile boolean isRun = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		baseLayout = new FrameLayout(this);
		touchHoldList = new Vector<View>();

		thread = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				while(isRun)
				{
//					recursiveChild(baseLayout);
					
					for (View view : touchHoldList)
					{
						if(view.isPressed())
						{
							onTouchHoldEvent(view);
						}
					}
					
					try
					{
						Thread.sleep(15);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@Override
	protected synchronized void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		
		isRun = false;
		while(isRun)
		{
			Log.e("State", "Waiting");
		}
		
	}

	@Override
	public void setContentView(View view)
	{
		super.setContentView(baseLayout);
		baseLayout.addView(view);
		
		isRun = true;
		thread.start();
	}
	
	@Override
	public void setContentView(int layoutResID)
	{
		super.setContentView(baseLayout);
		
		View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
		
		baseLayout.addView(contentView);
		
		isRun = true;
		thread.start();
	}
	
	@Override
	public void setContentView(View view, LayoutParams params)
	{
		super.setContentView(baseLayout);
		baseLayout.addView(view, params);
		
		isRun = true;
		thread.start();
	}
	
	public void onTouchHoldEvent(View v)
	{
		
	}
	
	
	public void registerTouchHoldEvent(View view)
	{	
		if(!touchHoldList.contains(view))
		{
			touchHoldList.add(view);
			view.setClickable(true);
		}
	}
	
	public void registerTouchHoldEvent(int id)
	{
		View view = findViewById(id);
		
		if(!touchHoldList.contains(view))
		{
			touchHoldList.add(view);
			view.setClickable(true);
		}
	}
	
	public void unregisterTouchHoldEvent(View view)
	{
		if(touchHoldList.contains(view))
		{
			touchHoldList.remove(view);
		}
	}
	
	public void unregisterTouchHoldEvent(int id)
	{
		View view = findViewById(id);
		
		if(touchHoldList.contains(view))
		{
			touchHoldList.remove(view);
		}
	}
	
//	private void recursiveChild(View view)
//	{
//		if(view.isPressed())
//		{
//			Log.e("Touching", view.getId()+"");
//		}
//		
//		if(view instanceof ViewGroup)
//		{
//			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
//			{
//				recursiveChild(((ViewGroup) view).getChildAt(i));
//			}
//		}
//	}


//	
//	@Override
//	public boolean onTouchEvent(MotionEvent event)
//	{
//		int[] location = new int[2];
//		for (View view : views)
//		{
//			Rect rct;
//			int w = view.getWidth();
//			int h = view.getHeight();
//			
//			view.getLocationOnScreen(location);
//			
//			rct = new Rect(location[0], location[1], location[0]+w, location[1]+h);
//			
//			Log.e("Rect", rct.left+", "+rct.right+", "+rct.bottom+", "+rct.top);
//			
//			
//			
//		}
//		
//		return true;
//	}

}

interface IController
{
	void update(ViewController controller, Object...objects);
}