package wingkwai.main;

import java.util.ArrayList;

public class ItemDB
{
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private ItemDB()
	{
		Item none = new Item("none", 0, 0, 0, 0, 0);
		Item superBuffy = new Item("berserk", 2, 1.25f, -1, 0, 5);
		
		items.add(none);
		items.add(superBuffy);
	}

	private static ItemDB instance = new ItemDB();
	
	public static Item get(int index)
	{
		return instance.items.get(index);
	}
}
