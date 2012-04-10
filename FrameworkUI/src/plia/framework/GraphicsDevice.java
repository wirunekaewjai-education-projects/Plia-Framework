package plia.framework;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GraphicsDevice implements Renderer, Runnable
{
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{

	}

	@Override
	public void onDrawFrame(GL10 gl)
	{

	}

	@Override
	public void run()
	{
		
	}
}
