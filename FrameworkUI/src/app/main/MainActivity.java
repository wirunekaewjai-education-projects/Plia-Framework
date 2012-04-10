package app.main;

import plia.game.Game;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
//        Game game = new Game(this);
//        game.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        game.start();
        
        Game.startGame(this, Game1.class);
    }

}