package demo1.main;

import java.util.ArrayList;

import demo1.app.BaseApplication;
import demo1.app.CollisionDetection;
import demo1.app.Displacement;
import demo1.app.FBX;
import demo1.app.Lighting;
import demo1.app.ObjectInstancing;
import demo1.app.SkyDomeApp;
import demo1.app.Sprite2D;

import plia.core.Game;
import plia.core.GameTime;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Scene;
import plia.core.scene.Sprite;
import plia.core.scene.View;
import plia.core.scene.shading.Texture2D;
import plia.math.Vector3;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Program extends Game implements OnTouchListener, SensorEventListener
{
	private Scene scene;
	private Layer<View> ui;
	private Button previous, next;
	
	private ArrayList<BaseApplication> apps;

	private int state = -1;
	private boolean isChanged = false;
	
	private Layer<View> mainLayer;
	private Sprite mainbg;
	private ListView listView;
	private Button up, down;
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	
	private Sprite textFPS;
	private Sprite sprite;
	private Texture2D fpsTex;
	private long fps;
	private int delay;
	
	private boolean isFirst = true;

	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		String[] strname = 
			{
				"FBX File Format",
				"Lighting",
				"Displacement Mapping",
				"Collision Detection",
				"Sprite 2D",
				"Sky Dome",
				"Object Instancing",
			};
		
		listView = new ListView(4, 0.6f, 0.2f);
		listView.setPosition(0.1f, 0.145f);
		
		for (int i = 0; i < strname.length; i++)
		{
			listView.addChild(new Item("mainpic/list0"+(i+1)+".jpg", strname[i], 120, Color.WHITE));
		}
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			
			public void onClick(ItemAdapter item, int index)
			{
				state = index;
				isChanged = true;
			}
		});
		
		up = button("mainpic/upButton.png");
		down = button("mainpic/downButton.png");
		
		up.setScale(0.15f, 0.17f);
		down.setScale(0.15f, 0.17f);
		
		up.setCenter(0.85f, 0.325f);
		down.setCenter(0.85f, 0.675f);
		
		up.setOnTouchListener(this);
		down.setOnTouchListener(this);
		
		scene = new Scene();
		apps = new ArrayList<BaseApplication>();
		apps.add(new FBX());
		apps.add(new Lighting());
		apps.add(new Displacement());
		apps.add(new CollisionDetection());
		apps.add(new Sprite2D());
		apps.add(new SkyDomeApp());
		apps.add(new ObjectInstancing());
		
		previous = button("ui/previous.png");
		previous.setScale(0.06f, 0.1f);
		previous.setCenter(0.04f, 0.5f);
		previous.setOnTouchListener(this);
		
		next = button("ui/next.png");
		next.setScale(0.06f, 0.1f);
		next.setCenter(0.96f, 0.5f);
		next.setOnTouchListener(this);
		
		ui = new Layer<View>();
		ui.addChild(previous, next);
		ui.setActive(false);
		scene.addLayer(ui);
		
		mainbg = sprite("mainpic/mainBackground.png");
		mainbg.addChild(up, down);

		textFPS = new Sprite();
		textFPS.setPosition(0.005f, 0.1f);
		textFPS.setScale(0.0125f * (("Framerate :     fps").length()), 0.05f);
		
		sprite = new Sprite();
		sprite.setPosition(0.005f + 0.0125f * (("Framerate :  ").length()), 0.1f);
		ui.addChild(textFPS, sprite);
		
		mainLayer = new Layer<View>();
		mainLayer.addChild(mainbg, listView);
		scene.addLayer(mainLayer);

		setScene(scene);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		mSensorManager.unregisterListener(this);

	}
	
	private void removeAll()
	{
		mainbg.setActive(false);
		listView.setActive(false);
		ui.setActive(true);
		
		scene.removeLayer(mainLayer);

		for (int i = 0; i < apps.size(); i++)
		{
			Layer<View> _2DLayer = apps.get(i).get2DLayer();
			Layer<Group> _3DLayer = apps.get(i).get3DLayer();
			scene.removeLayer(_2DLayer);
			scene.removeLayer(_3DLayer);
		}
	}

	public void onUpdate()
	{
		if(isFirst)
		{
			textFPS.setImageSrc(Game.text("Framerate :     fps", 64, Color.WHITE));
			isFirst = false;
		}

		if(delay > 9)
		{
			fps = (long) (1000f / GameTime.getElapsedGameTime().getMilliseconds());
			if(fps > 60)
			{
				fps = 60;
			}
			fpsTex = Game.text(fps+"", 30, Color.WHITE);
			sprite.setScale(0.0125f * ((fps+"").length()), 0.04f);
			sprite.setImageSrc(fpsTex);

			delay = 0;
		}
		delay++;
		
		if(state >= 0)
		{
			if(isChanged)
			{
				apps.get(state).resume();
				
				removeAll();
				
				scene.addLayer(apps.get(state).get2DLayer());
				scene.addLayer(apps.get(state).get3DLayer());

				listView.resume();
				isChanged = false;
			}
			
			apps.get(state).update();
		}
	}
	
	@Override
	public void onBackPressed()
	{
		if(scene.contains(mainLayer))
		{
			super.onBackPressed();
		}
		else
		{
			removeAll();
			mainbg.setActive(true);
			listView.setActive(true);
			ui.setActive(false);
			scene.addLayer(mainLayer);
		}
	}


	public void onTouch(Button btn, int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_DOWN)
		{
			if(btn == up)
			{
				listView.previous();
			}
			else if(btn == down)
			{
				listView.next();
			}
			else
			{
				if(btn == previous)
				{
					state -= 1;
				}
				else if(btn == next)
				{
					state += 1;
				}
				
				if(state >= apps.size())
				{
					state = 0;
				}
				else if(state < 0)
				{
					state = apps.size()-1;
				}
				isChanged = true;
			}
		}
	}
	
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{
		
	}

	public void onSensorChanged(SensorEvent event)
	{
		ACCEL.x = (Float.isNaN(event.values[0])) ? 0 : event.values[0];
		ACCEL.y = (Float.isNaN(event.values[1])) ? 0 : event.values[1];
		ACCEL.z = (Float.isNaN(event.values[2])) ? 0 : event.values[2];
	}
	
	public static final Vector3 ACCEL = new Vector3();
}