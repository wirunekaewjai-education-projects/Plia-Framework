package plia.game;

import static android.opengl.GLES20.*;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.widget.FrameLayout;

public abstract class Game extends FrameLayout implements IGame, Renderer
{
	private GLSurfaceView glSurfaceView;
	private ArrayList<Scene> children;
	
	public Game(Context context)
	{
		super(context);
		
		this.children = new ArrayList<Scene>();
		
		this.glSurfaceView = new GLSurfaceView(context);
		this.glSurfaceView.setEGLContextClientVersion(2);
		this.glSurfaceView.setRenderer(this);
		
		this.addView(glSurfaceView);
	}

	@Override
	public final void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		glClearColor(0.392f, 0.584f, 0.93f, 1);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		
		initialize();
	}
	
	@Override
	public final void onSurfaceChanged(GL10 gl, int width, int height)
	{
		
		
	}
	
	@Override
	public final void onDrawFrame(GL10 gl)
	{
		update();
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public boolean addChild(Scene child)
	{
		if(!children.contains(child))
		{
			children.add(child);
			return true;
		}
		
		return false;
	}
	
	public boolean removeChild(Scene child)
	{
		if(children.contains(child))
		{
			children.remove(child);
			return true;
		}
		
		return false;
	}
}

interface IGame
{
	void initialize();
	void update();
}