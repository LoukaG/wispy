package com.loukag.Scene;

import com.loukag.Camera.Camera;
import com.loukag.GameEngine;
import com.loukag.GameObject.Features.Collider;
import com.loukag.GameObject.GameObject;
import com.loukag.GameObject.Player;
import com.loukag.Listener.KeyboardListener;
import com.loukag.Main;
import com.loukag.Map.Map;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene extends JPanel {

    public static final GameEngine gameEngine;
    private static Scene currentScene;

    static{
        gameEngine = new GameEngine();
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setCurrentScene(Scene currentScene) {
        Scene.currentScene = currentScene;
    }

    private List<List<GameObject>> layers;

    private Camera camera;

    public Scene() {
        layers = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            layers.add(new ArrayList<>());
        }

        setPreferredSize(new Dimension(1250, 720));
        setFocusable(true);
        setBackground(Color.BLACK);

        this.addKeyListener(new KeyboardListener());
        this.camera = new Camera(0,0);
        this.init();
    }

    public abstract void init();

    public void addGameObject(GameObject gameObject, int layer) {
        if (layer >= 0 && layer < layers.size()) {
            layers.get(layer).add(gameObject);
        }
    }

    public void removeGameObject(GameObject gameObject, int layer) {
        if (layer >= 0 && layer < layers.size()) {
            layers.get(layer).remove(gameObject);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.translate(-camera.getPosX(), -camera.getPosY());

        Graphics2D g2d = (Graphics2D) g;

        for (int i = layers.size() - 1; i >= 0; i--) {
            List<GameObject> layer = layers.get(i);
            for (GameObject gameObject : layer) {
                gameObject.render(g2d);

                if(Main.DEBUG){
                    g2d.setColor(Color.red);
                    if(gameObject instanceof Collider collider){
                        for(Rectangle rect : collider.getBounds()){
                            g2d.draw(rect);
                        }
                    }
                }

            }
        }


        g.translate(camera.getPosX(), camera.getPosY());

        paintDebug(g2d);

        g.dispose();
    }

    protected void paintDebug(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(GameEngine.getFPS()+" FPS", 10, 20);
    }




    // Méthode pour la logique de jeu
    public void update() {
        for (int i = layers.size() - 1; i >= 0; i--) {
            List<GameObject> layer = layers.get(i);
            for (GameObject gameObject : layer) {
                gameObject.update();
            }
        }
    }

    public boolean checkCollision(Rectangle rec, int layer){
        for(GameObject gameObject : layers.get(layer)){
            if(gameObject instanceof Collider other){
                for(Rectangle rec2 : other.getBounds()){
                    if(rec.intersects(rec2)){
                        return true;
                    }

                }
            }
        }

        return false;
    }

    public boolean checkCollision(Rectangle rec, int layer, boolean isSolid){
        for(GameObject gameObject : layers.get(layer)){
            if(gameObject instanceof Collider other){
                if(other.isSolid() == isSolid & other.getBounds() != null)
                    for(Rectangle rec2 : other.getBounds()){
                        if(rec.intersects(rec2)){
                            return true;
                        }
                    }
            }
        }

        return false;
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public Camera getCamera(){
        return camera;
    }
}
