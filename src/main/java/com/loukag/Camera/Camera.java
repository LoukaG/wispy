package com.loukag.Camera;

public class Camera {
    private int posX, posY;

    public Camera(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void move(int x, int y){
        posX += x;
        posY += y;
    }

    public void setPos(int x, int y){
        posX = x;
        posY = y;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }
}
