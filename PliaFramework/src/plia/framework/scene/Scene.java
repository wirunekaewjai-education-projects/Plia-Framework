package plia.framework.scene;

import plia.framework.GameObject;

@SuppressWarnings({"rawtypes"})
public abstract class Scene extends GameObject implements IScene
{
	private Layer[] children = new Layer[32];
	private int childCount = 0;
	private boolean isInitialized = false;
	
	public Scene()
	{
		setName("Scene");
	}
	
	public void initialize()
	{
		if(isInitialized)
		{
			onInitialize();
			isInitialized = true;
		}
	}
	
	@Override
	public void update()
	{
		if(isActive())
		{
			for (int i = 0; i < childCount; i++)
			{
				children[i].update();
			}
		}
	}
	
	private final int indexOf(Layer layer)
	{
		for (int i = 0; i < children.length; i++)
		{
			if(layer == children[i])
			{
				return i;
			}
		}
		
		return -1;
	}

	public final boolean contains(Layer layer)
	{
		return indexOf(layer) != -1;
	}

	public final int getLayerCount()
	{
		return childCount;
	}

	public final Layer getLayer(int index)
	{
		if(index < childCount)
		{
			return children[index];
		}
		
		return null;
	}

	public final boolean addLayer(Layer layer)
	{
		if(indexOf(layer) == -1)
		{
			if(childCount >= children.length)
			{
				Layer[] arr = new Layer[children.length + 32];
				System.arraycopy(children, 0, arr, 0, children.length);
				children = arr;
			}
			
			children[childCount++] = layer;
			return true;
		}
		return false;
	}

	public final boolean removeLayer(Layer layer)
	{
		int i = indexOf(layer);
		if(i > -1 && i < childCount)
		{
			Layer[] arr = new Layer[children.length];
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

interface IScene
{
	void onInitialize();
	void onUpdate();
}