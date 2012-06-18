package plia.framework.scene.obj3d.geometry;

public class Geometry
{
	private int type;
	protected int[] buffers = new int[10];

	public Geometry()
	{
		
	}
	
	public void setBuffer(int index, int value)
	{
		this.buffers[index] = value;
	}
	
	public int getBuffer(int index)
	{
		return buffers[index];
	}
}
