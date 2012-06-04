package plia.framework.gameobject;

public final class Scene extends Node<Layer>
{

	public void update()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			getChild(i).update();
		}
	}
}
