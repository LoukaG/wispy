package com.loukag.GameObject.Features;

import java.awt.*;
import java.util.ArrayList;

/**
 * Interface for GameObjects that can collide with other GameObjects
 */
public interface Collider {
    /**
     * Returns the bounding boxes of the GameObject
     * @return the bounding boxes of the GameObject
     */
    public ArrayList<Rectangle> getBounds();

    /**
     * Called when the GameObject collides with another GameObject
     * @param collider the GameObject that the GameObject collided with
     */
    public void onCollision(Collider collider);

    /**
     * Returns whether the GameObject is solid or not
     * @return whether the GameObject is solid or not
     */
    public boolean isSolid();
}
