package com.kovats.breakout;

import com.kovats.breakout.utils.Utils;

import javax.swing.*;
import java.util.Random;

public class Ball extends Sprite {

    private int xdir, ydir, xOld, scoreOld = 20;

    public Ball() {

        Random random = new Random();
        xdir = -1 * Utils.maxSpeed + random.nextInt(Utils.maxSpeed - Utils.maxSpeed * -1 + 1);
        if (xdir == 0) xdir++;
        ydir = -1 * (random.nextInt(Utils.maxSpeed) + 1);
        ImageIcon ii = new ImageIcon(getClass().getResource("res/ball.png"));
        image = ii.getImage();
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
        resetState();
    }

    public void move() {

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Integer.parseInt(Utils.timeOnGame.getText().substring(3)) > scoreOld) {
            if (scoreOld >= 60) scoreOld = 20;
            if (xdir < 0 && Math.abs(xdir) < Utils.maxSpeed) xdir -= 1;
            else if (Math.abs(xdir) < Utils.maxSpeed) xdir += 1;
            if (ydir < 0 && Math.abs(ydir) < Utils.maxSpeed) ydir -= 1;
            else if (Math.abs(ydir) < Utils.maxSpeed) ydir +=1;
            scoreOld += 20;
        }

        xOld = x;
        x += xdir;
        y += ydir;

        if (x <= 0)
            setXdir(-1 * xdir);

        if (x > Utils.WIDTH - i_width)
            setXdir(-1 * xdir);

        if (y < 0)
            setYdir(-1 * ydir);
    }

    private void resetState() {
        x = Utils.INIT_BALL_X;
        y = Utils.INIT_BALL_Y;
    }

    public int getXdir() {
        return xdir;
    }

    public void setXdir(int xdir) {
        this.xdir = xdir;
    }

    public int getYdir() {
        return ydir;
    }

    public int getxOld() { return xOld; }

    public void setYdir(int ydir) {
        this.ydir = ydir;
    }
}
