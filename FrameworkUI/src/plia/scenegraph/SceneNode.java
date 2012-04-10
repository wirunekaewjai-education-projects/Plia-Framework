package plia.scenegraph;

import java.util.ArrayList;

public abstract class SceneNode implements IUpdatable
{
	public SceneNode parent = null;
	public ArrayList<SceneNode> children = null;
	
	public SceneNode()
	{
		children = new ArrayList<SceneNode>();
	}
	
	@Override
	public void update()
	{
		for (SceneNode child : children)
		{
			child.update();
		}
	}
	
	public boolean addChild(SceneNode child)
	{
		if(!children.contains(child))
		{
			children.add(child);
			
			child.parent = this;
			return true;
		}
		
		return false;
	}
	
	public boolean removeChild(SceneNode child)
	{
		if(children.contains(child))
		{
			children.remove(child);
			child.parent = null;
			return true;
		}
		
		return false;
	}
}
