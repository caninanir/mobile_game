package com.game_prototype.meteor2;

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

