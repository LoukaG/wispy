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
        return (int) GameObject.blockToPixel(posX);
    }

    /**
     * Get the y position of the GameObject in pixels
     * @return y position in pixels
     */
    public int getScreenY(){
        return (int) (Chunk.getChunkHeight()* GameScene.getBlockSize()-GameObject.blockToPixel(posY));
    }

    public double distance(int posX, int posY){
        return Math.sqrt(Math.pow(posX - getScreenX(), 2) + Math.pow(posY - getScreenY(), 2));
    }

    /**
     * Convert a block position to a pixel position
     * @param block block position
     * @return pixel position
     */
    public static int blockToPixel(double block){
        return (int) (block* GameScene.getBlockSize());
    }

    /**
     * Convert a pixel position to a block position
     * @param pixel pixel position
     * @return block position
     */
    public static double pixelToBlock(int pixel){
        return (double) (Chunk.getChunkHeight() * GameScene.getBlockSize() - pixel) / GameScene.getBlockSize();
    }


}
