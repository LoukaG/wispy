package com.loukag.GameObject;

import com.loukag.Listener.KeyboardListener;
import com.loukag.Map.Block.Block;
import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import com.loukag.Utils.Animator;
import com.loukag.Utils.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class Player extends GameObject{

    enum State{
        IDLE,
        WALKING
    }

    private Animator anim;

    private final BufferedImage[] idleSheet;
    private final BufferedImage[] walkingSheet;
    private final double SPEED = 0.05, MAX_GRAVITY = -0.05;
    private boolean flip;

    private double gravity = 0;
    private State state;

    public Player(){
        super(0, 150);
        anim = new Animator(2, 10);
        idleSheet = Sprite.loadSheet(new String[]{"/textures/entity/player/player_idle1.png", "/textures/entity/player/player_idle2.png"});
        walkingSheet = Sprite.loadSheet(Arrays.asList("/textures/entity/player/player_walk1.png", "/textures/entity/player/player_walk2.png").toArray(new String[0]));
        flip = false;
        state = State.IDLE;
    }
    @Override
    public void render(Graphics2D g) {

        BufferedImage image = switch (state) {
            case IDLE -> idleSheet[anim.getIndex()];
            case WALKING -> walkingSheet[anim.getIndex()];
        };
        image = flip?Sprite.mirrorImageHorizontally(image):image;

        g.drawImage(image, getScreenX(), getScreenY(), 32, 64, null);

        g.setColor(Color.blue);
    }

    @Override
    public void update() {


        if(Scene.getCurrentScene() instanceof GameScene){
            GameScene gameScene = (GameScene) Scene.getCurrentScene();
            Block left = gameScene.getMap().getBlock((int) getPosX(), (int) (getPosY()-2));
            Block middle = gameScene.getMap().getBlock((int) ((int) getPosX()+0.5), (int) (getPosY()-2));
            Block right = gameScene.getMap().getBlock((int) getPosX()+1, (int) (getPosY()-2));
            if((!left.isSolid() | !left.isSolid() | !right.isSolid()) && gravity >= MAX_GRAVITY){
                gravity -= 0.01;
            }else if(left.isSolid() | left.isSolid() | right.isSolid()){
                gravity = 0;
            }

        }
        posY += gravity;
        anim.update();
        //Controls
        if(KeyboardListener.isKeyPressed(65)){
            posX -= SPEED;
            flip = true;
            state = State.WALKING;
        }
        if(KeyboardListener.isKeyPressed(68)){
            posX += SPEED;
            flip = false;
            state = State.WALKING;
        }

        if(!KeyboardListener.isKeyPressed(65) && !KeyboardListener.isKeyPressed(68)){
            state = State.IDLE;
        }

        if(KeyboardListener.isKeyPressed(32)){
            gravity += 5;
        }
    }
}
