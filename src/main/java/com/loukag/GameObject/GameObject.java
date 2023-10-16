package com.loukag.GameObject;

import com.loukag.Map.Chunk;
import com.loukag.Scene.GameScene;

import java.awt.*;

public abstract class GameObject {

    protected double posX, posY;

    /**
     * Constructor for GameObject
     * @param posX x position (in blocks)
     * @param posY y position (in blocks)
     */
    public GameObject(double posX, double posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Render the GameObject
     * @param g Graphics2D object
     */
    public abstract void render(Graphics2D g);

    /**
     * Update the GameObject. Called 60 times per second
     */
    public abstract void update();

    /**
     * Get the x position of the GameObject in blocks
     * @return x position in blocks
     */
    public double getPosX(){
        return posX;
    }

    /**
     * Get the y position of the GameObject in blocks
     * @return y position in blocks
     */
    public double getPosY(){
        return posY;
    }

    /**
     * Get the x position of the GameObject in pixels
     * @return x position in pixels
     */
    public int getScreenX(){
        return (int) (posX* GameScene.getBlockSize());
    }

    /**
     * Get the y position of the GameObject in pixels
     * @return y position in pixels
     */
    public int getScreenY(){
        return (int) ((int) Chunk.getChunkHeight()* GameScene.getBlockSize()-((posY * GameScene.getBlockSize())));
    }


}
