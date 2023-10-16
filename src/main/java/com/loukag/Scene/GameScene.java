package com.loukag.Scene;

import com.loukag.GameEngine;
import com.loukag.GameObject.Player;
import com.loukag.Map.Chunk;
import com.loukag.Map.Map;

import java.awt.*;

public class GameScene extends Scene{
    private static final int BLOCK_SIZE = 32;

    private Player player;

    private Map map;

    public static int getBlockSize(){
        return BLOCK_SIZE;
    }

    @Override
    public void init() {
        player = new Player();
        map = new Map();
        this.addGameObject(player, 0);
        this.addGameObject(map, 5);
    }

    protected void paintDebug(Graphics g) {
        getCamera().setPos(player.getScreenX()-350, player.getScreenY()-350);
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(GameEngine.getFPS()+" FPS", 10, 20);
        g.drawString("X: "+player.getPosX() , 10, 30);
        g.drawString("Y: "+player.getPosY(), 10, 40);
    }

    public Map getMap(){
        return map;
    }
}
