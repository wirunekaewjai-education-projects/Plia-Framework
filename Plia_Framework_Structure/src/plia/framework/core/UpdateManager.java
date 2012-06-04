package plia.framework.core;

import java.util.Vector;

public class UpdateManager
{
	private Vector<UpdateAdapter> queues = new Vector<UpdateAdapter>();

	private Vector<UpdateAdapter> added = new Vector<UpdateAdapter>();
	private Vector<UpdateAdapter> removed = new Vector<UpdateAdapter>();

	private UpdateManager()
	{

	}

	public void enqueue(UpdateAdapter obj)
	{
		if (!queues.contains(obj))
		{
			queues.add(obj);
		}
	}

	public void add(UpdateAdapter obj)
	{
		if (!added.contains(obj))
		{
			added.add(obj);
		}
	}

	public void remove(UpdateAdapter obj)
	{
		if (!removed.contains(obj))
		{
			removed.add(obj);
		}
	}

	public void removeAll()
	{
		queues.clear();
		added.clear();
		removed.clear();
	}

	public void update()
	{
		if (!removed.isEmpty())
		{
			int rsize = removed.size();
			for (int i = 0; i < rsize; i++)
			{
				if (removed.isEmpty())
				{
					break;
				}
				added.remove(removed.remove(0));
			}
		}

		if (!added.isEmpty())
		{
			int asize = added.size();
			for (int i = 0; i < asize; i++)
			{
				if (added.isEmpty())
				{
					break;
				}
				UpdateAdapter current = added.get(i);
				current.update();
			}
		}

		if (!queues.isEmpty())
		{
			int qsize = queues.size();
			for (int i = 0; i < qsize; i++)
			{
				if (queues.isEmpty())
				{
					break;
				}
				UpdateAdapter current = queues.remove(0);
				current.update();
			}
		}
	}
	
	private static UpdateManager instance = new UpdateManager();
	public static UpdateManager getInstance()
	{
		return instance;
	}
}
