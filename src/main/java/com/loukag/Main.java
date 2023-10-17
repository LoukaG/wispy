package com.loukag;

import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import com.loukag.Utils.Sprite;

import javax.swing.*;

public class Main {
    public static boolean DEBUG = false;
    public static void main(String[] args) {
        System.setProperty("sun.java2d.d3d", "false");
        System.setProperty("sun.java2d.uiScale", "1.0");


        ImageIcon img = new ImageIcon(Sprite.load("/textures/blocks/grass.png"));
        JFrame frame = new JFrame("Wispy");
        Scene scene = new GameScene();
        Scene.setCurrentScene(scene);
        frame.setContentPane(scene);
        frame.pack();
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Scene.gameEngine.start();

    }
}