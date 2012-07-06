package plia.core.scene;

import plia.core.GameObject;
import plia.core.scene.animation.Animation;

@SuppressWarnings({"rawtypes", "unchecked"})
class Node<T extends Node> extends GameObject
{
	protected Node[] children = new Node[32];
	protected int childCount = 0;
	
	protected boolean hasAnimation = false;
	protected Animation animation;
	
	protected Node()
	{
		// TODO Auto-generated constructor stub
	}
	
	protected Node(String name)
	{
		// TODO Auto-generated constructor stub
		super(name);
	}

	@Override
	protected void copyTo(GameObject gameObject)
	{
		super.copyTo(gameObject);
		
		Node node = (Node) gameObject;
		node.animation = this.animation.clone();
		node.hasAnimation = this.hasAnimation;
		node.childCount = this.childCount;
		node.children = new Node[this.childCount];
		
		for (int i = 0; i < childCount; i++)
		{
			node.children[i] = this.children[i].instantiate();
		}
	}

	@Override
	public Node instantiate()
	{
		Node copy = new Node();
		this.copyTo(copy);
		return copy;
	}
	
	@Override
	public void setActive(boolean active)
	{
		super.setActive(active);
		
		for (int i = 0; i < childCount; i++)
		{
			children[i].setActive(active);
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
	
	@Override
	protected void update()
	{
		// TODO Auto-generated method stub
		super.update();
	}
	
	public boolean hasAnimation()
	{
		return hasAnimation;
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
		this.hasAnimation = (animation != null);
	}
	
	public void setHasAnimation(boolean hasAnimation)
	{
		this.hasAnimation = hasAnimation;
	}
}
