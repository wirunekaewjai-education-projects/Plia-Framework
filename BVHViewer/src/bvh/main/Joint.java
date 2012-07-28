package bvh.main;

import java.util.ArrayList;

import android.util.Log;

import plia.core.debug.Debug;
import plia.core.scene.Group;
import plia.core.scene.animation.Animation;
import plia.core.scene.shading.Color3;
import plia.math.Matrix4;
import plia.math.Vector3;
import plia.math.Vector4;

public class Joint extends Group
{
	private static Vector4[] points = 
		{ 
			new Vector4(-2, 0, 0, 1), new Vector4(2, 0, 0, 1), 
			new Vector4(0, -2, 0, 1), new Vector4(0, 2, 0, 1), 
			new Vector4(0, 0, -2, 1), new Vector4(0, 0, 2, 1) 
		};
	
	private Vector3[] drawPoints = new Vector3[6];
	
	private static Color3 color = new Color3(1, 1, 1);
	private static Color3[] rgb = { new Color3(1, 0, 0), new Color3(0, 1, 0), new Color3(0, 0, 1)};
	
	private ArrayList<Integer> channels = new ArrayList<Integer>();
	private Motion motion = new Motion();

	public Joint(String name)
	{
		setName(name);
	}

	@Override
	protected void onUpdateHierarchy(boolean parentHasChanged)
	{
		if(hasAnimation)
		{
			Matrix4 world = getWorldMatrix();
			
			int currentFrame = animation.getCurrentFrame();
//			Log.e("Frame", currentFrame+"");
			Matrix4 palette = motion.get(currentFrame);
			
			if(palette != null)
			{
				
				if(isRoot())
				{
					world = Matrix4.multiply(getAxisRotation(), palette);
				}
				else
				{
					world = palette.clone();
				}
				
//				if(!isRoot())
//				{
//					world = Matrix4.multiply(getParent().getWorldMatrix(), palette);
//				}
//				else
//				{
//					world = Matrix4.multiply(getAxisRotation(), palette);
//				}
				
//				Log.e(name, palette.toString());
				
				getWorldMatrix().set(world.clone());
			}
			
			
		}
		
		super.onUpdateHierarchy(parentHasChanged);
		

		
		for (int i = 0; i < 6; i++)
		{
			drawPoints[i] = new Vector3(Matrix4.multiply(getWorldMatrix(), points[i]));
		}
	}
	
	public int getChannelCount()
	{
		return channels.size();
	}
	
	public void addChannel(int c)
	{
		channels.add(c);
	}
	
	public void removeChannel(int c)
	{
		channels.remove(c);
	}
	
	public int getChannel(int index)
	{
		return channels.get(index);
	}
	
	public Motion getMotion()
	{
		return motion;
	}
	
	public void setMotion(Motion motion)
	{
		this.motion = motion;
	}
	
	public void draw()
	{
		
		if(!isRoot())
		{
			Debug.drawLine(parent.getPosition(), getPosition(), color);
		}
		
//		for (int i = 0; i < 2; i++)
//		{
//			for (int j = 2; j < 6; j++)
//			{
//				Debug.drawLine(drawPoints[i], drawPoints[j], color);
//			}
//		}
//		
//		for (int i = 2; i < 4; i++)
//		{
//			for (int j = 4; j < 6; j++)
//			{
//				Debug.drawLine(drawPoints[i], drawPoints[j], color);
//			}
//		}

		
		for (int i = 0; i < 6; i+=2)
		{
			int indx = i/2;
			Debug.drawLine(drawPoints[i], drawPoints[i+1], rgb[indx]);
		}
		
		for (int i = 0; i < getChildCount(); i++)
		{
			Group child = getChild(i);
			
			if(child instanceof Joint)
			{
				((Joint) child).draw();
			}
		}
	}
}
