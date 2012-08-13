package demo1.main;

import java.util.ArrayList;

import demo1.app.BaseApplication;
import demo1.app.Displacement;
import demo1.app.FBX;
import demo1.app.Lighting;

import plia.core.Game;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Scene;
import plia.core.scene.Sprite;
import plia.core.scene.View;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Program extends Game implements OnTouchListener
{
	private Scene scene;
	private Layer<View> ui;
	private Button previous, next;
	
	private ArrayList<BaseApplication> apps;

	private int state = -1;
	private boolean isChanged = false;
	
	private Layer<View> mainLayer;
	private Sprite mainbg;
	private Button b1, b2, b3;

	public void onInitialize(Bundle arg0)
	{
		setRequestedOrientation(0);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		scene = new Scene();
		apps = new ArrayList<BaseApplication>();
		apps.add(new FBX());
		apps.add(new Lighting());
		apps.add(new Displacement());
		
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
		scene.addLayer(ui);
		
		mainbg = sprite("mainpic/mainbg.png");
		b1 = new Button();
		b1.setScale(0.5f, 0.15f);
		b1.setPosition(0.5f, 0.225f);
		b1.setOnTouchListener(this);
		
		b2 = new Button();
		b2.setScale(0.5f, 0.15f);
		b2.setPosition(0.5f, 0.4f);
		b2.setOnTouchListener(this);
		
		b3 = new Button();
		b3.setScale(0.5f, 0.15f);
		b3.setPosition(0.5f, 0.575f);
		b3.setOnTouchListener(this);
		mainbg.addChild(b1, b2, b3);

		mainLayer = new Layer<View>();
		mainLayer.addChild(mainbg);
		scene.addLayer(mainLayer);

		setScene(scene);
	}
	
	private void removeAll()
	{
		mainbg.setActive(false);
		scene.removeLayer(mainLayer);
		
		for (int i = 0; i < apps.size(); i++)
		{
			Layer<View> _2DLayer = apps.get(i).get2DLayer();
			Layer<Group> _3DLayer = apps.get(i).get3DLayer();
			scene.removeLayer(_2DLayer);
			scene.removeLayer(_3DLayer);
		}
	}
	
	private void set(int index)
	{
		removeAll();
		
		scene.addLayer(apps.get(index).get2DLayer());
		scene.addLayer(apps.get(index).get3DLayer());
		
		isChanged = true;
	}

	public void onUpdate()
	{
		if(state >= 0)
		{
			if(isChanged)
			{
				apps.get(state).resume();
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
			scene.addLayer(mainLayer);
		}
	}

	public void onTouch(Button btn, int action, float x, float y)
	{
		if(action == TouchEvent.ACTION_DOWN)
		{
			if(btn == b1)
			{
				state = 0;
			}
			else if(btn == b2)
			{
				state = 1;
			}
			else if(btn == b3)
			{
				state = 2;
			}
			else if(btn == previous)
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
			
			set(state);
		}
	}
}