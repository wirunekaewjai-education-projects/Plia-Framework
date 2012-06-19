package plia.test;

import plia.framework.core.GameObjectManager;
import plia.framework.scene.Camera;
import plia.framework.scene.Layer;
import plia.framework.scene.Object3D;
import plia.framework.scene.Scene;

public class Scene2 extends Scene
{
	private Layer<Object3D> layer1 = new Layer<Object3D>();
	private Object3D model;
	private Camera camera;

	public void onInitialize()
	{
		model = GameObjectManager.loadModel("elementalist31.FBX");

		camera = Scene.getMainCamera();
		camera.setPosition(100, 120, 100);
		camera.setLookAt(model);
		camera.setProjectionType(Camera.PERSPECTIVE);
		camera.setRange(1000);
		
		addLayer(layer1);
		layer1.addChild(model);
		layer1.addChild(camera);
	}

	public void onUpdate()
	{
		
	}

}
