package com.loukag.Map;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PlayerData implements Serializable {
    private double posX, posY;
    private long seed;
    private int[] loadedChunks;

    /**
     * Constructor for PlayerData
     * @param posX x position
     * @param posY y position
     * @param loadedChunks Offset list of loaded chunks
     */
    public PlayerData(double posX, double posY, int[] loadedChunks){
        this.posX = posX;
        this.posY = posY;
        this.loadedChunks = loadedChunks;
    }

    /**
     * Get the x position
     * @return the x position
     */
    public double getPosX(){
        return posX;
    }


    /**
     * Get the y position
     * @return the y position
     */
    public double getPosY(){
        return posY;
    }

    /**
     * Get the loaded chunks
     * @return the loaded chunks
     */
    public int[] getLoadedChunks(){
        return loadedChunks;
    }

    /**
     * Get the seed
     * @return the seed
     */
    public long getSeed(){
        return seed;
    }

    /**
     * Export the player data to a file. Export to %appdata%/.wispy8bit/saves/<worldName>/player.ser
     * @param worldName name of the world
     */
    public void export(String worldName){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(System.getenv("APPDATA") + "/.wispy8bit/saves/"+worldName+"/player.ser"))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
