package plia.framework.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture2D
{
	private Bitmap bitmapSrc;
	private int textureBuffer;
	private int width;
	private int height;
	
	public Texture2D(Bitmap src)
	{
		this.bitmapSrc = src;
		this.width = bitmapSrc.getWidth();
		this.height = bitmapSrc.getHeight();
		
		// Gen Texture
		//
		int[] buffers = new int[1];
		GLES20.glGenTextures(1, buffers, 0);
		
		// generate color texture
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, buffers[0]);

		// parameters
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, src, 0);
		
		GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}
	
	public Texture2D(int texBuffer, int width, int height)
	{
		this.textureBuffer = texBuffer;
		this.width = width;
		this.height = height;
		
		// Create Bitmap
		//
		IntBuffer ib = ByteBuffer.allocateDirect(width * height * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texBuffer);
		GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, ib);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		bitmapSrc = Bitmap.createBitmap(ib.array(), width, height, Config.RGB_565);
	}
	
	public int getTextureBuffer()
	{
		return textureBuffer;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Color4 getPixelColor(int x, int y)
	{
		int pixel = bitmapSrc.getPixel(x, y);
		
		float r = Color.red(pixel) / 255f;
		float g = Color.green(pixel) / 255f;
		float b = Color.blue(pixel) / 255f;
		float a = Color.alpha(pixel) / 255f;
		
		return new Color4(r, g, b, a);
	}
}
