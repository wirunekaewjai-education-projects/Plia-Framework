package plia.framework;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import plia.framework.debug.Debug;
import plia.framework.graphics.Shader;
import plia.framework.math.Matrix;
import plia.framework.scene.Camera;
import plia.framework.scene.Scene;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;

public abstract class Game extends Activity implements IFramework
{
	private GameObjectManager gameObjectManager;
	private GameTime gameTime;
	
//	private Debug debug;
	
	private GLSurfaceView glSurfaceView;
	private boolean isInitialized = false;
	private boolean isLoadedShader = false;
	
	public static boolean enabledDebug = true;

	@Override
	protected final void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		instance = this;
		
		this.gameObjectManager = GameObjectManager.getInstance();
		this.gameObjectManager.setContext(this);
		this.gameObjectManager.initialize();

		this.gameTime = GameTime.getInstance();
		
		this.debug = Debug.getInstance();

		Scene.mainCamera = new Camera("Main Camera");
		
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
	}
	
	private class GLRenderer implements Renderer
	{
		private float ratio;

		public void onDrawFrame(GL10 gl)
		{
//			Log.e("FPS", (1000f / GameTime.getElapsedGameTime().getMilliseconds())+"");

			if(!isInitialized)
			{
				if(currentScene != null)
				{
					currentScene.initialize();
				}
				isInitialized = true;
			}
			
			GLES20.glClearColor(0.3f, 0.6f, 0.9f, 1);
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

			Camera mainCamera = Scene.mainCamera;
			
			if(mainCamera.getProjectionType() == Camera.PERSPECTIVE)
			{
				Matrix.createFrustum(Scene.getProjectionMatrix(), -ratio, ratio, -1, 1, 1, Scene.mainCamera.getRange());
			}
			else
			{
				Matrix.createOrtho(Scene.getProjectionMatrix(), -ratio, ratio, -1, 1, 1, 10);
			}
			
			if(currentScene != null)
			{
				gameTime.update();
				currentScene.update();
				currentScene.drawScene();
			}
		}

		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			ratio = (float)width/ height;
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			if(!isLoadedShader)
			{
				Shader.loadAllShader();
				isLoadedShader = true;
			}
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
