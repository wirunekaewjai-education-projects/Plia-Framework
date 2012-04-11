package plugin.fbx.reader;

import android.util.Log;

public class ObjectConnections implements FbxReadState
{
	public static final ObjectConnections instance = new ObjectConnections();

	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		while(!(line = fbx.raw.dequeue()).contains(FbxPlugin.TAGS[5]))
		{
			
			
		
		
		
		
		
		
		
		
		
		

		}
		
		fbx.fsm.changeState(TakesSection.instance);
	}
	
	@Override
	public void start(FbxPlugin fbx)
	{
		Log.e("State", FbxPlugin.TAGS[4]);
	}
}
