package plugin.fbx.reader;

import android.util.Log;


public class TakesSection implements FbxReadState
{
	public static final TakesSection instance = new TakesSection();

	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		while((line = fbx.raw.dequeue()) != null)
		{
			
			
		
		
		
		
		
		
		
		
		
		

		}
	}
	
	@Override
	public void start(FbxPlugin fbx)
	{
		Log.e("State", FbxPlugin.TAGS[5]);
	}
}
