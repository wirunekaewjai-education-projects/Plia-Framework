package plia.framework.scene.view;

import plia.framework.core.TouchEventManager;
import plia.framework.event.OnTouchListener;

public class Button extends Sprite
{
	private OnTouchListener onTouchListener = null;

	public Button()
	{

	}
	
	public OnTouchListener getOnTouchListener()
	{
		return onTouchListener;
	}
	
	public void setOnTouchListener(OnTouchListener onTouchListener)
	{
		this.onTouchListener = onTouchListener;
		TouchEventManager.getInstance().add(this);
	}
}
