package plia.framework.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import plia.framework.gameobject.Scene;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;

public class Framework
{
	private OpenGLSurfaceView surfaceView;
	
	private GameObjectManager gameObjectManager;
	private UpdateManager updateManager;
	private TouchEventManager touchEventManager;
	private TextureManager textureManager;
	private ShaderManager shaderManager;
	
	private GameTime gameTime;
	
	private Scene currentScene;

	private boolean isStarted = false;
	private boolean isStopped = false;

	private Framework()
	{
		
	}
	
	public void linkSurfaceView(OpenGLSurfaceView surfaceView)
	{
		if(this.surfaceView != surfaceView)
		{
			this.surfaceView = surfaceView;
			this.surfaceView.setRenderer(renderer);
			((Activity)this.surfaceView.getContext()).setContentView(surfaceView);
		}
	}
	
	public void setScene(Scene scene)
	{
		this.currentScene = scene;
	}
	
	public void start()
	{
		this.isStarted = false;
		this.isStopped = false;
		
		this.gameObjectManager = GameObjectManager.getInstance();
		this.updateManager = UpdateManager.getInstance();
		this.touchEventManager = TouchEventManager.getInstance();
		this.textureManager = TextureManager.getInstance();
		this.shaderManager = ShaderManager.getInstance();
		this.gameTime = GameTime.getInstance();
	}
	
	public void stop()
	{
		this.isStopped = true;
		
		if(surfaceView.getRenderMode() == GLSurfaceView.RENDERMODE_WHEN_DIRTY)
		{
			onStop();
		}
	}
	
	private void onStop()
	{
		currentScene = null;
		touchEventManager.removeAll();
		updateManager.removeAll();
		gameObjectManager.removeAll();
	}
	
	public void resume()
	{
		this.surfaceView.onResume();
	}
	
	public void pause()
	{
		this.surfaceView.onPause();
	}
	
	// //
	private Renderer renderer = new Renderer()
	{
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			GLES20.glDisable(GLES20.GL_DITHER);
			GLES20.glClearDepthf(1.0f);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
			GLES20.glDepthFunc(GLES20.GL_LEQUAL);
		}
		
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			
		}
		
		public void onDrawFrame(GL10 gl)
		{
			gameTime.update();
			
			if(!isStarted)
			{
//				long start = System.nanoTime();
				shaderManager.start(true);
				textureManager.setContext(surfaceView.getContext());
				gameObjectManager.setContext(surfaceView.getContext());
//				long end = System.nanoTime() - start;
//				Log.e("Usage Time to Create Shader", (end / 1000000f)+" ms");
				
				isStarted = true;
			}
			
			if(!isStopped)
			{
				GLES20.glClearColor(0.39f, 0.58f, 0.94f, 1);
				GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
				
				touchEventManager.update();
				
				if(currentScene != null)
				{
					currentScene.update();
				}
				
				gameObjectManager.render();
			}
			else
			{
				onStop();
			}
		}
	};
	
	// //
	private static Framework instance = new Framework();
	public static Framework getInstance()
	{
		return instance;
	}
}
