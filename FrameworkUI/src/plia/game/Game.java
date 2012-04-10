package plia.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

public abstract class Game implements IGame
{
	Context mContext;
	
	private Vector<GameComponent> mComponent = null;
	private GraphicsManager mGraphicsManager = null;

	// Getter/Setter
	public Vector<GameComponent> getComponents()
	{
		return mComponent;
	}
	
	public GraphicsManager getGraphicsManager()
	{
		return mGraphicsManager;
	}
	
	
	private void onCreate(Context context)
	{
		mContext = context;
		mComponent = new Vector<GameComponent>();
		mGraphicsManager = new GraphicsManager(this);
	}
	
	/////////////////////////////////////////////////////////////////////////
	public static void startGame(Context context, Class<? extends Game> game)
	{
		Constructor[] ctors = game.getConstructors();
		Constructor<Game> ctor = null;
		
		for (int i = 0; i < ctors.length; i++)
		{
			ctor = ctors[i];
			
			if(ctor.getGenericParameterTypes().length == 0)
			{
				break;
			}
		}
		
		if(ctor != null)
		{
			try
			{
				ctor.setAccessible(true);
				Game nGame = ctor.newInstance();
				nGame.onCreate(context);
				
				
			} catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				Log.e("Error", e.getMessage());
			} catch (InstantiationException e)
			{
				// TODO Auto-generated catch block
				Log.e("Error", e.getMessage());
			} catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				Log.e("Error", e.getMessage());
			} catch (InvocationTargetException e)
			{
				// TODO Auto-generated catch block
				Log.e("Error", e.getMessage());
			}
		}
	}
}

interface IGame
{
	void initialize();
	void update();
	void render();
}
