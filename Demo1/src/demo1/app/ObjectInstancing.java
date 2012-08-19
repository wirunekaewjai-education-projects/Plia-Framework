package demo1.app;

import java.util.ArrayList;

import demo1.main.Grid;
import android.graphics.Color;
import android.util.Log;
import plia.core.Game;
import plia.core.GameObjectManager;
import plia.core.event.OnTouchListener;
import plia.core.event.TouchEvent;
import plia.core.scene.Button;
import plia.core.scene.Group;
import plia.core.scene.animation.Animation;
import plia.core.scene.animation.PlaybackMode;
import plia.core.scene.shading.Shader;
import plia.math.Vector3;


public class ObjectInstancing extends BaseApplication
{
	private Group buffy;
	
	private Button add, sub, sub2;
	
	private boolean first = true;
	
	private ArrayList<Group> buffys = new ArrayList<Group>();
	
	public ObjectInstancing()
	{
		super("Object Instancing");
		
		camera.setPosition(25, 25, 15);
		camera.setEulerAngles(0, 0, 0);
		camera.setLookAt(new Vector3());
		camera.setRange(1000);
		
		buffy = GameObjectManager.loadModel("model/buffylow.FBX");
		buffy.setScale(0.1f, 0.1f, 0.1f);
		buffy.asModel().getMaterial().setShader(Shader.AMBIENT);
		
		Animation buffyAnimation = buffy.getAnimation();
		buffyAnimation.getAnimationClip("idle").set(0, 30, PlaybackMode.LOOP);
		buffyAnimation.addAnimationClip("run", 35, 50, PlaybackMode.LOOP);
		buffyAnimation.play("run");
		
		add = new Button();
		add.setScale(0.1f, 0.1f);
		add.setPosition(0.1f, 0.8f);
		add.setOnTouchListener(new OnTouchListener()
		{
			
			public void onTouch(Button btn, int action, float x, float y)
			{
				
				if(action == TouchEvent.ACTION_DOWN)
				{
					add();
				}
			}
		});
		
		sub = new Button();
		sub.setScale(0.1f, 0.025f);
		sub.setCenter(0.3f, 0.85f);
		sub2 = new Button();
		sub2.setPosition(0.25f, 0.8f);
		sub2.setScale(0.1f, 0.1f);
		sub2.setOnTouchListener(new OnTouchListener()
		{
			
			public void onTouch(Button btn, int action, float x, float y)
			{
				if(action == TouchEvent.ACTION_DOWN)
				{
					remove();
				}
			}
		});
		
		_3DLayer.addChild(camera, buffy);
		_2DLayer.addChild(add, sub);
	}
	
	private void add()
	{
		Log.e("Size", buffys.size()+"");
		if(buffys.size() < 40)
		{
			Group b = (buffys.isEmpty()) ? buffy.instantiate() : buffys.get(0).instantiate();
			int size = buffys.size() + 5;
			float $fsin = (float) (Math.sin(size) * (size));
		    float $fcos = (float) (Math.cos(size) * (size));
			b.translate($fsin, $fcos, 0);
			b.getAnimation().play("run");
			buffys.add(0, b);
			_3DLayer.addChild(b);
		}
	}
	
	private void remove()
	{
		Log.e("Size", buffys.size()+"");
		if(buffys.size() > 0)
		{
			Group b = buffys.remove(0);
			_3DLayer.removeChild(b);
		}
	}
	
	private void reset()
	{
		buffy.getAnimation().play("run");
		
		if(!buffys.isEmpty())
		{
			int count = buffys.size();
			for (int i = 0; i < count; i++)
			{
				Group b = buffys.remove(0);
				_3DLayer.removeChild(b);
			}
		}
		
		for (int i = 0; i < 3; i++)
		{
			add();
		}
		
		System.gc();
	}
	
	@Override
	public void resume()
	{
		super.resume();
		if(first)
		{
			add.setImageSrc(Game.text("+", 120, Color.WHITE));
			sub.setImageSrc(Game.text("-", 120, Color.WHITE));
			first = false;
		}
		
		reset();
	}

	public void update()
	{
		camera.rotate(0, 0, 0.25f, true);
		Grid.draw();
	}

}
