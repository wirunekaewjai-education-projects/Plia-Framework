package plia.framework.gameobject;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public abstract class Node<T extends Node> extends GameObject
{
	protected T parent = null;
	protected boolean isRoot = true;
	private final ArrayList<T> children = new ArrayList<T>();
	
	protected Node()
	{
		super();
	}
	
	protected Node(String name)
	{
		super(name);
	}
	
	public int getChildCount()
	{
		return children.size();
	}
	
	public T getChild(int index)
	{
		return children.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public boolean addChild(T child)
	{
		if(!children.contains(child))
		{
			child.parent = this;
			child.isRoot = false;
			children.add(child);
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean removeChild(T child)
	{
		if(!children.contains(child))
		{
			child.parent = null;
			child.isRoot = true;
			children.remove(child);
			return true;
		}
		
		return false;
	}
}
