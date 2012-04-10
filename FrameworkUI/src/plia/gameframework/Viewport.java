package plia.gameframework;

public class Viewport
{
	private int mWidth, mHeight;
	private float mRatio;
	
	public Viewport(int width, int height)
	{
		mWidth = width;
		mHeight = height;
		mRatio = (float)width/height;
	}
	
	public float getAspectRatio()
	{
		return mRatio;
	}
	
	public int getWidth()
	{
		return mWidth;
	}
	
	public int getHeight()
	{
		return mHeight;
	}
}
