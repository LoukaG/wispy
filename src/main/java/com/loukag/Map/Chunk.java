package com.loukag.Map;

import com.loukag.GameObject.Features.Collider;
import com.loukag.Main;
import com.loukag.Map.Block.Block;
import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import de.articdive.jnoise.pipeline.JNoise;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Chunk implements Collider, Serializable {
    private static final int CHUNK_WIDTH = 16, CHUNK_HEIGHT = 256;

    public static int getChunkWidth(){
        return CHUNK_WIDTH;
    }

    public static int getChunkHeight(){
        return CHUNK_HEIGHT;
    }

    private Block[][] blocks;
    private ArrayList<Rectangle> bounds;
    private int offset;

    /**
     * Constructor for Chunk
     * @param offset offset of the chunk from the origin
     */
    public Chunk(int offset){
        this.offset = offset;
        blocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT];
        this.bounds = new ArrayList<>();
    }

    /**
     * Get the block at the specified position
     * @param x x position (in blocks)
     * @param y y position (in blocks)
     * @return the block at the specified position
     */
    public Block getBlock(int x, int y){
        y = CHUNK_HEIGHT - y - 1;
        if(x < 0 || x >= CHUNK_WIDTH || y < 0 || y >= CHUNK_HEIGHT)
            return Block.AIR;
        return blocks[x][y];
    }

    /**
     * Set the block at the specified position
     * @param x x position in blocks
     * @param y y position in blocks
     * @param block block to set
     */
    public void setBlock(int x, int y, Block block){
        blocks[x][y] = block;
    }

    public void generate(JNoise noise){
        for (int x = 0; x < CHUNK_WIDTH; x++) {
            Arrays.fill(blocks[x], Block.AIR);

            double noiseValue = noise.evaluateNoise(x + offset);
            int height = (int) (noiseValue * 50);
            height = height+100;
            int dirtHeight = (int) (noise.evaluateNoise(x + offset) * 10)+1;

            for (int y = CHUNK_HEIGHT - 1; y > CHUNK_HEIGHT - height; y--) {
                if(y < (CHUNK_HEIGHT-height) + dirtHeight)
                    blocks[x][y] = Block.DIRT;
                else
                    blocks[x][y] = Block.STONE;
            }
            blocks[x][CHUNK_HEIGHT- height] = Block.GRASS;


        }
        calculteBounds();
    }

    /**
     * Render the chunk
     * @param g Graphics2D object
     */
    public void draw(Graphics2D g){
        if(Scene.getCurrentScene().getCamera().isOnCamera(new Rectangle(offset*GameScene.getBlockSize(), 0, CHUNK_WIDTH*GameScene.getBlockSize(), CHUNK_HEIGHT*GameScene.getBlockSize()))){
            for(int x = 0; x < CHUNK_WIDTH; x++) {
                for (int y = 0; y < CHUNK_HEIGHT; y++) {
                    int posX = (x+offset)*GameScene.getBlockSize();
                    int posY = y*GameScene.getBlockSize();
                    blocks[x][y].draw(g, posX, posY);
                }
            }

            if(Main.DEBUG){
                g.setColor(Color.MAGENTA);
                g.drawRect(offset*GameScene.getBlockSize(), 0, CHUNK_WIDTH*GameScene.getBlockSize(), CHUNK_HEIGHT*GameScene.getBlockSize());
            }
        }


    }

    /**
     * Get the offset of the chunk
     * @return offset of the chunk
     */
    public int getOffset(){
        return offset;
    }

    @Override
    public ArrayList<Rectangle> getBounds() {
        return bounds;
    }

    public void calculteBounds(){
        bounds = new ArrayList<>();
        for(int x = 0; x < CHUNK_WIDTH; x++) {
            for (int y = 0; y < CHUNK_HEIGHT; y++) {
                int posX = (x+offset)*GameScene.getBlockSize();
                int posY = y*GameScene.getBlockSize();
                if(x != 0 && y != 0 && x != CHUNK_WIDTH-1 && y != CHUNK_HEIGHT-1)
                    if(blocks[x-1][y].isSolid()
                        && blocks[x+1][y].isSolid()
                        && blocks[x][y+1].isSolid()
                        && blocks[x][y-1].isSolid())
                        continue;

                if(blocks[x][y].isSolid()){
                    Rectangle bound = new Rectangle(posX, posY, GameScene.getBlockSize(), GameScene.getBlockSize());
                    if(Scene.getCurrentScene() != null){
                        bounds.add(bound);
                    }

                }
            }
        }

    }

    @Override
    public void onCollision(Collider collider) {

    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void exportToFile(String mapName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(System.getenv("APPDATA") + "/.wispy8bit/saves/"+mapName+"/chunks/"+offset+".chunk"))) {
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Chunk importFromFile(String mapName, int offset){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(System.getenv("APPDATA") + "/.wispy8bit/saves/"+mapName+"/chunks/"+offset+".chunk"))) {
            return (Chunk) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
