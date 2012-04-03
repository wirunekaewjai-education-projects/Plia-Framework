package app.main;

import plia.framework.ViewController;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends ViewController implements OnClickListener
{
	Database db;
	TextView tv1, tv2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        db = new Database();
        db.registerObserver(this);
        
        tv1 = (TextView)findViewById(R.id.text1);
        tv2 = (TextView)findViewById(R.id.text2);

    }

	@Override
	public void update(ViewController controller, Object... objects)
	{
		if(controller == this)
		{
			tv1.setText(objects[0].toString());
			tv2.setText(objects[1].toString());
		}
		
	}

	@Override
	public void onClick(View v)
	{
		Log.e("OnClick View ID", v.getId()+"");
		
		switch(v.getId())
		{
			case R.id.button1 : db.updateValue(0); 
								this.registerTouchHoldEvent(R.id.text1);
								break;
			case R.id.button2 : db.updateValue(1); 
								this.unregisterTouchHoldEvent(R.id.text1);
								break;
		}
		
	}
	
	@Override
	public void onTouchHoldEvent(View v)
	{
		// TODO Auto-generated method stub
		super.onTouchHoldEvent(v);
		
		Log.e("Holding", v.getId()+"");
	}
}