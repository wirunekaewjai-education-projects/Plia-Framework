package app.main;

import android.util.Log;
import plia.game.Game;

public class Game1 extends Game
{

	public Game1()
	{
		Log.e("State", "Starting");
	}

	@Override
	public void initialize()
	{
		getGraphicsManager().getScreen().setColor(1, 1, 0.5f);
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		Log.e("State", "Updating");
	}

	@Override
	public void render()
	{
		// TODO Auto-generated method stub
		Log.e("State", "Drawing");
	}

}
