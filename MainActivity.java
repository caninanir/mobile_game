package com.game_prototype.meteor2;

/*This MainActivity implementation initializes the game by setting up the window to full screen, initializing game settings,
game objects, and game states. The GameView is then created and set as the content view of the activity. The onPause() and onResume()
methods handle pausing and resuming the game when the app's lifecycle changes. */

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Initialize game settings
        GameSettings.init(getApplicationContext());

        // Initialize game objects and states
        initGameObjects();
        initGameStates();

        // Initialize the game view
        gameView = new GameView(this);
        setContentView(gameView);
    }

    private void initGameObjects() {
        // Initialize player, enemy, powerup, and other game objects here
    }

    private void initGameStates() {
        // Initialize main menu, play, and game over states here
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
