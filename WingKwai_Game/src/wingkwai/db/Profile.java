package wingkwai.db;

public class Profile
{
	private int matchCount;
	private int win;
	private int lose;
	
	public Profile()
	{
		matchCount = 0;
		win = 0;
		lose = 0;
	}
	
	public Profile(int matchCount, int win, int lose)
	{
		this.matchCount = matchCount;
		this.win = win;
		this.lose = lose;
	}
	
	public int getMatchCount()
	{
		return matchCount;
	}
	
	public int getWin()
	{
		return win;
	}
	
	public int getLose()
	{
		return lose;
	}
}
