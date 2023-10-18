package com.loukag;

import com.loukag.Scene.GameScene;
import com.loukag.Scene.MenuScene;
import com.loukag.Scene.Scene;
import com.loukag.Utils.Sprite;

import javax.swing.*;

public class Main {
    public static boolean DEBUG = false;
    public static void main(String[] args) {
        System.setProperty("sun.java2d.d3d", "false");
        System.setProperty("sun.java2d.uiScale", "1.0");

        Scene scene = new GameScene();
        GameFrame frame = new GameFrame();
        Scene.gameFrame = frame;
        frame.create(scene);

        Scene.gameEngine.start();

    }
}