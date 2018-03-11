package com.kovats.breakout.utils;

import javax.swing.*;

public class Utils {

    public static final int WIDTH = 550;
    public static final int HEIGH = 800;
    public static final int BOTTOM_EDGE = 630;
    public static final int N_OF_BRICKS = 30;
    public static final int INIT_PADDLE_X = 250;
    public static final int INIT_PADDLE_Y = 600;
    public static final int INIT_BALL_X = 270;
    public static final int INIT_BALL_Y = 570;
    public static final int DELAY = 1000;
    public static final int PERIOD = 10;
    public static boolean pause = false;
    public static int maxSpeed = 2;
    public static int balls = 2;
    public static int points = 0;
    public static String user = "User";
    public static JLabel score = new JLabel("0000000"), timeOnGame = new JLabel("00:00") ;
}
