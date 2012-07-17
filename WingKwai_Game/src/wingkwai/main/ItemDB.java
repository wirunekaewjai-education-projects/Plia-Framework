package wingkwai.main;

import java.util.HashMap;

public class ItemDB
{
	private HashMap<String, Item> items = new HashMap<String, Item>();
	
	private ItemDB()
	{
//		Item none = new Item("none", 0, 0, 0, 0);
//		Item superBuffy = new Item("berserk", 2, 1.25f, -1, 0);
	}

	private static ItemDB instance = new ItemDB();
	
	
}
