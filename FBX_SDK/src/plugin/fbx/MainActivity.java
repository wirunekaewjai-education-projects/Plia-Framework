package plugin.fbx;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.State;

import plugin.fbx.reader.FbxPlugin;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        FbxPlugin plugin = new FbxPlugin(this);
//		plugin.load("elementalist3.FBX");
		

    }
  
    
    public static void print(Object value)
	{
		Log.e("", value.toString());
	}
    
}