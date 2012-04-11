package plugin.fbx.reader;

import android.util.Log;

public class ObjectDefinition implements FbxReadState
{
	public static final ObjectDefinition instance = new ObjectDefinition();
	
	@Override
	public void action(FbxPlugin fbx)
	{
		String line = "";

		while(!(line = fbx.raw.dequeue()).contains(FbxPlugin.TAGS[3]))
		{
//			if(line.contains("ObjectType:"))
//			{
//				String objectType = line.split("\"")[1];
//				int count = Integer.parseInt(fbx.raw.dequeue().split(": ")[1]);
//
//				Log.println(Log.ASSERT, objectType, count+"");
//			}
		}
		
		fbx.fsm.changeState(ObjectProperties.instance);
	}
	
	@Override
	public void start(FbxPlugin fbx)
	{
		Log.e("State", FbxPlugin.TAGS[2]);
	}
}
