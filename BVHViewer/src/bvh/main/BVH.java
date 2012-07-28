package bvh.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.util.Log;

import plia.core.scene.Group;
import plia.core.scene.animation.Animation;
import plia.math.Vector3;
import plia.util.Convert;

public class BVH
{
	private static int index = 0;
	private static int stack = 0;
	private static int jointCount = 0;
	
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
		jointCount = 0;

		Joint root = null;
		Animation animation = null;
		ArrayList<ArrayList<Float>> motionData = new ArrayList<ArrayList<Float>>();
		
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
				
				if(line.startsWith("HIERARCHY"))
				{
					root = loadHierarchy(list);
				}
				else if(line.startsWith("MOTION"))
				{
					index++;
					animation = new Animation(0, Convert.toInt(list[index++].trim()));
					index+=2;
					String fpsStr = list[index++].trim();
					float frameRate = 1f / Convert.toFloat(fpsStr);
					animation.setFrameRate(Math.round(frameRate));
					
					ArrayList<Float> datas = loadMotionData(list);
					
					int size = (datas.size() / animation.getTotalFrame());
					
					for (int i = 0; i < animation.getTotalFrame(); i++)
					{
						ArrayList<Float> chunk = new ArrayList<Float>();
						for (int j = 0; j < size; j++)
						{
							chunk.add(datas.remove(0));
						}
						motionData.add(chunk);
					}
				}
			}

			Log.e("Joints", jointCount+"");
			if(root == null)
			{
				root = new Joint("root");
			}
			else
			{
				int count = motionData.size();
				for (int i = 0; i < count; i++)
				{
					setAnimationAndMotion(root, animation, motionData.remove(0));
				}
			}
			
			return root;
		}
		
		return new Joint("root");
	}
	
	private static void setAnimationAndMotion(Joint joint, Animation animation, ArrayList<Float> frameData)
	{
		int channelCount = joint.getChannelCount();
		
		joint.setAnimation(animation);
		
		Vector3 translation = new Vector3();
		Vector3 rotation = new Vector3();

		for (int i = 0; i < channelCount; i++)
		{
			int channel = joint.getChannel(i);
			float value = frameData.remove(0);
			
			switch (channel)
			{
				case 0 : translation.x = value; break;
				case 1 : translation.y = value; break;
				case 2 : translation.z = value; break;
				
				case 3 : rotation.x = value; break;
				case 4 : rotation.y = value; break;
				case 5 : rotation.z = value; break;
	
				default : break;
			}
		}
		
//		Log.e(translation.toString(), rotation.toString());
		
		joint.getMotion().add(translation, rotation);
		
		for (int i = 0; i < joint.getChildCount(); i++)
		{
			Joint child = (Joint)joint.getChild(i);
			setAnimationAndMotion(child, animation, frameData);
		}
	}
	
	private static Joint loadHierarchy(String[] list)
	{
		Joint joint = new Joint(list[index++]);
		boolean isSetOffset = false;
		int currentStack = 0;
		
		jointCount++;

		String line = "";
		
		while(index < list.length)
		{
			line = list[index++];

			if(line.startsWith("ROOT") || line.startsWith("JOINT"))
			{
				joint.addChild(loadHierarchy(list));
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

				joint.setPosition(x, y, z);
				isSetOffset = true;
			}
			else if(line.startsWith("CHANNELS"))
			{
				int channelCount = Convert.toInt(list[index++]);
				for (int i = 0; i < channelCount; i++)
				{
					String channel = list[index++];
					
					//Xposition Yposition Zposition Zrotation Xrotation Yrotation
					if(channel.startsWith("Xposition"))
					{
						joint.addChannel(0);
					}
					else if(channel.startsWith("Yposition"))
					{
						joint.addChannel(1);
					}
					else if(channel.startsWith("Zposition"))
					{
						joint.addChannel(2);
					}
					else if(channel.startsWith("Xrotation"))
					{
						joint.addChannel(3);
					}
					else if(channel.startsWith("Yrotation"))
					{
						joint.addChannel(4);
					}
					else if(channel.startsWith("Zrotation"))
					{
						joint.addChannel(5);
					}
				}
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
	
	private static ArrayList<Float> loadMotionData(String[] list)
	{
		ArrayList<Float> items = new ArrayList<Float>();
		while(index < list.length)
		{
			items.add(Convert.toFloat(list[index++].trim()));
		}
		
		return items;
	}
}
