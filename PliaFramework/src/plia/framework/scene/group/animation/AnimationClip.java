package plia.framework.scene.group.animation;

public class AnimationClip
{
	private String name;
	private int start;
	private int end;
	
	private int playbackMode = PlaybackMode.ONCE;
	
	public AnimationClip()
	{
		// TODO Auto-generated constructor stub
	}
	
	public AnimationClip(String name, int start, int end)
	{
		this.name = name;
		this.start = start;
		this.end = end;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getStart()
	{
		return start;
	}
	
	public void setStart(int start)
	{
		this.start = start;
	}
	
	public int getEnd()
	{
		return end;
	}
	
	public void setEnd(int end)
	{
		this.end = end;
	}
	
	public int getPlaybackMode()
	{
		return playbackMode;
	}
	
	public void setPlaybackMode(int playbackMode)
	{
		this.playbackMode = playbackMode;
	}
	
	public void set(int start, int end, int playbackMode)
	{
		this.start = start;
		this.end = end;
		this.playbackMode = playbackMode;
	}
}
