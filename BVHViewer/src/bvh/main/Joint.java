package bvh.main;

import plia.core.debug.Debug;
import plia.core.scene.Group;
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
	
	private static Vector3[] drawPoints = new Vector3[6];
	
	private static Color3 color = new Color3(1, 1, 1);
	private static Color3[] rgb = { new Color3(1, 0, 0), new Color3(0, 1, 0), new Color3(0, 0, 1)};
	
	public Joint(String name)
	{
		setName(name);
	}
	
	public void draw()
	{
		for (int i = 0; i < 6; i++)
		{
			drawPoints[i] = new Vector3(Matrix4.multiply(getWorldMatrix(), points[i]));
		}
		
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
