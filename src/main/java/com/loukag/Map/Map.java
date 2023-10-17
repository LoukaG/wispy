package com.loukag.Map;

import com.loukag.GameObject.Features.Collider;
import com.loukag.GameObject.GameObject;
import com.loukag.Map.Block.Block;
import com.loukag.Scene.GameScene;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noise_parameters.interpolation.Interpolation;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.perlin.PerlinNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;

import java.awt.*;
import java.util.ArrayList;

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

    /**
     * Constructor for Map
     */
    public Map(){
        super(0,0);
       noise =  JNoise.newBuilder().perlin(System.currentTimeMillis(), Interpolation.CUBIC, FadeFunction.IMPROVED_PERLIN_NOISE)
               .scale(1 / 50.0)
               .addModifier(v -> (v + 1) / 2.0)
               .clamp(0.0, 1.0)
               .build();
        chunks = new Chunk[16];
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
    }

    @Override
    public void update() {

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
