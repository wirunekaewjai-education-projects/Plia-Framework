package plia.core;

import java.util.ArrayList;
import java.util.List;

import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;

public class TouchEventManager
{
	private final List<Button> buttons = new ArrayList<Button>();
	
	// Touch
	private Button currentButton;
	private int currentAction = TouchEvent.ACTION_NONE;
	
	private float locationX, locationY;
	private float tempX, tempY;
	private int count = 0;
	
	private TouchEventManager()
	{
		
	}
	
	public void add(Button button)
	{
		buttons.add(button);
	}
	
	public void remove(Button button)
	{
		buttons.remove(button);
	}
	
	public void removeAll()
	{
		buttons.clear();
	}
	
	public void update()
	{
		if(currentButton != null && currentButton.isActive())
		{
			if(currentAction == TouchEvent.ACTION_DOWN)
			{
				if(count == 4)
				{
					currentAction = TouchEvent.ACTION_HOLD;
					count = 0;
				}
				count++;
			}

			if(currentAction == TouchEvent.ACTION_HOLD || currentAction == TouchEvent.ACTION_DRAG)
			{
				if(count == 2)
				{
					tempX = locationX;
					tempY = locationY;
				}
				else if(count == 4)
				{
					if(tempX == locationX && tempY == locationY)
					{
						currentAction = TouchEvent.ACTION_HOLD;
					}
					
					count = 0;
				}
				
				currentButton.getOnTouchListener().onTouch(currentButton, currentAction, locationX, locationY);
				count++;
			}
		}
	}
	
	public void onTouchEvent(int action, float x, float y)
	{
		locationX = x;
		locationY = y;
		
		if(action == TouchEvent.ACTION_DOWN)
		{
			for (Button button : buttons)
			{
				if(button.intersect(locationX, locationY) && button.isActive())
				{
					OnTouchListener onTouchListener = button.getOnTouchListener();
					if(onTouchListener != null)
					{
//						Log.e(button.toString(), button.getOnTouchListener().toString());
						currentAction = TouchEvent.ACTION_DOWN;
						currentButton = button;
						button.getOnTouchListener().onTouch(currentButton, currentAction, locationX, locationY);
						count = 0;
						break;
					}
				}
			}
		}

		if(currentButton != null)
		{
			if(!currentButton.intersect(locationX, locationY))
			{
				currentButton.getOnTouchListener().onTouch(currentButton, TouchEvent.ACTION_CANCEL, locationX, locationY);
				currentAction = TouchEvent.ACTION_NONE;
				currentButton = null;
			}
			else if(action == TouchEvent.ACTION_UP)
			{
				currentButton.getOnTouchListener().onTouch(currentButton, action, locationX, locationY);
				currentAction = TouchEvent.ACTION_NONE;
				currentButton = null;
			}
			else if(action == TouchEvent.ACTION_DRAG)
			{
				currentAction = action;
				count = 0;
			}
		}
	}
	
	public void destroy()
	{
		buttons.clear();
		instance = null;
	}
	
	private static TouchEventManager instance;
	public static TouchEventManager getInstance()
	{
		if(instance == null)
		{
			instance = new TouchEventManager();
		}
		
		return instance;
	}
}
