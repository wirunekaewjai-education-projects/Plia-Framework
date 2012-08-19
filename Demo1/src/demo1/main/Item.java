package demo1.main;

import plia.core.Game;
import plia.core.scene.Sprite;
import plia.core.scene.shading.Texture2D;
import plia.math.Vector2;

public class Item extends ItemAdapter
{
	private Sprite illustration;
	private Sprite text;
	private String textSrc;
	
	public Item(String illusSrc, String text, float textSize, int textColor)
	{
		this.setImageSrc(Game.tex2D("mainpic/itemBackground.png"));
		
		illustration = Game.sprite(illusSrc);
		Texture2D t = Game.text(text, textSize, textColor);
		this.text = new Sprite();
		this.text.setImageSrc(t);
		
		textSrc = text;
		setChanged(true);
		addChild(illustration, this.text);
	}
	
	@Override
	protected void update()
	{
		super.update();
		
		if(isChanged())
		{
			Vector2 scale = getScale();

			float illsx = scale.y;
			Vector2 newScale = new Vector2(illsx * 0.65f, scale.y * 0.65f);
			Vector2 padding = new Vector2(((illsx - newScale.x)/2f), ((scale.y - newScale.y)/2f));
			illustration.setPosition(getX() + padding.x, getY() + padding.y);
			illustration.setScale(newScale);
			
//			float tw = scale.x - illsx - padding.x;
//			float th = Math.min((tw/(textSrc.length()+0.00001f)) * 4f, newScale.y); 
			
			float tx = illustration.getX() + newScale.y + padding.x;

			float th = newScale.y * 0.4f;
			float tw = Math.min((newScale.y / 8f) * textSrc.length(), (scale.x - illsx) - padding.x);
			
			float ty = getY() + ((scale.y-th)/2f);
			text.setPosition(tx, ty);
			text.setScale(tw, th);
			
			setChanged(false);
		}
	}
}
