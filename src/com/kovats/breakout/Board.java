package com.kovats.breakout;

import com.kovats.breakout.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Board extends JPanel {

    private Timer timer, timerPoints;
    private String message = "Go Start";
    private ArrayList <Ball> balls;
    private Paddle paddle;
    private Brick[] bricks;
    public boolean ingame = false;
    private Date date;
    private Sound sound = new Sound(getClass().getResource("res/PaddleVSball.wav"));
    private Sound music = new Sound(getClass().getResource("res/music.wav"));
    private Sound sound2 = new Sound(getClass().getResource("res/brick.wav"));

    public Board() {
        setFocusable(true);
        setBounds(0, 0, Utils.WIDTH, Utils.HEIGH - 200);
        setDoubleBuffered(true);
        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());
        addMouseMotionListener(new MAdapter());
        music.play();
    }

    public void initBoard() {
        bricks = null;
        bricks = new Brick[Utils.N_OF_BRICKS];
        balls = null;
        balls = new ArrayList<>(Utils.balls);
        for (int i = 0; i < Utils.balls; i++) {
            balls.add(new Ball());
        }
        paddle = new Paddle();

        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 60 + 90, i * 30 + 70);
                k++;
            }
        }

        ingame = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), Utils.DELAY, Utils.PERIOD);
        timerPoints = new Timer();
        timerPoints.scheduleAtFixedRate(new TimerPoints(), Utils.DELAY, Utils.PERIOD);
        date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        Utils.points = 0;
    }

    public  void addNotify() {
        super.addNotify();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("res/background.png"));
        g.drawImage(img, 0, 0, this);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (ingame)
            drawObjects(g2d);
        else
            gameFinished(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        for (Ball paintBall : balls) {
            g2d.drawImage(paintBall.getImage(), paintBall.getX(), paintBall.getY(), paintBall.getI_width(), paintBall.getI_height(), this);
        }
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(), paddle.getI_width(), paddle.getI_height(), this);

        for (int i = 0; i < Utils.N_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed())
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getI_width(), bricks[i].getI_height(), this);
        }
    }

    private void gameFinished(Graphics2D g2d) {
        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = getFontMetrics(font);
        g2d.setColor(Color.BLUE);
        g2d.setFont(font);
        g2d.drawString(message, (Utils.WIDTH - metr.stringWidth(message)) / 2, Utils.WIDTH / 2);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }

    private class MAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            paddle.mousePressed(e);
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            paddle.mouseReleased(e);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            paddle.mouseDragged(e);
        }
    }

    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
            if (!music.isPlaying()) music.play();
            if (!Utils.pause) {
                for (Ball ball1 : balls) {
                    ball1.move();
                }
                paddle.move();
                checkCollision();
                repaint();
            }
        }
    }

    private class TimerPoints extends TimerTask {

        @Override
        public void run() {
            if (!Utils.pause) {
                try {
                    Thread.sleep(1000);
                    date.setSeconds(date.getSeconds() + 1);
                    Utils.points += balls.size() * Utils.maxSpeed;
                    Utils.score.setText(String.format("%07d", Utils.points));
                    Utils.timeOnGame.setText(String.format("%02d:%02d", date.getMinutes(), date.getSeconds()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopGame() {
        if (ingame) timer.cancel();
        if (ingame) timerPoints.cancel();
        TablePoints points = new TablePoints();
        if (ingame) {
            points.saveAndPrint();
        }
        ingame = false;
        music.stop();
        Breakout.menu.startSetEnable();
    }

    private  void checkCollision() {

        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).getRect().getMaxY() > Utils.BOTTOM_EDGE)
                balls.remove(i);
        }

        if (balls.isEmpty()) {
            message = Utils.user + " Проиграл!";
            stopGame();
        }

        for (int i = 0, j  = 0; i < Utils.N_OF_BRICKS; i++) {
            if (bricks[i].isDestroyed())
                j++;
            if (j == Utils.N_OF_BRICKS) {
                message = Utils.user + " Победил!";
                stopGame();
            }
        }

        for (Ball theBall : balls) {
            if (theBall.getRect().intersects(paddle.getRect())) {
                sound.play(true);
                int paddlePos = (int) paddle.getRect().getMinX();
                int ballPos = (int) theBall.getRect().getMinX() + theBall.i_width/2;

                int leftTrap = paddlePos + 5;
                int rightTrap = paddlePos + 45;

                if (ballPos < leftTrap && theBall.getxOld() < theBall.getX()) {
                    theBall.setXdir(-1*(theBall.getXdir()));
                    theBall.setYdir(-1*theBall.getYdir());
                }

                if (ballPos >= leftTrap && ballPos <= rightTrap) {
                    theBall.setXdir(theBall.getXdir());
                    theBall.setYdir(-1 * theBall.getYdir());
                }

                if (ballPos > rightTrap && theBall.getxOld() > theBall.getX()) {
                    theBall.setXdir(-1 * (theBall.getXdir()));
                    theBall.setYdir(-1 * theBall.getYdir());
                }
            }

            for (int i = 0; i < Utils.N_OF_BRICKS; i++) {
                if (theBall.getRect().intersects(bricks[i].getRect())) {
                    int ballLeft = (int) theBall.getRect().getMinX();
                    int ballHeight = (int) theBall.getRect().getHeight();
                    int ballWidth = (int) theBall.getRect().getWidth();
                    int ballTop = (int) theBall.getRect().getMinY();

                    Point pointRight = new Point(ballLeft + ballWidth + Math.abs(theBall.getXdir()), ballTop + ballHeight/2);
                    Point pointLeft = new Point(ballLeft - Math.abs(theBall.getXdir()), ballTop + ballHeight/2);
                    Point pointTop = new Point(ballLeft + ballWidth/2, ballTop - Math.abs(theBall.getYdir()));
                    Point pointBottom = new Point(ballLeft + ballWidth/2, ballTop + ballHeight + Math.abs(theBall.getYdir()));

                    if (!bricks[i].isDestroyed()) {
                        if (bricks[i].getRect().contains(pointRight) || bricks[i].getRect().contains(pointLeft)) {
                            theBall.setXdir(-1 * theBall.getXdir());
                            bricks[i].setDestroyed(true);
                            sound2.play(true);
                        }
                        if (bricks[i].getRect().contains(pointTop) || bricks[i].getRect().contains(pointBottom)) {
                            theBall.setYdir(-1 * theBall.getYdir());
                            bricks[i].setDestroyed(true);
                            sound2.play(true);
                        }
                    }
                }
            }
        }
    }

    private String resolveName(String name) {

        return name;
    }
}
