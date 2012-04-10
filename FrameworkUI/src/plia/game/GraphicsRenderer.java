package plia.game;

import static android.opengl.GLES20.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

public class GraphicsRenderer implements Renderer
{
	private Game mGame;
	private Screen mScreen;
	
	private boolean isFirst;
	
	public GraphicsRenderer(Game game)
	{
		mGame = game;
		isFirst = true;
	}
	
	public Screen getScreen()
	{
		return mScreen;
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		mScreen = new Screen(width, height);
	}
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		if(isFirst)
		{
			mGame.initialize();
			isFirst = false;
		}
		
		
		mGame.update();
		
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		glViewport(0, 0, mScreen.getWidth(), mScreen.getHeight());

		float[] color = mScreen.getColor();
		glClearColor(color[0], color[1], color[2], 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		mGame.render();
		
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_CULL_FACE);
		
	}
}
