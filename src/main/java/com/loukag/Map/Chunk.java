package com.loukag.Map;

import com.loukag.Map.Block.Block;
import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import de.articdive.jnoise.pipeline.JNoise;

import java.awt.*;
import java.util.Arrays;

public class Chunk {
    private static final int CHUNK_WIDTH = 16, CHUNK_HEIGHT = 256;

    public static int getChunkWidth(){
        return CHUNK_WIDTH;
    }

    public static int getChunkHeight(){
        return CHUNK_HEIGHT;
    }

    private Block[][] blocks;
    private int offset;

    public Chunk(int offset){
        this.offset = offset;
        blocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT];
    }

    public Block getBlock(int x, int y){
        y = CHUNK_HEIGHT - y - 1;
        if(x < 0 || x >= CHUNK_WIDTH || y < 0 || y >= CHUNK_HEIGHT)
            return Block.AIR;
        return blocks[x][y];
    }

    public void setBlock(int x, int y, Block block){
        blocks[x][y] = block;
    }

    public void generate(JNoise noise){
        for (int x = 0; x < CHUNK_WIDTH; x++) {
            Arrays.fill(blocks[x], Block.AIR);

            double noiseValue = noise.evaluateNoise(x + offset);
            int height = (int) (noiseValue * 50);
            height = height+100;
            int dirtHeight = (int) (noiseValue * 10)+1;

            for (int y = CHUNK_HEIGHT - 1; y > CHUNK_HEIGHT - height; y--) {
                if(y < (CHUNK_HEIGHT-height) + dirtHeight)
                    blocks[x][y] = Block.DIRT;
                else
                    blocks[x][y] = Block.STONE;
            }
            blocks[x][CHUNK_HEIGHT- height] = Block.GRASS;


        }
    }

    public void draw(Graphics2D g){
        for(int x = 0; x < CHUNK_WIDTH; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                int posX = (x+offset)*GameScene.getBlockSize();
                int posY = y*GameScene.getBlockSize();
                blocks[x][y].draw(g, posX, posY);
            }
        }
        g.setColor(Color.MAGENTA);
        g.drawRect(offset*GameScene.getBlockSize(), 0, CHUNK_WIDTH*GameScene.getBlockSize(), CHUNK_HEIGHT*GameScene.getBlockSize());
    }

    public int getOffset(){
        return offset;
    }
}
