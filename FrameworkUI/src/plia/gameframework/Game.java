package plia.gameframework;

import java.util.Vector;

public class Game implements Runnable
{
	private Vector<IUpdatable> updatables = null;
	
	public Game()
	{
		updatables = new Vector<IUpdatable>();
	}
	
	public void initialize()
	{
		
	}

	public void update()
	{
		
	}

	public void render()
	{
		
	}
	
	void addUpdatableObject(IUpdatable updatable)
	{
		if(!updatables.contains(updatable))
		{
			updatables.add(updatable);
		}
	}
	
	void removeUpdatableObject(IUpdatable updatable)
	{
		if(updatables.contains(updatable))
		{
			updatables.remove(updatable);
		}
	}

	@Override
	public void run()
	{
		
		
	}

}
