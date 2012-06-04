package plia.framework.gameobject;

public final class Scene extends Node<Layer> implements Intializable
{
	@Override
	public void initialize()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			Layer layer = getChild(i);
			if(!layer.isInitialized)
			{
				layer.initialize();
				layer.isInitialized = true;
			}
		}
	}
	
	public void update()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			Layer layer = getChild(i);
			if(!layer.isInitialized)
			{
				layer.initialize();
				layer.isInitialized = true;
			}
			layer.update();
		}
	}
}
