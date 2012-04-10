package plia.game;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class GraphicsManager
{
	private Game mGame = null;
	private GraphicsRenderer mRenderer = null;
	
	private FrameLayout mBaseLayout = null;
	private GLSurfaceView mGLSurfaceView = null;
	private FrameLayout mGUILayout = null;
	
	public GraphicsManager(Game game)
	{
		mGame = game;
		
		mBaseLayout = new FrameLayout(game.mContext);
		
		mRenderer = new GraphicsRenderer(game);
		
		mGLSurfaceView = new GLSurfaceView(game.mContext);
		mGLSurfaceView.setEGLContextClientVersion(2);
		mGLSurfaceView.setRenderer(mRenderer);
		mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		mGUILayout = new FrameLayout(game.mContext);
		
		mBaseLayout.addView(mGLSurfaceView);
		mBaseLayout.addView(mGUILayout);
		
		Activity activity = ((Activity)game.mContext);
		
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.setContentView(mBaseLayout);
		
		Log.e("State", "Create Manager");
	}
	
	public void setOrientation(int requestedOrientation)
	{
		((Activity)mGame.mContext).setRequestedOrientation(requestedOrientation);
	}
	
	public Screen getScreen()
	{
		return mRenderer.getScreen();
	}
}
