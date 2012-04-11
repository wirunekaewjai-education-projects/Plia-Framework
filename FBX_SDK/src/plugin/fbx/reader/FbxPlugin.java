package plugin.fbx.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import plugin.fbx.Queue;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FbxPlugin
{
	private Context context;
	public String tag;
	public String line;
	public FbxStateMachine fsm;
	
	public Queue<String> raw;
	public FbxData data;
	
	public FbxPlugin(Context context)
	{
		this.context = context;
		this.tag = TAGS[0];
		this.fsm = new FbxStateMachine(this, DocumentsDescription.instance);
		this.data = new FbxData();
	}
	
	private Queue<String> getData(String file)
	{
		BufferedReader br = null;

		String line = "";

		Queue<String> data = new Queue<String>();

		int choice = 0;
		
		if(file.contains(Environment.getExternalStorageDirectory().getAbsolutePath()))
		{
			choice = 1;
		}
		
		try
		{
			InputStreamReader isr = null;
			
			if(choice == 0)
			{
				isr = new InputStreamReader(context.getAssets().open(file));
			}
			else
			{
				isr = new InputStreamReader(new FileInputStream(file));
			}
			
			br = new BufferedReader(isr, 8*1024);

			while ((line = br.readLine()) != null)
			{
				data.queue(line);
			}
			
			isr.close();
			br.close();

		} catch (IOException e)
		{
			Log.e("Error", e.getMessage());
		}
		
		return data;
	}
	
	public void load(String file)
	{
		long start = System.currentTimeMillis();

		raw = getData(file);
		
		while(raw.size() > 0)
		{
			fsm.action();
		}

		Log.e("Geometry", data.geometries.size()+"");
		
		long end = System.currentTimeMillis() - start;
		Log.e("Usage Time", end + " ms");
	}
	
	static String[] TAGS = 
		{
			"Documents Description",
			"Document References",
			"Object definitions",
			"Object properties",
			"Object connections",
			"Takes section",
		};
	static String PROPERTIES_REGEX = "\"\\,*\\W*";
}
