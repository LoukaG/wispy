package com.loukag.Utils;

import java.awt.image.BufferedImage;

public class Animator{

    private final BufferedImage[] spritesheet;
    private final int speed;
    private int index, size;
    private int count;

    /**
     * Creates an animator object. This object is used to automatically cycle through a spritesheet.
     * @param spritesheet The list of images to cycle through.
     * @param speed The speed at which to cycle through the spritesheet. 1 is the fastest, 60+ is the slowest.
     */
    public Animator(BufferedImage[] spritesheet, int speed){
        this.spritesheet = spritesheet;
        this.speed = speed;
        this.index = 0;
        this.count = 0;
        this.size = spritesheet.length;
    }

    /**
     * Creates an animator object. This object is used to automatically cycle through a spritesheet.
     * @param size The size of the spritesheet.
     * @param speed The speed at which to cycle through the spritesheet. 1 is the fastest, 60+ is the slowest.
     */
    public Animator(int size, int speed){
        this.spritesheet = null;
        this.speed = speed;
        this.index = 0;
        this.count = 0;
        this.size = size;
    }


    /**
     * Updates the animator. This method should be called every update.
     */
    public void update(){

        count++;

        if(count > speed){
            count = 0;
            index++;

            if(index >= size){
                index = 0;
            }
        }
    }



    /**
     * Gets the current frame of the animator.
     * @return The current frame of the animator.
     */
    public BufferedImage getCurrentFrame(){
        return spritesheet[index];
    }

    /**
     * Gets the current index of the animator.
     * @return The current index of the animator.
     */
    public int getIndex(){
        return index;
    }


}
