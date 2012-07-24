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
	private static int stack = 0;
	
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
		stack = 0;
		
		String[] rotationOrder = new String[3];
		
		Joint root = null;
		
		ArrayList<String> alist = new ArrayList<String>();
		
		for (int i = 0; i < list.length; i++)
		{
			String tmp = list[i].trim();
			if(!tmp.isEmpty())
			{
				alist.add(tmp);
			}
		}
		
		list = new String[alist.size()];
		alist.toArray(list);
		
		if(list != null && list.length > 0)
		{
			while(index < list.length)
			{
				line = list[index++];
//				Log.e((index-1)+"", line);
				
				if(line.startsWith("ROOT"))
				{
					root = new Joint(list[index++]);
//					Log.e((index-1)+"", list[index-1]);
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
		boolean isSetOffset = false;
		int currentStack = 0;

		String line = "";
		
		while(index < list.length)
		{
			line = list[index++];

			if(line.startsWith("JOINT"))
			{
				joint.addChild(loadJoint(list));
			}
			else if(line.startsWith("{"))
			{
				stack++;
				currentStack = stack;
			}
			else if(line.startsWith("OFFSET") && !isSetOffset)
			{
				String sx = list[index++].trim();
				String sy = list[index++].trim();
				String sz = list[index++].trim();
				
				float x = Convert.toFloat(sx);
				float y = Convert.toFloat(sy);
				float z = Convert.toFloat(sz);
				
//				Log.e(joint.getName(), x+", "+y+", "+z);
				joint.setPosition(x, y, z);
				isSetOffset = true;
			}
			else if(line.startsWith("}"))
			{
				if(currentStack == stack--)
				{
					
					break;
				}
				
			}
		}
		
		return joint;
	}
}
