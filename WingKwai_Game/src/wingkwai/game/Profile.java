package wingkwai.game;

public class Profile
{
	private int match, win, lose;
	
	public Profile()
	{
		this.match = 0;
		this.win = 0;
		this.lose = 0;
	}
	
	public Profile(int match, int win, int lose)
	{
		this.match = match;
		this.win = win;
		this.lose = lose;
	}
	
	public int getMatch()
	{
		return match;
	}
	
	public int getWin()
	{
		return win;
	}
	
	public int getLose()
	{
		return lose;
	}
	
	public void setMatch(int match)
	{
		this.match = match;
	}
	
	public void setWin(int win)
	{
		this.win = win;
	}
	
	public void setLose(int lose)
	{
		this.lose = lose;
	}
}
