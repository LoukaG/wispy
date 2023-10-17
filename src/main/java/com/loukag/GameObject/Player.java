package com.loukag.GameObject;

import com.loukag.GameObject.Features.Collider;
import com.loukag.Listener.KeyboardListener;
import com.loukag.Map.Block.Block;
import com.loukag.Scene.GameScene;
import com.loukag.Scene.Scene;
import com.loukag.Utils.Animator;
import com.loukag.Utils.Sprite;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Player extends MovingObject implements Collider {

    enum State{
        IDLE,
        WALKING
    }

    private Animator anim;

    private final BufferedImage[] idleSheet;
    private final BufferedImage[] walkingSheet;
    private final ArrayList<Rectangle> bounds;
    private final double SPEED = 0.05, MAX_GRAVITY = -0.2, GRAVITY = 0.01, JUMP_SPEED = 0.25;
    private boolean flip, canJump;
    private Rectangle rec;

    private State state;

    /**
     * Constructor for Player. Should only be called once.
     * @param posX x position
     * @param posY y position
     */
    public Player(int posX, int posY){
        super(posX, posY);
        anim = new Animator(2, 10);
        idleSheet = Sprite.loadSheet(new String[]{"/textures/entity/player/player_idle1.png", "/textures/entity/player/player_idle2.png"});
        walkingSheet = Sprite.loadSheet(Arrays.asList("/textures/entity/player/player_walk1.png", "/textures/entity/player/player_walk2.png").toArray(new String[0]));
        flip = false;
        state = State.IDLE;
        canJump = false;
        bounds = new ArrayList<>(List.of(new Rectangle(getScreenX(), getScreenY(), GameScene.getBlockSize(), GameScene.getBlockSize() * 2)));
    }

    @Override
    public void render(Graphics2D g) {

        BufferedImage image = switch (state) {
            case IDLE -> idleSheet[anim.getIndex()];
            case WALKING -> walkingSheet[anim.getIndex()];
        };
        image = flip?Sprite.mirrorImageHorizontally(image):image;

        g.drawImage(image, getScreenX(), getScreenY(), GameScene.getBlockSize(), GameScene.getBlockSize()*2, null);

        g.setColor(Color.blue);
    }

    @Override
    public void update() {
        rec = new Rectangle(getScreenX()+GameObject.blockToPixel(velX), getScreenY(), GameScene.getBlockSize(), GameScene.getBlockSize() * 2);

        if(Scene.getCurrentScene().checkCollision(rec, 8, true)){
            velX = 0;
        }
        rec = new Rectangle(getScreenX(), getScreenY()-GameObject.blockToPixel(velY-SPEED), GameScene.getBlockSize(), GameScene.getBlockSize() * 2);

        if(Scene.getCurrentScene().checkCollision(rec,8,true)){
            velY = 0;
            canJump = true;
        }else{
            canJump = false;
            if(velY > MAX_GRAVITY)
                velY -= GRAVITY;
        }

        posY += velY;
        posX += velX;
        anim.update();
        //Controls
        if(KeyboardListener.isKeyPressed(65)){
            velX = -SPEED;
            flip = true;
            state = State.WALKING;
        }
        if(KeyboardListener.isKeyPressed(68)){
            velX = SPEED;
            flip = false;
            state = State.WALKING;
        }

        if(!KeyboardListener.isKeyPressed(65) && !KeyboardListener.isKeyPressed(68)){
            state = State.IDLE;
            velX = 0;
        }

        if(KeyboardListener.isKeyPressed(32) && canJump) {
            velY = JUMP_SPEED;
            canJump = false;
        }



    }

    @Override
    public ArrayList<Rectangle> getBounds() {
        bounds.get(0).setLocation(getScreenX(), getScreenY());
        return bounds;
    }

    @Override
    public void onCollision(Collider collider) {

    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
