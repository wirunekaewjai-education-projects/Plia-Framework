package plia.framework.gameobject.component;

import java.util.HashMap;

import plia.fbxsdk.core.base.FbxTimeSpan;
import plia.framework.core.GameTime;
import plia.framework.core.UpdateAdapter;

public class Animation implements UpdateAdapter
{
	private FbxTimeSpan timeSpan = new FbxTimeSpan(0, 0);
	
	private int frameRate = 60;
	
	private float sleepTime = 16;
	private float playbackSpeed = 1;
	private float currentFrame = 0;
	
	private AnimationClip currentAnimationClip = new AnimationClip("idle", 0, 100);
	private HashMap<String, AnimationClip> animationClips = new HashMap<String, AnimationClip>();
	
	public Animation()
	{
		this.animationClips.put(currentAnimationClip.getName(), currentAnimationClip);
	}
	
	public void update()
	{
		if(currentFrame >= currentAnimationClip.getEnd())
		{
			if(currentAnimationClip.getPlaybackMode() == PlaybackMode.LOOP)
			{
				
				currentFrame = currentAnimationClip.getStart();
			}
			else
			{
				stop();
			}
		}
		else
		{
			float step = ((float)GameTime.getElapsedGameTime().getMilliseconds() / sleepTime);
			currentFrame += step * playbackSpeed;
		}
	}
	
	public FbxTimeSpan getTimeSpan()
	{
		return timeSpan;
	}
	
	public int getFrameRate()
	{
		return frameRate;
	}

	public String getCurrentAnimationClipName()
	{
		return currentAnimationClip.getName();
	}
	
	public AnimationClip getCurrentAnimationClip()
	{
		return currentAnimationClip;
	}
	
	public float getPlaybackSpeed()
	{
		return playbackSpeed;
	}
	
	public int getTotalKeyframe()
	{
		return (int) (timeSpan.getLength() / (1539538600L) + 1);
	}
	
	public int getStartFrame()
	{
		return (int) (timeSpan.getStart() / 1539538600L);
	}
	
	public int getEndFrame()
	{
		return (int) (timeSpan.getStop() / 1539538600L);
	}
	
	public int getAnimationClipCount()
	{
		return animationClips.size();
	}
	
	public AnimationClip getAnimationClip(String clipName)
	{
		return animationClips.get(clipName);
	}
	
	public void addAnimationClip(String clipName, int start, int end)
	{
		this.animationClips.put(clipName, new AnimationClip(clipName, start, end));
	}
	
	public void addAnimationClip(String clipName, int start, int end, int playbackMode)
	{
		AnimationClip clip = new AnimationClip(clipName, start, end);
		clip.setPlaybackMode(playbackMode);
		this.animationClips.put(clipName, clip);
	}
	
	public void removeAnimationClip(String clipName)
	{
		this.animationClips.remove(clipName);
	}

	public void setTimeSpan(FbxTimeSpan timeSpan)
	{
		this.timeSpan = timeSpan;
	}
	
	public void setFrameRate(int frameRate)
	{
		this.frameRate = frameRate;
		this.sleepTime = 1000f / frameRate;
	}
	
	public void setPlaybackSpeed(float playbackSpeed)
	{
		if(playbackSpeed > 2.0f)
		{
			playbackSpeed = 2.0f;
		}
		else if(playbackSpeed < 0.4f)
		{
			playbackSpeed = 0.4f;
		}
		
		this.playbackSpeed = playbackSpeed;
	}
	
	public void play(String clipName)
	{
		//
		if(animationClips.containsKey(clipName))
		{
			currentAnimationClip = animationClips.get(clipName);
		}
	}

	public void stop()
	{
		//
		
	}
	
	public boolean isPlaying(String clipName)
	{
		return currentAnimationClip.getName() == clipName;
	}
	
	
}
