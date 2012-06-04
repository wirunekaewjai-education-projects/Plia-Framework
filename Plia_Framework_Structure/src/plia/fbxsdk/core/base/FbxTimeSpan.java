package plia.fbxsdk.core.base;

public class FbxTimeSpan
{
	private long start;
	private long stop;
	
	public FbxTimeSpan()
	{
		this.start = 0;
		this.stop = 0;
	}
	
	public FbxTimeSpan(long start, long stop)
	{
		this.start = start;
		this.stop = stop;
	}
	
	public long getStart()
	{
		return start;
	}
	
	public void setStart(long start)
	{
		this.start = start;
	}
	
	public long getStop()
	{
		return stop;
	}
	
	public void setStop(long stop)
	{
		this.stop = stop;
	}
	
	public long getLength()
	{
		return stop - start;
	}
}
