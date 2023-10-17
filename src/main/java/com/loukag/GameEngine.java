package com.loukag;

import com.loukag.Scene.Scene;

public class GameEngine implements Runnable{

    private static final int TARGET_FPS = 60;
    private static final long TARGET_TIME = 1000000000 / TARGET_FPS;
    private static int FPS = 0;

    /**
     * Returns the current FPS
     * @return the current FPS
     */
    public static int getFPS() {
        return FPS;
    }

    private boolean isRunning = false;
    private Thread thread;


    /**
     * Starts the game engine
     */
    public synchronized void start(){
        if(isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the game engine
     */
    public synchronized void stop(){
        if(!isRunning) return;
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double unprocessedTime = 0;
        int frames = 0;
        int updates = 0;

        while (isRunning) {
            long now = System.nanoTime();
            long elapsedTime = now - lastTime;
            lastTime = now;
            unprocessedTime += elapsedTime;

            while (unprocessedTime >= TARGET_TIME) {
                if(Scene.getCurrentScene() != null)
                    Scene.getCurrentScene().update();
                updates++;
                unprocessedTime -= TARGET_TIME;
            }

            if(Scene.getCurrentScene() != null)
                Scene.getCurrentScene().repaint();

            frames++;

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames + ", Updates: " + updates);
                GameEngine.FPS = frames;
                frames = 0;
                updates = 0;
                timer += 1000;
            }

            try {
                Thread.sleep(1); // Peut être ajusté selon les besoins
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
