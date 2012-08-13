package demo1.main;

import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Scene;

public abstract class App implements IApp
{
	protected final Camera camera;
	private final Layer<Group> layer;

	protected App()
	{
		layer = new Layer<Group>();
		camera = new Camera();
	}
	
	public void resume()
	{
		Scene.setMainCamera(camera);
	}
	
	public Layer<Group> getLayer()
	{
		return layer;
	}
}

interface IApp
{
	void resume();
	void update();
}
