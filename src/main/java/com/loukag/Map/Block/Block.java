package com.loukag.Map.Block;

import com.loukag.Scene.GameScene;
import com.loukag.Utils.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public enum Block {
    AIR((BufferedImage) null, false),
    GRASS("/textures/blocks/grass.png", true),
    DIRT("/textures/blocks/dirt.png", true),
    STONE("/textures/blocks/stone.png", true);

    private BufferedImage texture;
    private boolean isSolid;

    /**
     * Constructor for Block
     * @param texturePath path to the texture
     * @param isSolid whether the block is solid or not
     */
    Block(String texturePath, boolean isSolid){
        this.texture = Sprite.load(texturePath);
        this.isSolid = isSolid;
    }

    /**
     * Constructor for Block
     * @param texture texture
     * @param isSolid whether the block is solid or not
     */
    Block(BufferedImage texture, boolean isSolid){
        this.texture = texture;
        this.isSolid = isSolid;
    }

    /**
     * Is the block solid?
     * @return whether the block is solid or not
     */
    public boolean isSolid(){
        return isSolid;
    }

    /**
     * Render the block
     * @param g Graphics2D object
     * @param x x position in pixels
     * @param y y position in pixels
     */
    public void draw(Graphics2D g, int x, int y){
        if(texture != null)
            g.drawImage(texture, x, y, GameScene.getBlockSize(), GameScene.getBlockSize(), null);
    }
}
