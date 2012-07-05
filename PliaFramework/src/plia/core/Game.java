package plia.core;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import plia.math.Vector2;
import plia.math.Vector3;
import plia.math.Vector4;
import plia.scene.Button;
import plia.scene.Camera;
import plia.scene.Group;
import plia.scene.Light;
import plia.scene.PlaneCollider;
import plia.scene.Scene;
import plia.scene.SkyBox;
import plia.scene.SkyDome;
import plia.scene.SphereCollider;
import plia.scene.Sprite;
import plia.scene.Terrain;
import plia.scene.shading.Color3;
import plia.scene.shading.Color4;
import plia.scene.shading.Shader;
import plia.scene.shading.Texture2D;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public abstract class Game extends Activity implements IFramework
{
	private GameObjectManager gameObjectManager;
	private AnimationPlayer animationPlayer;
	private TouchEventManager touchEventManager;
	private GameTime gameTime;
	
//	private Debug debug;
	
	private GLSurfaceView glSurfaceView;

	private boolean isResumed = false;
	private boolean isStarted = false;
	
	public static boolean enabledDebug = true;

	@Override
	protected final void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		instance = this;
		
		this.gameObjectManager = GameObjectManager.getInstance();
		this.gameObjectManager.setContext(this);
		this.gameObjectManager.initialize();
		
		this.animationPlayer = AnimationPlayer.getInstance();
		
		this.touchEventManager = TouchEventManager.getInstance();

		this.gameTime = GameTime.getInstance();
		
//		this.debug = Debug.getInstance();

		Scene.setMainCamera(new Camera("Main Camera"));
		
		this.onInitialize(savedInstanceState);
		this.glSurfaceView = new GLSurfaceView(this);
		this.glSurfaceView.setEGLContextClientVersion(2);
		this.glSurfaceView.setRenderer(new GLRenderer());
		this.setContentView(glSurfaceView);
	}
	
	private void checkStart()
	{
		if(isStarted)
		{
			Shader.warmUpAllShader();
			gameObjectManager.start();
			isStarted = false;
		}
	}

	private void checkResume()
	{
		if(isResumed)
		{
			gameObjectManager.resume();
			isResumed = false;
		}
	}

	private void update()
	{
		touchEventManager.update();
		animationPlayer.update();
		
		onUpdate();
		
		if(currentScene != null)
		{
			gameTime.update();
			currentScene.update();
		}
	}
	
	private void draw()
	{
		if(currentScene != null)
		{
			currentScene.drawScene();
		}
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		this.gameTime.start();
		this.isStarted = true;
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		this.glSurfaceView.onPause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		this.glSurfaceView.onResume();
		this.isResumed = true;
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		this.gameObjectManager.destroy();
		this.touchEventManager.removeAll();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.touchEventManager.onTouchEvent(event);
		return true;
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		Log.e("State", "Back");
	}
	
	private class GLRenderer implements Renderer
	{
		public void onDrawFrame(GL10 gl)
		{
//			Log.e("FPS", (1000f / GameTime.getElapsedGameTime().getMilliseconds())+"");

			checkStart();
//			checkInit();
			checkResume();

			GLES20.glViewport(0, 0, Screen.getWidth(), Screen.getHeight());
			GLES20.glClearColor(0.3f, 0.6f, 0.9f, 1);
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
			
			update();
			
			draw();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			Screen.w = width;
			Screen.h = height;
			
			Scene.onSurfaceChanged();
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{

		}
		
	}

	private Scene currentScene;
	
//	private static Game instance;

	public void setScene(Scene scene)
	{
		currentScene = scene;
	}
	
	///
	public static final Group model(String fbx_path)
	{
		return GameObjectManager.loadModel(fbx_path);
	}
	
	public static final Terrain terrain(String heightmapSrc, int maxHeight, int scale)
	{
		return GameObjectManager.createTerrain(heightmapSrc, maxHeight, scale);
	}
	
	public static final Terrain terrain(String heightmapSrc, String diffusemapSrc, int maxHeight, int scale)
	{
		Terrain t = GameObjectManager.createTerrain(heightmapSrc, maxHeight, scale);
		t.setBaseTexture(GameObjectManager.loadTexture2D(diffusemapSrc));
		
		return t;
	}
	
	public static final Light directionalLight(float intensity)
	{
		Light light = new Light();
		light.setIntensity(intensity);
		return light;
	}
	
	public static final Light directionalLight(float forwardX, float forwardY, float forwardZ)
	{
		Light light = new Light();
		light.setForward(forwardX, forwardY, forwardZ);

		return light;
	}
	
	public static final Light directionalLight(float forwardX, float forwardY, float forwardZ, float intensity)
	{
		Light light = new Light();
		light.setForward(forwardX, forwardY, forwardZ);
		light.setIntensity(intensity);
		
		return light;
	}
	
	public static final Light directionalLight(float forwardX, float forwardY, float forwardZ, float red, float green, float blue)
	{
		Light light = new Light();
		light.setForward(forwardX, forwardY, forwardZ);
		light.setColor(red, green, blue);

		return light;
	}
	
	public static final Light directionalLight(float forwardX, float forwardY, float forwardZ, float intensity, float red, float green, float blue)
	{
		Light light = new Light();
		light.setForward(forwardX, forwardY, forwardZ);
		light.setColor(red, green, blue);
		light.setIntensity(intensity);
		
		return light;
	}
	
	public static final Light pointLight(float posX, float posY, float posZ, float range)
	{
		Light light = new Light();
		light.setLightType(Light.POINT_LIGHT);
		light.setRange(range);
		light.setPosition(posX, posY, posZ);

		return light;
	}
	
	public static final Light pointLight(float posX, float posY, float posZ, float range, float intensity)
	{
		Light light = new Light();
		light.setLightType(Light.POINT_LIGHT);
		light.setRange(range);
		light.setPosition(posX, posY, posZ);
		light.setIntensity(intensity);
		
		return light;
	}
	
	public static final Light pointLight(float posX, float posY, float posZ, float range, float red, float green, float blue)
	{
		Light light = new Light();
		light.setLightType(Light.POINT_LIGHT);
		light.setRange(range);
		light.setPosition(posX, posY, posZ);
		light.setColor(red, green, blue);

		return light;
	}
	
	public static final Light pointLight(float posX, float posY, float posZ, float range, float intensity, float red, float green, float blue)
	{
		Light light = new Light();
		light.setLightType(Light.POINT_LIGHT);
		light.setRange(range);
		light.setPosition(posX, posY, posZ);
		light.setColor(red, green, blue);
		light.setIntensity(intensity);
		
		return light;
	}
	
	public static final Camera camera(int projectionType)
	{
		Camera camera = new Camera();
		camera.setProjectionType(projectionType);
		
		return camera;
	}
	
	public static final Camera camera(int projectionType, float range)
	{
		Camera camera = new Camera();
		camera.setProjectionType(projectionType);
		camera.setRange(range);
		
		return camera;
	}
	
	public static final Camera camera(int projectionType, float posX, float posY, float posZ, float range)
	{
		Camera camera = new Camera();
		camera.setProjectionType(projectionType);
		camera.setPosition(posX, posY, posZ);
		camera.setRange(range);
		
		return camera;
	}
	
	public static final Camera camera(int projectionType, float posX, float posY, float posZ, float targetX, float targetY, float targetZ, float range)
	{
		Camera camera = new Camera();
		camera.setProjectionType(projectionType);
		camera.setPosition(posX, posY, posZ);
		camera.setLookAt(new Vector3(targetX, targetY, targetZ));
		camera.setRange(range);
		
		return camera;
	}
	
	public static final SkyDome skydome(String textureSrc)
	{
		SkyDome dome = new SkyDome();
		dome.setTexture(tex2D(textureSrc));
		
		return dome;
	}
	
	public static final SkyBox skybox(String src)
	{
		int indexOfDot = src.lastIndexOf(".");
		
		String s1 = src.substring(0, indexOfDot);
		String s2 = src.substring(indexOfDot, src.length());
		
		String frontSrc  = s1+"_front"+s2;
		String backSrc 	 = s1+"_back"+s2;
		String leftSrc 	 = s1+"_left"+s2;
		String rightSrc  = s1+"_right"+s2;
		String topSrc 	 = s1+"_top"+s2;
		String bottomSrc = s1+"_bottom"+s2;
		
		Log.e("", frontSrc+", "+backSrc+", "+leftSrc+", "+rightSrc+", "+topSrc+", "+bottomSrc);
		
		
		return new SkyBox(tex2D(frontSrc), tex2D(backSrc), tex2D(leftSrc), tex2D(rightSrc), tex2D(topSrc), tex2D(bottomSrc));
	}
	
	public static final SkyBox skyBox(String frontSrc, String backSrc, String leftSrc, String rightSrc, String topSrc, String bottomSrc)
	{
		return new SkyBox(tex2D(frontSrc), tex2D(backSrc), tex2D(leftSrc), tex2D(rightSrc), tex2D(topSrc), tex2D(bottomSrc));
	}
	
	public static final Sprite sprite(String imgSrc)
	{
		return GameObjectManager.createSprite(imgSrc);
	}
	
	public static final Sprite sprite(String imgSrc, int frame)
	{
		Sprite sprite = new Sprite();
		sprite.setImageSrc(GameObjectManager.loadTexture2D(imgSrc), frame);
		
		return sprite;
	}
	
	public static final Button button()
	{
		return new Button();
	}
	
	public static final Button button(String imgSrc)
	{
		return GameObjectManager.createButton(imgSrc);
	}
	
	public static final Button button(String imgSrc, int frame)
	{
		Button sprite = new Button();
		sprite.setImageSrc(GameObjectManager.loadTexture2D(imgSrc), frame);
		
		return sprite;
	}
	
	public static final Texture2D tex2D(String path)
	{
		return GameObjectManager.loadTexture2D(path);
	}
	
	public static final PlaneCollider collider(float upX, float upY, float upZ, float scaleX, float scaleY)
	{
		PlaneCollider boundingPlane = new PlaneCollider();
		boundingPlane.setScale(scaleX, scaleY, 0);
		boundingPlane.setUp(upX, upY, upZ);
		return boundingPlane;
	}
	
	public static final PlaneCollider collider(Vector3 up, Vector2 scale)
	{
		PlaneCollider boundingPlane = new PlaneCollider();
		boundingPlane.setScale(scale.x, scale.y, 0);
		boundingPlane.setUp(up);
		return boundingPlane;
	}
	
	public static final SphereCollider collider(float radius)
	{
		return new SphereCollider(radius);
	}
	
	//
	public static final Vector2 vec2()
	{
		return new Vector2();
	}
	
	public static final Vector2 vec2(float value)
	{
		return new Vector2(value, value);
	}
	
	public static final Vector2 vec2(float x, float y)
	{
		return new Vector2(x, y);
	}
	
	public static final Vector3 vec3()
	{
		return new Vector3();
	}
	
	public static final Vector3 vec3(float value)
	{
		return new Vector3(value, value, value);
	}
	
	public static final Vector3 vec3(float x, float y, float z)
	{
		return new Vector3(x, y, z);
	}
	
	public static final Vector4 vec4()
	{
		return new Vector4();
	}
	
	public static final Vector4 vec4(float value)
	{
		return new Vector4(value, value, value, value);
	}
	
	public static final Vector4 vec4(float x, float y, float z, float w)
	{
		return new Vector4(x, y, z, w);
	}
	
	public static final Color3 color(float r, float g, float b)
	{
		return new Color3(r, g, b);
	}
	
	public static final Color4 color(float r, float g, float b, float a)
	{
		return new Color4(r, g, b, a);
	}
}

interface IFramework
{
	void onInitialize(Bundle savedInstanceState);
	void onUpdate();
}
