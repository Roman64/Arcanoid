package com.kovats.breakout;

import com.kovats.breakout.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Paddle extends Sprite {

    public boolean beginDrag = false;
    private  int dx;
    Rectangle pad;

    public  Paddle() {
        ImageIcon ii = new ImageIcon(getClass().getResource("res/Paddle.png"));
        image = ii.getImage();
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
        pad = new Rectangle(Utils.INIT_PADDLE_X, Utils.INIT_PADDLE_Y, i_width, i_height);
        resetState();
    }

    public void move() {
        x += dx;
        pad = new Rectangle(x, Utils.INIT_PADDLE_Y, i_width, i_height);

        if (x < 0)
            x = 0;

        if (x >= Utils.WIDTH - i_width)
            x = Utils.WIDTH - i_width;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            dx = -2;

        if (key == KeyEvent.VK_RIGHT)
            dx = 2;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            dx = 0;

        if (key == KeyEvent.VK_RIGHT)
            dx = 0;
    }

    private void resetState() {
        x = Utils.INIT_PADDLE_X;
        y = Utils.INIT_PADDLE_Y;
    }


    public void mousePressed(MouseEvent e) {
        if (pad.contains(e.getX(), e.getY()) && e.getButton() == MouseEvent.BUTTON3)
        beginDrag = true;

    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            beginDrag = false;
            dx = 0;
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (beginDrag)
            x = e.getX();
    }

}
