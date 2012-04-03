package app.main;

import java.util.Vector;

import plia.framework.Model;

public class Database extends Model
{	
	private Vector<Integer> list = new Vector<Integer>();
	
	public Database()
	{
		list.add(0);
		list.add(0);
		list.add(0);
	}
	
	public void updateValue(int index)
	{
		list.set(index, list.get(index)+1);
		notifyAllObserver(list.get(0), list.get(1), list.get(2));
	}
}
