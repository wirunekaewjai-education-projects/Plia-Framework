package bvh.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;

import plia.core.scene.Group;
import plia.util.Convert;

public class BVH
{
	private static int index = 0;
	
	public static Group parse(InputStream inputStream)
	{
		String[] list = null;
		
		try
		{
			InputStreamReader isr = new InputStreamReader(inputStream);
			char[] buffer = new char[inputStream.available()];
			isr.read(buffer);

			list = new String(buffer).split("\\s");
		} 
		catch (IOException e)
		{
			Log.e("Error", e.getMessage());
		}

		String line = "";
		index = 0;
		
		String[] rotationOrder = new String[3];
		
		Joint root = null;
		
		if(list != null && list.length > 0)
		{
			while(!line.startsWith("}") && index < list.length)
			{
				line = list[index++];
				Log.e((index-1)+"", line);
				
				if(line.startsWith("ROOT"))
				{
					root = new Joint(list[index++]);
					Log.e((index-1)+"", list[index-1]);
				}
				else if(line.startsWith("OFFSET"))
				{
					float x = Convert.toFloat(list[index++]);
					float y = Convert.toFloat(list[index++]);
					float z = Convert.toFloat(list[index++]);
					
					root.setPosition(x, y, z);
				}
				else if(line.startsWith("CHANNELS"))
				{
					index++;
					String t1 = list[index++];
					String t2 = list[index++];
					String t3 = list[index++];
					
					String r1 = list[index++];
					String r2 = list[index++];
					String r3 = list[index++];
					
					rotationOrder[0] = r1.charAt(0)+"";
					rotationOrder[1] = r2.charAt(0)+"";
					rotationOrder[2] = r3.charAt(0)+"";
				}
				else if(line.startsWith("JOINT"))
				{
					root.addChild(loadJoint(list));
				}
			}
			
			return root;
		}
		
		return new Joint("root");
	}
	
	private static Joint loadJoint(String[] list)
	{
		Joint joint = new Joint(list[index++]);
		boolean isEnd = false;
		
		String line = list[index++];
		
		while(!line.startsWith("}") && index < list.length)
		{
			line = list[index++];
			Log.e((index-1)+"", line);
			
			if(line.startsWith("OFFSET") && !isEnd)
			{
				float x = Convert.toFloat(list[index++]);
				float y = Convert.toFloat(list[index++]);
				float z = Convert.toFloat(list[index++]);
				
				joint.setPosition(x, y, z);
			}
			else if(line.startsWith("JOINT"))
			{
				joint.addChild(loadJoint(list));
			}
			else if(line.startsWith("End"))
			{
				isEnd = true;
			}
//			else if(line.startsWith("End"))
//			{
//				line = list[index++];
//				if(line.startsWith("Site"))
//				{
//					String ll = "";
//					while(!ll.startsWith("\\}") && index < list.length)
//					{
//						ll = list[index++];
//					}
//				}
//			}
		}
		
		return joint;
	}
}
