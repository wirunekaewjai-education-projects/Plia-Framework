package plia.framework.scene.group.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Box
{
	private float[] vertices = 
		{
			-0.5f, -0.5f, -0.191843f, 0.5f, -0.5f, -0.191843f, -0.5f, 0.5f, -0.191843f, 0.5f, 0.5f, -0.191843f, -0.5f, -0.5f, 0.80815697f, 0.5f, -0.5f, 0.80815697f, -0.5f, 0.5f, 0.80815697f, 
			0.5f, 0.5f, 0.80815697f, -0.5f, -0.5f, -0.191843f, 0.5f, 0.5f, -0.191843f, -0.5f, 0.5f, -0.191843f, -0.5f, -0.5f, 0.80815697f, 0.5f, 0.5f, 0.80815697f, 0.5f, -0.5f, 
			0.80815697f, 0.5f, -0.5f, 0.80815697f, 0.5f, -0.5f, -0.191843f, 0.5f, 0.5f, 0.80815697f, 0.5f, 0.5f, -0.191843f, -0.5f, 0.5f, 0.80815697f, -0.5f, 0.5f, -0.191843f, -0.5f, 
			-0.5f, -0.191843f, -0.5f, -0.5f, -0.191843f, 0.5f, -0.5f, -0.191843f, 0.5f, 0.5f, -0.191843f, -0.5f, -0.5f, 0.80815697f, -0.5f, 0.5f, 0.80815697f, 0.5f, 0.5f, 0.80815697f, 
			-0.5f, -0.5f, 0.80815697f, 0.5f, -0.5f, 0.80815697f, 0.5f, 0.5f, 0.80815697f, -0.5f, 0.5f, 0.80815697f, 
		};
	
	private float[] uv = 
		{
			0.99950045f, 0.99950045f, 0.99950045f, 0.99950045f, 0.99950045f, 0.99950045f, 0.99950045f, 0.99950045f, 4.9978495E-4f, 4.995465E-4f, 0.99950045f, 4.995465E-4f, 0.99950045f, 4.995465E-4f, 0.99950045f, 4.995465E-4f, 4.995465E-4f, 0.9995002f, 0.99950045f, 4.9978495E-4f, 4.9978495E-4f, 
			4.995465E-4f, 4.994273E-4f, 4.9984455E-4f, 0.99950045f, 0.99950016f, 0.99950016f, 4.994869E-4f, 4.9978495E-4f, 4.995465E-4f, 4.9978495E-4f, 0.99950045f, 4.9978495E-4f, 4.995465E-4f, 4.9978495E-4f, 0.99950045f, 4.9978495E-4f, 4.995465E-4f, 4.9978495E-4f, 0.99950045f, 4.9978495E-4f, 
			0.99950045f, 4.995465E-4f, 0.9995002f, 0.9995002f, 0.99950045f, 0.99950045f, 4.9978495E-4f, 4.994273E-4f, 4.9984455E-4f, 4.9984455E-4f, 0.9995005f, 0.99950045f, 0.99950016f, 0.99950045f, 4.995465E-4f, 4.9978495E-4f, 4.995465E-4f, 4.9978495E-4f, 4.995465E-4f, 4.9978495E-4f, 
			4.995465E-4f, 
		};
	
	private int[] indices = 
		{
			8, 9, 10, 11, 12, 13, 0, 14, 15, 1, 16, 17, 3, 18, 19, 2, 4, 20, 21, 22, 23, 
			24, 25, 26, 0, 27, 28, 1, 5, 29, 3, 7, 30, 2, 6, 4, 
		};
	
	private float[] materials = 
		{
			4,3,2,5,0,1,
			4,3,2,5,0,1
		};
	
	private static FloatBuffer vb;
	private static FloatBuffer uvb;
	private static IntBuffer ib;
	private static FloatBuffer mb;
	
	private Box()
	{
		vb = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vb.put(vertices).position(0);

		uvb = ByteBuffer.allocateDirect(uv.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		uvb.put(uv).position(0);
		
		mb = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		mb.put(materials).position(0);
		
		ib = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		ib.put(indices).position(0);
	}
	
	public static FloatBuffer getVB()
	{
		return vb;
	}

	public static FloatBuffer getUVB()
	{
		return uvb;
	}
	
	public static IntBuffer getIB()
	{
		return ib;
	}
	
	public static FloatBuffer getMB()
	{
		return mb;
	}
	
	private static Box instance = new Box();
	public static Box getInstance()
	{
		return instance;
	}
}
