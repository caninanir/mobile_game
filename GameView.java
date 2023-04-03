package com.game_prototype.meteor2;

/*
* This GameView implementation sets up the game thread and manages the game state.
* It also handles touch events by delegating them to the current game state.
* The update() and draw() methods are called by the game thread to update game logic and render graphics, respectively.
* The setState() method is used to switch between game states. Finally, the pause() and resume()
* methods control the game thread's execution based on the app's lifecycle.
* */


import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private GameState currentState;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        // Initialize game thread
        gameThread = new GameThread(getHolder(), this);

        // Set initial game state
        currentState = new MainMenuState(this);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if necessary
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentState.handleInput(event);
        return true;
    }

    public void update() {
        currentState.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        currentState.draw(canvas);
    }

    public void setState(GameState newState) {
        currentState = newState;
    }

    public void pause() {
        gameThread.setRunning(false);
    }

    public void resume() {
        gameThread.setRunning(true);
    }
}

