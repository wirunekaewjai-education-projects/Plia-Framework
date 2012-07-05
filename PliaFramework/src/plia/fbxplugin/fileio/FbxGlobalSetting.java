package plia.fbxplugin.fileio;

import plia.fbxplugin.core.base.FbxTimeMode;
import plia.fbxplugin.core.base.FbxTimeSpan;

public class FbxGlobalSetting
{
	private FbxTimeMode timeMode;
	private FbxTimeSpan timeSpan;
	
	private int upAxis;
	private int upAxisSign;
	
	private int frontAxis;
	private int frontAxisSign;

	public FbxTimeMode getTimeMode()
	{
		return timeMode;
	}

	public void setTimeMode(FbxTimeMode timeMode)
	{
		this.timeMode = timeMode;
	}

	public FbxTimeSpan getTimeSpan()
	{
		return timeSpan;
	}

	public void setTimeSpan(FbxTimeSpan timeSpan)
	{
		this.timeSpan = timeSpan;
	}
	
	public int getFrontAxis()
	{
		return frontAxis;
	}
	
	public int getFrontAxisSign()
	{
		return frontAxisSign;
	}
	
	public void setFrontAxis(int frontAxis)
	{
		this.frontAxis = frontAxis;
	}
	
	public void setFrontAxisSign(int frontAxisSign)
	{
		this.frontAxisSign = frontAxisSign;
	}
	
	public int getUpAxis()
	{
		return upAxis;
	}
	
	public int getUpAxisSign()
	{
		return upAxisSign;
	}
	
	public void setUpAxis(int upAxis)
	{
		this.upAxis = upAxis;
	}
	
	public void setUpAxisSign(int upAxisSign)
	{
		this.upAxisSign = upAxisSign;
	}
}
