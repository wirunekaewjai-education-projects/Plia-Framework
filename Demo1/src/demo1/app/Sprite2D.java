package demo1.app;

import plia.core.Game;
import plia.core.scene.Sprite;

public class Sprite2D extends BaseApplication
{
	private Sprite sprite1, sprite2, sprite3;
	
	public Sprite2D()
	{
		super("Sprite 2D");
		
		sprite1 = Game.sprite("sprite/sprite3.png", 12);
		sprite1.getAnimation().setFrameRate(12);
		sprite1.setScale(0.2f, 0.2f);
		sprite1.setCenter(0.8f, 0.5f);
		
		sprite2 = Game.sprite("sprite/rockman.png", 10);
		sprite2.getAnimation().setFrameRate(15);
		sprite2.setScale(0.1f, 0.1f);
		sprite2.setCenter(-0.2f, 0.9f);
		
		sprite3 = Game.sprite("sprite/smoke.png", 8);
		sprite3.getAnimation().setFrameRate(12);
		sprite3.setScale(0.3f, 0.3f);
		sprite3.setCenter(0.3f, 0.5f);
		
		_2DLayer.addChild(sprite1, sprite2, sprite3);
	}

	public void update()
	{
		sprite2.setPosition(sprite2.getPosition().x+0.01f, 0.9f);
		
		if(sprite2.getPosition().x > 1)
		{
			sprite2.setPosition(-0.2f, 0.9f);
		}
	}
	
}
