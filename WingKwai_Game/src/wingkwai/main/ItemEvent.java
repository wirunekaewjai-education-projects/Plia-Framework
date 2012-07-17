package wingkwai.main;

import java.util.ArrayList;

public class ItemEvent
{
	private ArrayList<Item> items = new ArrayList<Item>();
	
	public void add(Item item)
	{
		items.add(item);
	}
	
	public void remove(Item item)
	{
		items.remove(item);
	}
	
	public void update()
	{
		
	}
}
