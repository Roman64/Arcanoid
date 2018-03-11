package com.kovats.breakout;

import javax.swing.*;

public class Brick extends Sprite {

    private boolean destroyed;

    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon(getClass().getResource("res/brick.png"));
        image = ii.getImage();
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
        destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
