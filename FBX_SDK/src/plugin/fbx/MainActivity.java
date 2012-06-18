package plugin.fbx;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.State;

import library.graphics.Mesh;
import library.graphics.Renderer;

import plugin.fbx.reader.FbxFile;
import plugin.fbx.reader.FbxPlugin;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        GLSurfaceView gl = new GLSurfaceView(this);
        
        gl.setEGLContextClientVersion(2);
        gl.setRenderer(new Renderer(this));
        
        setContentView(gl);
        
        
    }
  
    
    public static void print(Object value)
	{
		Log.e("", value.toString());
	}
    
}