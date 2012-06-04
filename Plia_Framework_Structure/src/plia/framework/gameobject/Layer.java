package plia.framework.gameobject;

@SuppressWarnings("rawtypes")
public abstract class Layer extends Node implements Intializable
{
	boolean isInitialized = false;
}

interface Intializable
{
	void initialize();
}