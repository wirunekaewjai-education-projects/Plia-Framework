package plugin.fbx.reader;

import android.util.Log;

public class DocumentReferences implements FbxReadState
{
	public static final DocumentReferences instance = new DocumentReferences();

	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		while(!(line = fbx.raw.dequeue()).contains(FbxPlugin.TAGS[2]))
		{
			
			
		
		
		
		
		
		
		
		
		
		

		}
		
		fbx.fsm.changeState(ObjectDefinition.instance);

	}
	
	@Override
	public void start(FbxPlugin fbx)
	{
		Log.e("State", FbxPlugin.TAGS[1]);
	}
}
