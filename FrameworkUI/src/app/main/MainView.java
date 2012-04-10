package app.main;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import plia.framework.ViewController;

public class MainView extends ViewController implements OnClickListener
{
	Database db;
	TextView tv1, tv2;

	public MainView(Context context, int layoutResID)
	{
		super(context, layoutResID);
		
		db = new Database();
		db.registerObserver(this);
		
		tv1 = (TextView)findViewById(R.id.text1);
		tv2 = (TextView)findViewById(R.id.text2);
		
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
	}

	@Override
	public void update(ViewController observer, Object... objects)
	{
		if (this == observer)
		{
			tv1.setText(objects[0].toString());
			tv2.setText(objects[1].toString());
		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.button1:
			db.updateValue(0);
			break;
		case R.id.button2:
			db.updateValue(1);
			break;

		default:
			break;
		}
		
	}
	
	
}
