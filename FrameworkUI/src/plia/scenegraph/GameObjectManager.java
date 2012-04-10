package plia.scenegraph;

import java.util.HashMap;

public class GameObjectManager
{
	private int currentInstanceID;
	private HashMap<Object, Object> instances = null;
	

	private GameObjectManager()
	{
		currentInstanceID = 0;
		instances = new HashMap<Object, Object>();
	}
	
	public int getCurrentInstanceID()
	{
		return currentInstanceID++;
	}

	// //////////////////////
	private static GameObjectManager instance = null;

	public static void createInstance()
	{
		instance = new GameObjectManager();
	}

	public static void destroyInstance()
	{
		instance = null;
	}

	public static GameObjectManager getInstance()
	{
		return instance;
	}
}
