package plia.fbxsdk.fileio;

import plia.fbxsdk.core.base.FbxTimeMode;
import plia.fbxsdk.core.base.FbxTimeSpan;

public class FbxGlobalSetting
{
	private FbxTimeMode timeMode;
	private FbxTimeSpan timeSpan;

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
}
