package demo1.main;

import plia.core.scene.Button;

public class ItemAdapter extends Button
{
	private boolean changed = false;
	
	public boolean isChanged()
	{
		return changed;
	}
	
	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}
}
