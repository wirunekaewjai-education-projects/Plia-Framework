package plia.scene;

import plia.core.GameObject;

@SuppressWarnings("rawtypes")
public final class Layer<T extends Node> extends GameObject
{
	protected Node[] children = new Node[32];
	protected int childCount = 0;
	
	public Layer()
	{
		setName("Layer");
	}
	
	@Override
	protected void update()
	{
		if(isActive())
		{
			for (int i = 0; i < childCount; i++)
			{
				children[i].update();
			}
		}
	}
	
	private final int indexOf(T child)
	{
		for (int i = 0; i < children.length; i++)
		{
			if(child == children[i])
			{
				return i;
			}
		}
		
		return -1;
	}

	public final boolean contains(T child)
	{
		return indexOf(child) != -1;
	}

	public final int getChildCount()
	{
		return childCount;
	}

	@SuppressWarnings("unchecked")
	public final T getChild(int index)
	{
		if(index < childCount)
		{
			return (T) children[index];
		}
		
		return null;
	}

	public boolean addChild(T child)
	{
		if(indexOf(child) == -1)
		{
			if(childCount >= children.length)
			{
				Node[] arr = new Node[children.length + 32];
				System.arraycopy(children, 0, arr, 0, children.length);
				children = arr;
			}
			
			children[childCount++] = child;
			return true;
		}
		return false;
	}
	
	public void addChild(T...children)
	{
		for (T child : children)
		{
			addChild(child);
		}
	}

	public boolean removeChild(T child)
	{
		int i = indexOf(child);
		if(i > -1 && i < childCount)
		{
			Node[] arr = new Node[children.length];
			System.arraycopy(children, 0, arr, 0, i);
			
			int i2 = i+1;
			int length = childCount - i2;
			if(length > 0)
			{
				System.arraycopy(children, i2, arr, i, length);
			}
			
			children = arr;
			childCount--;

			return true;
		}
		return false;
	}
}
