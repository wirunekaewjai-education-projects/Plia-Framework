package library.graphics;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.texImage2D;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Texture2D
{
	int[] buffer = new int[1];
	Bitmap bitmap = null;
	
	public Texture2D(Context context, int resID)
	{
		bitmap = BitmapFactory.decodeResource(context.getResources(), resID);
		wrap();
	}
	
	public Texture2D(Context context, String asset)
	{
		InputStream is = null;
		
		try
		{
			is = context.getAssets().open(asset);
			bitmap = BitmapFactory.decodeStream(is);
			wrap();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	        //Always clear and close
	        try {
	            is.close();
	            is = null;
	        } catch (IOException e) {
	        	
	        }
	    }

		
	}
	
	private void wrap()
	{
		glGenTextures(1, buffer, 0);
		glBindTexture(GL_TEXTURE_2D, buffer[0]);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);


		texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
		
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
