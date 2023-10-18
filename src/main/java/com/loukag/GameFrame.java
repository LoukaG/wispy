package com.loukag;

import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import com.loukag.Utils.Sprite;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameFrame extends JFrame {
    public void create(Scene scene){
        ImageIcon img = new ImageIcon(Sprite.load("/textures/blocks/grass.png"));
        Scene.setCurrentScene(scene);
        this.setContentPane(scene);
        this.pack();
        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        GameFrame frame = this;

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Scene.setScreenSize(frame.getWidth(), frame.getHeight());
                if(Scene.getCurrentScene() instanceof GameScene scene1){
                    GameScene.setBlockSize((32*frame.getWidth())/1250);
                    scene1.getMap().updateBounds();
                    Scene.getCurrentScene().getCamera().setPos(scene1.getPlayer().getScreenX()-((Scene.getScreenW()/2)-scene1.getPlayer().getBounds().get(0).width/2), scene1.getPlayer().getScreenY()-((Scene.getScreenH()/2)-scene1.getPlayer().getBounds().get(0).height/2));
                }

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

}
