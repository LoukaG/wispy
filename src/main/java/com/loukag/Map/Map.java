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

    private static int MAX_HEIGHT = 50;

    public static int getMaxHeight(){
        return MAX_HEIGHT;
    }

    public static Map GenerateMap(long seed){
        PerlinNoiseGenerator.newBuilder().setSeed(seed).setInterpolation(Interpolation.COSINE).build();



        return null;
    }
    public String name;

    public Chunk[] chunks;
    private JNoise noise;


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

    public double getNoise(int x){
        return noise.evaluateNoise(x);
    }

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
