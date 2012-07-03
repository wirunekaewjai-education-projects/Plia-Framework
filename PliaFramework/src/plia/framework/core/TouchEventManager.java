package plia.framework.core;

import java.util.ArrayList;
import java.util.List;

import plia.framework.event.OnTouchListener;
import plia.framework.event.TouchEvent;
import plia.framework.scene.view.Button;

import android.view.MotionEvent;

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
		if(currentButton != null)
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
	
	public void onTouchEvent(MotionEvent event)
	{
		int action = TouchEvent.ACTION_NONE;
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN : action = TouchEvent.ACTION_DOWN; break;
			case MotionEvent.ACTION_MOVE : action = TouchEvent.ACTION_DRAG; break;
			case MotionEvent.ACTION_UP : action = TouchEvent.ACTION_UP; break;
		}
		
		locationX = event.getX() / Screen.getWidth();
		locationY = event.getY() / Screen.getHeight();
		
		if(action == TouchEvent.ACTION_DOWN)
		{
			for (Button button : buttons)
			{
				if(button.intersect(locationX, locationY) && button.isActive())
				{
					OnTouchListener onTouchListener = button.getOnTouchListener();
					if(onTouchListener != null)
					{
						currentAction = TouchEvent.ACTION_DOWN;
						currentButton = button;
						onTouchListener.onTouch(currentButton, currentAction, locationX, locationY);
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
	
	private static TouchEventManager instance = new TouchEventManager();
	public static TouchEventManager getInstance()
	{
		return instance;
	}
}
