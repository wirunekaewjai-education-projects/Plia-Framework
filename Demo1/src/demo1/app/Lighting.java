package demo1.app;

import plia.core.GameObjectManager;
import plia.core.scene.Group;
import plia.core.scene.Light;
import plia.core.scene.shading.Shader;
import plia.math.Vector3;

public class Lighting extends BaseApplication
{
	private Group geosphere;
	private Group blackBox;
	private Group movingLightPoint;
	private Light movingLight;

	public Lighting()
	{
		camera.setPosition(20, 20, 12);
		camera.setEulerAngles(0, 0, 0);
		camera.setLookAt(new Vector3(0, 0, 4));
		camera.setRange(1000);
		
		geosphere = GameObjectManager.loadModel("model/geosphere.FBX");
		
		blackBox = GameObjectManager.loadModel("model/blackbox.FBX");
		blackBox.setPosition(0, 0, -5);
		blackBox.setScale(100, 100, 100);
		
		Group redPoint = GameObjectManager.loadModel("model/redpoint.FBX");
		redPoint.setPosition(18, 0, 0);
		redPoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		redPoint.asModel().getMaterial().setBaseColor(1, 0, 0);
		
		Group greenPoint = redPoint.instantiate();
		greenPoint.setPosition(0, 18, 0);
		greenPoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		greenPoint.asModel().getMaterial().setBaseColor(0, 1, 0);
		
		Group bluePoint = redPoint.instantiate();
		bluePoint.setPosition(0, 0, 18);
		bluePoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		bluePoint.asModel().getMaterial().setBaseColor(0, 0, 1);
		
		movingLightPoint = redPoint.instantiate();
		movingLightPoint.setPosition(12, 0, 5);
		movingLightPoint.asModel().getMaterial().setShader(Shader.AMBIENT);
		movingLightPoint.asModel().getMaterial().setBaseColor(1, 1, 1);

		movingLight = new Light(Light.POINT_LIGHT, 5);
		movingLight.setPosition(12, 0, 5);
		
		Light l1 = new Light(Light.POINT_LIGHT, 5, 1, 1, 0, 0);
		l1.setPosition(18, 0, 0);
		
		Light l2 = new Light(Light.POINT_LIGHT, 5, 1, 0, 1, 0);
		l2.setPosition(0, 18, 0);
		
		Light l3 = new Light(Light.POINT_LIGHT, 5, 1, 0, 0, 1);
		l3.setPosition(0, 0, 18);

		getLayer().addChild(camera, geosphere, blackBox, l1, l2, l3, redPoint, greenPoint, bluePoint, movingLight, movingLightPoint);
	}

	public void update()
	{
		geosphere.rotate(0, 0, 1);
		movingLightPoint.rotate(0, 0, -0.5f, true);
		movingLight.rotate(0, 0, -0.5f, true);
	}

}
