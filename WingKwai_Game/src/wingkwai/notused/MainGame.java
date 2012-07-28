//package wingkwai.notused;
//
//import android.os.Bundle;
//import android.view.Window;
//import android.view.WindowManager;
//import plia.core.Game;
//import plia.core.event.TouchEvent;
//
//public class MainGame extends Game
//{
//	private MainMenu mainMenu;
//	private RaceScene raceScene;
//
//	public void onInitialize(Bundle arg0)
//	{
//		setRequestedOrientation(0);
//		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		
//		mainMenu = new MainMenu(this);
//		raceScene = new RaceScene();
//		
//		setScene(mainMenu);
//	}
//
//	public void onUpdate()
//	{
//		
//	}
//
//	@Override
//	public void onTouchEvent(int action, float x, float y)
//	{
//		// TODO Auto-generated method stub
//		super.onTouchEvent(action, x, y);
//		
//		log(x+", "+y);
//		
//		if(action == TouchEvent.ACTION_UP && getScene() instanceof RaceScene)
//		{
//			setScene(mainMenu);
//		}
//	}
//	
//	public MainMenu getMainMenuScene()
//	{
//		return mainMenu;
//	}
//	
//	public RaceScene getRaceScene()
//	{
//		return raceScene;
//	}
//}
