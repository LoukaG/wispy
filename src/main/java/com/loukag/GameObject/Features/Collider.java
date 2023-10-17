package com.loukag.GameObject.Features;

import java.awt.*;
import java.util.ArrayList;

public interface Collider {
    public ArrayList<Rectangle> getBounds();

    public void onCollision(Collider collider);

    public boolean isSolid();
}
