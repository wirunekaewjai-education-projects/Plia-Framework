package plia.game;

import java.util.ArrayList;

public class Scene extends GameObject implements IGame
{
	final ArrayList<Node> children;
	
	public Scene()
	{
		children = new ArrayList<Node>();
	}
	
	@Override
	public void initialize()
	{
		
		
	}

	@Override
	public void update()
	{
		for (Node child : children)
		{
			child.update();
		}
	}

	public boolean addChild(Node child)
	{
		if(!children.contains(child))
		{
			children.add(child);
			return true;
		}
		
		return false;
	}

	public boolean removeChild(Node child)
	{
		if(children.contains(child))
		{
			children.remove(child);
			return true;
		}
		
		return false;
	}
}


