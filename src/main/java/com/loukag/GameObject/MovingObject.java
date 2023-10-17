package com.loukag.GameObject;

public abstract class MovingObject extends GameObject{

    protected double velX, velY;

    /**
     * Constructor for GameObject
     *
     * @param posX x position (in blocks)
     * @param posY y position (in blocks)
     */
    public MovingObject(double posX, double posY) {
        super(posX, posY);
    }
}
