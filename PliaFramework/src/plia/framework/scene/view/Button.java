package plia.framework.scene.view;

import plia.framework.core.GameObject;
import plia.framework.core.TouchEventManager;
import plia.framework.event.OnTouchListener;

public class Button extends Sprite
{
	private OnTouchListener onTouchListener = null;

	public Button()
	{

	}
	
	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		Button view = (Button) gameObject;
	}

	@Override
	public Button instantiate()
	{
		Button copy = new Button();
		this.copyTo(copy);
		return copy;
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
