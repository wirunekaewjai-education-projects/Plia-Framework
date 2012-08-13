package demo1.app;

import android.graphics.Color;
import plia.core.Game;
import plia.core.scene.Camera;
import plia.core.scene.Group;
import plia.core.scene.Layer;
import plia.core.scene.Scene;
import plia.core.scene.Sprite;
import plia.core.scene.View;
import plia.core.scene.shading.Texture2D;

public abstract class BaseApplication implements IApp
{
	protected final Camera camera;
	protected final Layer<View> _2DLayer;
	protected final Layer<Group> _3DLayer;
	
	private Sprite applicationName;
	private String appNameTxt;

	private boolean isFirst = true;
	
	protected BaseApplication(String appName)
	{
		appNameTxt = appName;
		
		_2DLayer = new Layer<View>();
		_3DLayer = new Layer<Group>();
		camera = new Camera();
	}
	
	public void resume()
	{
		if(isFirst)
		{
			Texture2D text2D = Game.text(appNameTxt, 128, Color.WHITE);
			text2D.setEnabledAlpha(true);
			
			applicationName = new Sprite();
			applicationName.setImageSrc(text2D);
			applicationName.setPosition(0.005f, 0.005f);
			applicationName.setScale(0.02f * appNameTxt.length(), 0.075f);
			
			_2DLayer.addChild(applicationName);
			isFirst = false;
		}
		
		Scene.setMainCamera(camera);
	}
	
	public Layer<View> get2DLayer()
	{
		return _2DLayer;
	}
	
	public Layer<Group> get3DLayer()
	{
		return _3DLayer;
	}
}

interface IApp
{
	void resume();
	void update();
}
