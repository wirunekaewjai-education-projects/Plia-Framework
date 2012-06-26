package plia.framework.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import plia.framework.debug.Debug;
import plia.framework.scene.Camera;
import plia.framework.scene.Scene;
import plia.framework.scene.group.shading.Shader;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public abstract class Game extends Activity implements IFramework
{
	private GameObjectManager gameObjectManager;
	private AnimationPlayer animationPlayer;
	private GameTime gameTime;
	
	private Debug debug;
	
	private GLSurfaceView glSurfaceView;
	private boolean isInitialized = false;
	private boolean isResumed = false;
	
	public static boolean enabledDebug = true;

	@Override
	protected final void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		instance = this;
		
		this.gameObjectManager = GameObjectManager.getInstance();
		this.gameObjectManager.setContext(this);
		
		this.animationPlayer = AnimationPlayer.getInstance();

		this.gameTime = GameTime.getInstance();
		
		this.debug = Debug.getInstance();

		Scene.setMainCamera(new Camera("Main Camera"));
		
		this.onInitialize(savedInstanceState);
		this.glSurfaceView = new GLSurfaceView(this);
		this.glSurfaceView.setEGLContextClientVersion(2);
		this.glSurfaceView.setRenderer(new GLRenderer());
		this.setContentView(glSurfaceView);
	}
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		this.gameTime.start();
	}
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		this.glSurfaceView.onPause();
	}
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		this.glSurfaceView.onResume();
		this.isResumed = true;
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		this.gameObjectManager.destroy();
	}
	
	private class GLRenderer implements Renderer
	{
		public void onDrawFrame(GL10 gl)
		{
//			Log.e("FPS", (1000f / GameTime.getElapsedGameTime().getMilliseconds())+"");

			if(!isInitialized)
			{
				gameObjectManager.initialize();
				
				if(currentScene != null)
				{
					currentScene.initialize();
				}
				
				isInitialized = true;
			}
			
			if(isResumed)
			{
				Shader.warmUpAllShader();
				gameObjectManager.resume();
				isResumed = false;
			}
			
			GLES20.glClearColor(0.3f, 0.6f, 0.9f, 1);
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			
			if(currentScene != null)
			{
				gameTime.update();
				currentScene.update();
				currentScene.drawScene();
			}
			
			animationPlayer.update();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			Screen.w = width;
			Screen.h = height;
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			
		}
		
	}

	private Scene currentScene;
	
	private static Game instance;
	
	public static void runWithScene(Scene scene)
	{
		instance.currentScene = scene;
		instance.isInitialized = false;
	}
}

interface IFramework
{
	void onInitialize(Bundle savedInstanceState);
}
