package plugin.fbx.reader;

import android.util.Log;

public class DocumentsDescription implements FbxReadState
{
	public static final DocumentsDescription instance = new DocumentsDescription();

	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		while(!(line = fbx.raw.dequeue()).contains(FbxPlugin.TAGS[1]))
		{
			
			
		
		
		
		
		
		
		
		
		
		

		}
		
		fbx.fsm.changeState(DocumentReferences.instance);
	}

	@Override
	public void start(FbxPlugin fbx)
	{
		Log.e("State", FbxPlugin.TAGS[0]);
	}

}
