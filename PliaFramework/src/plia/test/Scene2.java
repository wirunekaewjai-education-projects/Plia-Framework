package plia.test;

import plia.framework.debug.Debug;
import plia.framework.event.OnTouchListener;
import plia.framework.scene.PlaneCollider;
import plia.framework.scene.SphereCollider;
import plia.framework.scene.Collider;
import plia.framework.scene.Camera;
import plia.framework.scene.Group;
import plia.framework.scene.Layer;
import plia.framework.scene.Scene;
import plia.framework.scene.View;
import plia.framework.scene.group.shading.Color3;
import plia.framework.scene.view.Button;

public class Scene2 extends Scene implements OnTouchListener
{
	private PlaneCollider boundingPlane = new PlaneCollider();
	private SphereCollider boundingSphere = new SphereCollider();
	private SphereCollider boundingSphere2 = new SphereCollider(10);
	private Color3 color = new Color3(0.5f, 1, 0.5f);
	
	private Button button1, button2, button3, button4;
	
	private Camera camera;
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();

	public void onInitialize()
	{
		camera = Scene.getMainCamera();
		camera.setPosition(50, 50, 50);
		camera.setRange(1000);
		camera.setLookAt(vec3());
		camera.setProjectionType(Camera.PERSPECTIVE);
		
		boundingPlane.setScale(20, 20, 20);
		boundingPlane.setUp(0, 1, 0);
		
		boundingSphere.setRadius(10);
		
		boundingSphere2.setPosition(30, 0, 0);
		
		button1 = button();
		button2 = button();
		button3 = button();
		button4 = button();
		
		button1.setScale(0.5f, 0.5f);
		button2.setScale(0.5f, 0.5f);
		button3.setScale(0.5f, 0.5f);
		button4.setScale(0.5f, 0.5f);
		
		button2.setPosition(0, 0.5f);
		button3.setPosition(0.5f, 0);
		button4.setPosition(0.5f, 0.5f);
		
		layer1.addChild(camera, boundingPlane, boundingSphere, boundingSphere2);
		layer2.addChild(button1, button2);
		addLayer(layer1);
		addLayer(layer2);

		button1.setOnTouchListener(this);
		button2.setOnTouchListener(this);
		button3.setOnTouchListener(this);
		button4.setOnTouchListener(this);
	}

	public void onUpdate()
	{
		Debug.drawBounds(boundingPlane, color);
		Debug.drawBounds(boundingSphere, color);
		
		if(Collider.intersect(boundingPlane, boundingSphere))
		{
			Debug.drawBounds(boundingSphere2, color);
		}
	}

	public void onTouch(Button button, int action, float x, float y)
	{
		if(button == button1)
		{
			boundingPlane.translate(0, 0, 1);
		}
		else if(button == button2)
		{
			boundingPlane.translate(0, 0, -1);
		}
		
		else if(button == button3)
		{
			boundingPlane.rotate(0, 0, 1);
		}
		else if(button == button4)
		{
			boundingPlane.rotate(0, 0, -1);
		}
	}

	
}
