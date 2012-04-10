package plia.game;

public abstract class GameComponent implements IGameComponent
{
	private Game mGame = null;
	
	public GameComponent(Game game)
	{
		mGame = game;
	}
	
	public Game getGame()
	{
		return mGame;
	}
}

interface IGameComponent
{
	void initialize();
	void update();
}
