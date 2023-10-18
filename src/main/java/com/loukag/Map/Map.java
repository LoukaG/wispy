package com.loukag.Map;

import com.loukag.GameObject.Features.Collider;
import com.loukag.GameObject.GameObject;
import com.loukag.GameObject.Player;
import com.loukag.Map.Block.Block;
import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noise_parameters.interpolation.Interpolation;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.perlin.PerlinNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Map extends GameObject implements Collider {
    /**
     * TODO implement map generation
     * @param seed seed for the map
     * @return the generated map
     */
    public static Map GenerateMap(long seed){
        PerlinNoiseGenerator.newBuilder().setSeed(seed).setInterpolation(Interpolation.COSINE).build();

        return null;
    }
    private String name;
    private Chunk[] chunks;
    private JNoise noise;
    private int chunkOffset=-1, maxChunkOffset=-1;

    /**
     * Constructor for Map
     */
    public Map(String mapName){
        super(0,0);
        this.name = mapName;
        noise =  JNoise.newBuilder().perlin(System.currentTimeMillis(), Interpolation.CUBIC, FadeFunction.IMPROVED_PERLIN_NOISE)
               .scale(1 / 50.0)
               .addModifier(v -> (v + 1) / 2.0)
               .clamp(0.0, 1.0)
               .build();
        chunks = new Chunk[16];

        File dossier = new File(System.getenv("APPDATA") + "/.wispy8bit/saves/"+name+"/chunks");
        if(!dossier.exists())
            dossier.mkdirs();

        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = new Chunk(i * Chunk.getChunkWidth());
            chunks[i].generate(noise);
        }

    }

    /**
     * Get the noise at the specified position
     * @param x x position
     * @return the noise at the specified position
     */
    public double getNoise(int x){
        return noise.evaluateNoise(x);
    }

    /**
     * Get the block at the specified position
     * @param x x position
     * @param y y position
     * @return the block at the specified position
     */
    public Block getBlock(int x, int y){

        int chunkIndex = x / Chunk.getChunkWidth();
        chunkIndex *= Chunk.getChunkWidth();

        Chunk chunk = null;
        for (Chunk c : chunks) {
            if(c.getOffset() == chunkIndex){
                chunk = c;
                break;
            }
        }

        return chunk.getBlock(x - chunkIndex, y);
    }

    /**
     * Render the map
     * @param g Graphics2D object
     */
    public void render(Graphics2D g){

        for (Chunk chunk : chunks) {
            chunk.draw(g);
        }

        g.setColor(Color.blue);
        //g.fill(chunkBound);
    }

    /**
     * Export the map to a file
     */
    public void export(){
        if(Scene.getCurrentScene() instanceof GameScene gameScene){
            Player player = gameScene.getPlayer();
            int[] offsetChunks = new int[16];
            for (int i = 0; i < offsetChunks.length; i++) {
                offsetChunks[i] = chunks[i].getOffset();
            }
            PlayerData playerData = new PlayerData(player.getPosX(), player.getPosY(), offsetChunks);
            playerData.export(name);
        }
        for (Chunk chunk : chunks) {
            chunk.exportToFile(name);
        }
    }

    @Override
    public void update() {
        if(Scene.getCurrentScene() instanceof GameScene gameScene){
            Player player = gameScene.getPlayer();
            if(chunkOffset == maxChunkOffset){
                chunkOffset = (int) (player.getPosX() / Chunk.getChunkWidth());
                maxChunkOffset = chunkOffset+1;
                chunkOffset *= Chunk.getChunkWidth();
                maxChunkOffset *= Chunk.getChunkWidth();
            }


            if(player.getPosX() < chunkOffset || player.getPosX() > maxChunkOffset)
                updateChunks(player);
        }
    }

    public void updateChunks(Player player){
        int victimIndex = -1;
        int newChunkOffset = chunkOffset;
        if(player.getPosX() < chunkOffset) {
            Chunk victim = getRightmostChunk();
            victimIndex = Arrays.asList(chunks).indexOf(victim);

            newChunkOffset = getLeftmostChunk().getOffset() - Chunk.getChunkWidth();

            maxChunkOffset = chunkOffset;
            chunkOffset -= Chunk.getChunkWidth();
        }else if(player.getPosX() > maxChunkOffset){
            Chunk victim = getLeftmostChunk();
            victimIndex = Arrays.asList(chunks).indexOf(victim);

            newChunkOffset = getRightmostChunk().getOffset() + Chunk.getChunkWidth();

            chunkOffset = maxChunkOffset;
            maxChunkOffset += Chunk.getChunkWidth();
        }
        chunks[victimIndex].exportToFile(name);

        System.out.println("export "+victimIndex);
        if(new File(System.getenv("APPDATA") + "/.wispy8bit/saves/"+name+"/chunks/"+newChunkOffset+".chunk").exists()) {
            chunks[victimIndex] = Chunk.importFromFile(name, newChunkOffset);
        }else {
            chunks[victimIndex] = new Chunk(newChunkOffset);
            chunks[victimIndex].generate(noise);
        }
    }

    private Chunk getLeftmostChunk(){
        Chunk leftmostChunk = null;
        for (Chunk chunk : chunks) {
            if(leftmostChunk == null)
                leftmostChunk = chunk;
            else if(chunk.getOffset() < leftmostChunk.getOffset())
                leftmostChunk = chunk;
        }

        return leftmostChunk;
    }

    private Chunk getRightmostChunk(){
        Chunk rightmostChunk = null;
        for (Chunk chunk : chunks) {
            if(rightmostChunk == null)
                rightmostChunk = chunk;
            else if(chunk.getOffset() > rightmostChunk.getOffset())
                rightmostChunk = chunk;
        }

        return rightmostChunk;
    }

    @Override
    public ArrayList<Rectangle> getBounds() {
        ArrayList<Rectangle> colliders = new ArrayList<>();

        for (Chunk chunk : chunks) {
            colliders.addAll(chunk.getBounds());
        }

        return colliders;
    }

    @Override
    public void onCollision(Collider collider) {

    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
