package app.main;

import plia.framework.Framework;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Framework framework = new Framework(this)
		{
			
			@Override
			public void update()
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void initialize()
			{
				// TODO Auto-generated method stub
				
			}
		};
		framework.start();
    }

}