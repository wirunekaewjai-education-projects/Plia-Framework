package plia.game;

public class Node extends Scene
{
	Node parent;
	
	public Node()
	{
		
	}
	
	@Override
	public final boolean addChild(Node child)
	{
		boolean b = super.addChild(child);
		
		if(b)
		{
			child.parent = this;
		}
		
		return b;
	}
	
	@Override
	public final boolean removeChild(Node child)
	{
		
		boolean b = super.removeChild(child);
		
		if(b)
		{
			child.parent = null;
		}
		
		return b;
	}
}
