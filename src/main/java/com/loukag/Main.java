package com.loukag;

import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.d3d", "false");

        JFrame frame = new JFrame("Wispy");
        Scene scene = new GameScene();
        Scene.setCurrentScene(scene);
        frame.add(scene);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Scene.gameEngine.start();

    }
}