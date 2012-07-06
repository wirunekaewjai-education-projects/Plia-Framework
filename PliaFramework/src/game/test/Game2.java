package game.test;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import plia.core.Game;
import plia.core.Screen;
import plia.core.debug.Debug;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Light;
import plia.core.scene.PlaneCollider;
import plia.core.scene.Scene;
import plia.core.scene.Terrain;
import plia.core.scene.View;
import plia.core.scene.shading.Color3;
import plia.math.Vector2;
import plia.math.Vector3;

public class Game2 extends Game
{
	private Scene scene = new Scene();
	private Layer<Group> layer1 = new Layer<Group>();
	private Layer<View> layer2 = new Layer<View>();
	
	private Camera camera;
	private Group map, buffy;
	
	private Button padButton;
	
	public void onInitialize(Bundle savedInstanceState)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		map = model("tscene.FBX");
		buffy = model("buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);

		layer1.addChild(map, buffy);
		
		
		camera = Scene.getMainCamera();
//		camera.setPosition(-300, 300, 200);
//		camera.setLookAt(vec3());
		camera.setPosition(0, -100, 90);
		camera.rotate(-10, 0, 0);
		camera.setRange(2500);
		camera.setSky(skydome("sky_sphere01.jpg"));

//		layer1.addChild(camera);
		buffy.addChild(camera);
		
		Vector3 min = map.asModel().getGeometry().getMin();
		Vector3 max = map.asModel().getGeometry().getMax();
		
		Vector3 size = Vector3.subtract(max, min);
		
		collider = collider(vec3(0, 0, 1), vec2(size.x, size.y));
		
		
		
		padButton = button("pad.png");
		
		float ratio = (float)Screen.getWidth() / Screen.getHeight();
		float scalef = 0.15f;
		padButton.setScale(scalef, scalef * ratio);
		padButton.setActive(false);
		padButton.setOnTouchListener(new OnTouchListener()
		{
			
			public void onTouch(Button button, int action, float x, float y)
			{
				Vector2 center = button.getCenter();
				
				float dx = center.x - x;
				float dy = center.y - y;
				
				Vector2 dir = vec2(dx, dy).getNormalized();
				
				buffy.translate(0, dir.y * 1f, 0);

				if(dir.x != 0.0f)
				{
					buffy.rotate(0, 0, 1.5f * dir.x);
				}
			}
		});
		

		layer1.addChild(collider);
		layer2.addChild(padButton);
		
		scene.addLayer(layer1);
		scene.addLayer(layer2);
		
		this.setScene(scene);
	}
	PlaneCollider collider;

	public void onUpdate()
	{
		Log.e("Buffy", buffy.getPosition().toString());
		
//		buffy.rotate(0.05f, 0, 0);
//		buffy.translate(0, 1, 0);

//		Debug.drawBounds(collider, new Color3(0.5f, 1, 0.5f));
	}

	@Override
	public void onTouchEvent(int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_UP || action == TouchEvent.ACTION_NONE)
		{
			padButton.setActive(false);
		}
		else if(action == TouchEvent.ACTION_DOWN)
		{
			padButton.setActive(true);
			padButton.setCenter(x, y);
		}
	}
}
