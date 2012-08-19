package demo1.main;

import java.util.ArrayList;

import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.View;

public class ListView extends View implements OnTouchListener
{
	private float iw, ih, isc;
	private boolean isChanged;
	private ArrayList<ItemAdapter> before = new ArrayList<ItemAdapter>();
	private ArrayList<ItemAdapter> current = new ArrayList<ItemAdapter>();
	private ArrayList<ItemAdapter> after = new ArrayList<ItemAdapter>();
	
	private OnItemClickListener onItemClickListener;
	
	public ListView(int itemShowCount, float itemWidth, float itemHeight)
	{
		isc = itemShowCount;
		iw = itemWidth;
		ih = itemHeight;
		
		isChanged = true;
	}
	
	@Override
	protected void update()
	{
		if(isChanged)
		{
//			Log.e("Update", "is Changed");
			for (int i = 0; i < childCount; i++)
			{
				super.removeChild(getChild(0));
			}

			for (int i = 0; i < current.size(); i++)
			{
				float x = getX();
				float y = getY() + (i * ih);
				
				ItemAdapter adapter = current.get(i);
				adapter.setActive(true);
				adapter.setScale(iw, ih);
				adapter.setPosition(x, y);
				adapter.setChanged(true);
				super.addChild(adapter);
			}
			
			for (int i = 0; i < before.size(); i++)
			{
				before.get(i).setActive(false);
			}
			
			for (int i = 0; i < after.size(); i++)
			{
				after.get(i).setActive(false);
			}

			isChanged = false;
		}
		
		super.update();
	}
	
	public void next()
	{
		if(!after.isEmpty())
		{
			if(!current.isEmpty())
			{
				before.add(current.remove(0));
			}
			current.add(after.remove(0));
			isChanged = true;
		}
	}
	
	public void previous()
	{
		if(!before.isEmpty() && !current.isEmpty())
		{
			after.add(0, current.remove(current.size()-1));
			current.add(0, before.remove(before.size()-1));
			isChanged = true;
		}
	}
	
	@Override
	public boolean addChild(View itemAdapter)
	{
		if(itemAdapter instanceof ItemAdapter)
		{
			if(after.isEmpty() && current.size() < isc)
			{
				current.add((ItemAdapter) itemAdapter);
			}
			else
			{
				after.add((ItemAdapter) itemAdapter);
			}
			
			((ItemAdapter) itemAdapter).setOnTouchListener(this);
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean removeChild(View itemAdapter)
	{
		if(itemAdapter instanceof ItemAdapter)
		{
			if(before.contains(itemAdapter))
			{
				before.remove(itemAdapter);
			}
			else if(current.contains(itemAdapter))
			{
				current.remove(itemAdapter);
				if(!after.isEmpty())
				{
					current.add(after.remove(0));
				}
			}
			else
			{
				after.remove(itemAdapter);
			}
			
			((ItemAdapter) itemAdapter).setOnTouchListener(null);
			
			return true;
		}
		
		return false;
	}

	public void onTouch(Button btn, int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_UP)
		{
			if(onItemClickListener != null && btn instanceof ItemAdapter)
			{
				int indx = before.size() + (current.indexOf(btn));
				if(indx > -1)
				{
					onItemClickListener.onClick((ItemAdapter) btn, indx);
				}
			}
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener)
	{
		this.onItemClickListener = onItemClickListener;
	}
}
