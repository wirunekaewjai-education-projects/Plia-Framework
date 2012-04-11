package plugin.fbx;

import java.util.ArrayList;

public class Queue<E>
{
	ArrayList<E> list;

	public Queue()
	{
		list = new ArrayList<E>();
	}
	
	public int size()
	{
		return list.size();
	}

	public void queue(E object)
	{
		list.add(object);
	}

	public E dequeue()
	{
		if (list.size() > 0)
		{
			E object = list.get(0);
			list.remove(0);

			return object;
		}
		
		return null;
	}
}
