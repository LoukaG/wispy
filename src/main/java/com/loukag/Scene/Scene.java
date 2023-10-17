package com.loukag.Scene;

import com.loukag.Camera.Camera;
import com.loukag.GameEngine;
import com.loukag.GameObject.Features.Collider;
import com.loukag.GameObject.GameObject;
import com.loukag.Listener.KeyboardListener;
import com.loukag.Main;

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

    /**
     * Returns the current scene
     * @return the current scene
     */
    public static Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Sets the current scene
     * @param currentScene the scene to set as current
     */
    public static void setCurrentScene(Scene currentScene) {
        Scene.currentScene = currentScene;
    }
    private List<List<GameObject>> layers;
    private Camera camera;

    /**
     * Constructor for Scene
     */
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

    /**
     * Initialize the scene
     */
    public abstract void init();

    /**
     * Called before the scene is updated
     */
    public abstract void beforeUpdate();

    /**
     * Called before the scene is rendered
     * @param g Graphics2D object
     */
    public abstract void beforeRender(Graphics2D g);

    /**
     * Add a GameObject to the scene
     * @param gameObject GameObject to add
     * @param layer layer to add the GameObject to (0 to 9)
     */
    public void addGameObject(GameObject gameObject, int layer) {
        if (layer >= 0 && layer < layers.size()) {
            layers.get(layer).add(gameObject);
        }
    }

    /**
     * Remove a GameObject from the scene
     * @param gameObject GameObject to remove
     * @param layer layer to remove the GameObject from (0 to 9)
     */
    public void removeGameObject(GameObject gameObject, int layer) {
        if (layer >= 0 && layer < layers.size()) {
            layers.get(layer).remove(gameObject);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.beforeRender(g2d);
        g.translate(-camera.getPosX(), -camera.getPosY());



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

    /**
     * Paint debug information
     * @param g Graphics2D object
     */
    protected void paintDebug(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(GameEngine.getFPS()+" FPS", 10, 20);
    }

    /**
     * Update the scene
     */
    public void update() {
        this.beforeUpdate();
        for (int i = layers.size() - 1; i >= 0; i--) {
            List<GameObject> layer = layers.get(i);
            for (GameObject gameObject : layer) {
                gameObject.update();
            }
        }
    }

    /**
     * Check if a collision occurs with Rectangle
     * @param rec Rectangle to check
     * @param layer layer to check
     * @return true if a collision occurs
     */
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

    /**
     * Check if a collision occurs with Rectangle
     * @param rec Rectangle to check
     * @param layer layer to check
     * @param isSolid if the GameObject is solid
     * @return true if a collision occurs
     */
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

    /**
     * Set the camera
     * @param camera Camera to set
     */
    public void setCamera(Camera camera){
        this.camera = camera;
    }

    /**
     * Get the camera
     * @return the camera
     */
    public Camera getCamera(){
        return camera;
    }

}
