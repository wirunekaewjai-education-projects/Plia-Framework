package plia.game;

public class Screen
{
	private int mWidth, mHeight;
	private float mRatio;
	private float[] mColor;
	
	public Screen(int width, int height)
	{
		mWidth = width;
		mHeight = height;
		mRatio = (float)width/height;
		
		mColor = new float[3];
		mColor[0] = 0.392f;
		mColor[1] = 0.584f;
		mColor[2] = 0.93f;
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
	
	public float[] getColor()
	{
		return mColor;
	}
	
	public void setColor(float red, float green, float blue)
	{
		mColor[0] = red;
		mColor[1] = green;
		mColor[2] = blue;
	}
}
