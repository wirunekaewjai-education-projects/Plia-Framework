package plia.library;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;

interface IGame extends Renderer
{
	@Override
	void onSurfaceCreated(GL10 gl, EGLConfig config);
	@Override
	void onSurfaceChanged(GL10 gl, int width, int height);
	@Override
	void onDrawFrame(GL10 gl);
	
	void initialize();
	void update();
	void render();
}
