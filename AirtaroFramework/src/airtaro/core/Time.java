package airtaro.core;

public final class Time
{
	private TimeProfile elapsedGameTime;
	private TimeProfile totalGameTime;
	private long start;
	
	private long frameTime;
	
	private Time()
	{
		totalGameTime = new TimeProfile();
		elapsedGameTime = new TimeProfile();
	}
	
	public void start()
	{
		start = System.currentTimeMillis();
	}
	
	public void update()
	{
		if(start == 0)
		{
			
		}
		else
		{
			long current = System.currentTimeMillis();
			
			// Total Game Time
			long totalMillis = current - start;
			totalGameTime.update(totalMillis);
			
			long elapsedMillis = totalMillis - frameTime;
			frameTime = totalMillis;

			elapsedGameTime.update(elapsedMillis);
		}
	}
	
	private static Time instance = new Time();
	static Time getInstance()
	{
		return instance;
	}
	
	public static TimeProfile getTotalGameTime()
	{
		return instance.totalGameTime;
	}
	
	public static TimeProfile getElapsedGameTime()
	{
		return instance.elapsedGameTime;
	}
}
