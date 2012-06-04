package plia.framework.core;

import java.io.IOException;
import java.util.HashMap;

import plia.framework.graphics.Texture2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class TextureManager
{
	private Context context;
	private HashMap<Object, Texture2D> textureList = new HashMap<Object, Texture2D>();

	public Texture2D loadFromAssets(String file)
	{
		if(!textureList.containsKey(file))
		{
			try
			{
				Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(file));
				Texture2D tex = new Texture2D(bitmap);
				textureList.put(file, tex);
				
			} catch (IOException e)
			{
				Log.e("Error", e.getMessage());
			}
		}
		
		return textureList.get(file);
	}
	
	public Texture2D loadFromResource(int id)
	{
		if(!textureList.containsKey(id))
		{
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
			Texture2D tex = new Texture2D(bitmap);
			textureList.put(id, tex);
		}
		
		return textureList.get(id);
	}

	public void setContext(Context context)
	{
		this.context = context;
	}
	
	private static TextureManager instance = new TextureManager();
	public static TextureManager getInstance()
	{
		return instance;
	}
}
