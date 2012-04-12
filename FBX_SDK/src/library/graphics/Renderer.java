package library.graphics;

import static android.opengl.GLES20.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import plugin.fbx.R;
import plugin.fbx.reader.FbxFile;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class Renderer implements GLSurfaceView.Renderer
{
	private Context context = null;
	private Shader shader = null;
	
	private Mesh m;
	
	// FPS
	private short timeCount;
	private long startCountTime;

	private float fps;
	
	public Renderer(Context context)
	{
		this.context = context;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		glClearColor(0.392f, 0.584f, 0.93f, 1);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		
		shader = new Shader(R.raw.gouraud2_vs, R.raw.gouraud2_fs, context);
		
		FbxFile fbx = new FbxFile(context, "elementalist3.FBX");
        m = fbx.createMesh();
        m.setTexture2D(new Texture2D(context, "mytex.png"));
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		glViewport(0, 0, width, height);
		float ratio = (float) width/height;
		
		Matrix.frustumM(projection, 0, -ratio, ratio, -1, 1, 1, 5000);
	}

	float angle;
	float y, dir = 1;
	@Override
	public void onDrawFrame(GL10 arg0)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		angle++;
//		if(y > 5 || y < -5)
//			dir*=-1;
//		
//		y+=dir*0.03f;
		
//		float[] eye = new float[3];
//		eye[0] = (float) Math.sin(Math.toRadians(angle))*8;
//		eye[1] = y;
//		eye[2] = (float) Math.cos(Math.toRadians(angle))*8;
		
		float[] eye = { 15,15,20 };
		
		Matrix.setLookAtM(modelView, 0, eye[0], eye[1], eye[2], 0, 0, 0, 0, 1, 0);
		Matrix.multiplyMM(mvpMatrix, 0, projection, 0, modelView, 0);
		
		int program = shader.getProgram();
		glUseProgram(program);
		glUniform3fv(glGetUniformLocation(program, "eyePosition"), 1, eye, 0);

		glUniformMatrix4fv(glGetUniformLocation(program, "light"), 3, false, lights, 0);
		
		m.rotate(1, 0, 1, 0);
		m.update();
		m.render(program, modelView, projection);
		// FPS
		timeCount++;

		if(timeCount >= 10)
		{
			fps = (1000.0f/((System.currentTimeMillis()-startCountTime)/(float)timeCount));
			

			timeCount = 0;
			
			Log.println(Log.ASSERT, "Frame per second", fps+" fps");
			
			startCountTime = System.currentTimeMillis();
		}
	}
	
	
	public void print(float[] m)
	{
		if(m.length == 4)
		{
			Log.println(Log.ASSERT, "", m[0]+", "+m[1]+", "+m[2]+", "+m[3]);
		}
		else if(m.length == 16)
		{
			Log.println(Log.ASSERT, "", "================================");
			Log.println(Log.ASSERT, "", m[0]+", "+m[4]+", "+m[8]+", "+m[12]);
			Log.println(Log.ASSERT, "", m[1]+", "+m[5]+", "+m[9]+", "+m[13]);
			Log.println(Log.ASSERT, "", m[2]+", "+m[6]+", "+m[10]+", "+m[14]);
			Log.println(Log.ASSERT, "", m[3]+", "+m[7]+", "+m[11]+", "+m[15]);
			Log.println(Log.ASSERT, "", "================================");
		}
	}

	float[] lights = 
		{
			10,0,0,0,		1,1,1,1,	1,1,1,1,	40,0,0,0,
			
			-10,0,0,0, 	1,1,1,1,	1,1,1,1,	40,0,0,0,
			
			0,20.0f,0,0,		1,1,1,1,	1,1,1,1,	40,0,0,0,
//			
//			0,-1.2f,0,1,	1,1,1,1,	1,1,1,1,	40,0,0,0,
//			
//			0,0,1.2f,1,		1,1,1,1,	1,1,1,1,	40,0,0,0,
//			
//			0,0,-1.2f,1,	1,1,1,1,	1,1,1,1,	40,0,0,0,
		};
	
	private float[] 
			modelView = new float[16], 
			projection = new float[16], 
			mvpMatrix = new float[16];
}
