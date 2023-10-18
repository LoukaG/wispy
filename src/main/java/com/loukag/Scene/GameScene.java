package com.loukag.Scene;

import com.loukag.GameEngine;
import com.loukag.GameObject.Player;
import com.loukag.Map.Chunk;
import com.loukag.Map.Map;

import java.awt.*;
import java.text.DecimalFormat;

public class GameScene extends Scene{
    private static int blockSize = 32;

    private Player player;

    private Map map;

    public static int getBlockSize(){
        return blockSize;
    }

    public static void setBlockSize(int blockSize){
        GameScene.blockSize = blockSize;
    }

    @Override
    public void init() {
        player = new Player(64,150);
        map = Map.createMap("HelloWorld");
        this.addGameObject(player, 2);
        this.addGameObject(map, 8);
    }

    @Override
    public void beforeUpdate() {

    }

    @Override
    public void beforeRender(Graphics2D g) {
        Scene.getCurrentScene().getCamera().setPosSmooth(player.getScreenX()-((Scene.getScreenW()/2)-player.getBounds().get(0).width/2), player.getScreenY()-((Scene.getScreenH()/2)-player.getBounds().get(0).height/2), 10);
    }


    protected void paintDebug(Graphics g) {
        DecimalFormat df = new DecimalFormat("0.00");
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(GameEngine.getFPS()+" FPS", 10, 20);
        g.drawString("X: "+df.format(player.getPosX()) , 10, 30);
        g.drawString("Y: "+df.format(player.getPosY()), 10, 40);
    }

    public Map getMap(){
        return map;
    }

    public Player getPlayer(){
        return player;
    }
}
