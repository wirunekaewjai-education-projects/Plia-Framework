package plia.framework;

import java.util.Vector;

import android.view.View;

public abstract class Controller implements IController, Runnable
{
	private View mView = null;
	private Vector<View> touchHoldList = null;
	
	public Controller(View view)
	{
		Framework.getInstance().addRunnable(this);
		mView = view;
		touchHoldList = new Vector<View>();
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
		View view = mView.findViewById(id);
		
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
		View view = mView.findViewById(id);
		
		if(touchHoldList.contains(view))
		{
			touchHoldList.remove(view);
		}
	}
	
	public void onTouchHoldEvent(View v)
	{
		
	}

	@Override
	public void run()
	{
		for (View view : touchHoldList)
		{
			if(view.isPressed())
			{
				onTouchHoldEvent(view);
			}
		}
	}
	
}

interface IController
{
	void update(Controller observer, Object...objects);
}
