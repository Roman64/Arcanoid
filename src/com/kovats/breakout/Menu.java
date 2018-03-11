package com.kovats.breakout;

import com.kovats.breakout.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

    private JButton start, stop, pause;
    private JLabel userLabel, ballsLabel, speedLabel, pointsLabel, timeLabel;
    private JTextField user, balls, speed;


    public Menu() {
        setLayout(new GridBagLayout());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(Color.BLACK);
        Font font = new Font("Verdana", Font.BOLD, 14);
        Font fontScore = new Font("Vera", Font.ITALIC, 20);

        start = new JButton("Старт");
        stop = new JButton("Стоп");
        pause = new JButton("Пауза");

        start.addActionListener(new ButtonActionListener());
        stop.addActionListener(new ButtonActionListener());
        pause.addActionListener(new ButtonActionListener());
        stop.setEnabled(false);
        pause.setEnabled(false);

        userLabel = new JLabel("Введите имя:");
        ballsLabel = new JLabel("Кол-во шаров:");
        speedLabel = new JLabel("Максимальная скорость шаров:");
        pointsLabel = new JLabel("Очки:");
        timeLabel = new JLabel("Время:");


        userLabel.setFont(font);
        userLabel.setForeground(Color.BLUE);
        ballsLabel.setFont(font);
        ballsLabel.setForeground(Color.BLUE);
        speedLabel.setFont(font);
        speedLabel.setForeground(Color.BLUE);
        pointsLabel.setFont(fontScore);
        pointsLabel.setForeground(Color.BLUE);
        timeLabel.setFont(fontScore);
        timeLabel.setForeground(Color.BLUE);
        Utils.score.setFont(fontScore);
        Utils.score.setForeground(Color.RED);
        Utils.timeOnGame.setFont(fontScore);
        Utils.timeOnGame.setForeground(Color.RED);

        user = new JTextField(1);
        balls = new JTextField(1);
        speed = new JTextField(1);
        user.setText(Utils.user);
        balls.setText(String.valueOf(Utils.balls));
        speed.setText(String.valueOf(Utils.maxSpeed));
        user.setFont(font);
        balls.setFont(font);
        speed.setFont(font);

        add(userLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(ballsLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(speedLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(user, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(balls, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(speed, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.NORTHWEST,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));

        add(start, new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_END,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(stop, new GridBagConstraints(3, 1, 1, 1, 1, 1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(pause, new GridBagConstraints(3, 2, 1, 1, 1, 1, GridBagConstraints.LAST_LINE_END,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));

        add(pointsLabel, new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.LAST_LINE_START,
                GridBagConstraints.HORIZONTAL, new Insets(30,0,0,0), 0, 0));
        add(timeLabel, new GridBagConstraints(2, 4, 1, 1, 1, 1, GridBagConstraints.LAST_LINE_START,
                GridBagConstraints.HORIZONTAL, new Insets(0,20,0,0), 0, 0));
        add(Utils.score, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.LAST_LINE_START,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
        add(Utils.timeOnGame, new GridBagConstraints(3, 4, 1, 1, 1, 1, GridBagConstraints.LAST_LINE_START,
                GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
    }

    public void startSetEnable() {
        if (!start.isEnabled()) {
            start.setEnabled(true);
            stop.setEnabled(false);
            pause.setEnabled(false);
            speed.setEditable(true);
            user.setEditable(true);
            balls.setEditable(true);
        }
    }

    public class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton)e.getSource();
                switch (button.getText()) {
                    case "Старт": {
                        if (speed.getText().matches("^\\d+$") && Integer.parseInt(speed.getText()) > 0) {
                            speed.setForeground(Color.BLUE);
                            Utils.maxSpeed = Integer.parseInt(speed.getText());
                            speed.setEditable(false);
                        } else {
                            speed.setForeground(Color.RED);
                            speed.setText("Неверное значение!");
                            start.setEnabled(true);
                        }
                        if (balls.getText().matches("^\\d+$") && Integer.parseInt(balls.getText()) > 0) {
                            balls.setForeground(Color.BLUE);
                            Utils.balls = Integer.parseInt(balls.getText());
                            balls.setEditable(false);
                        } else {
                            balls.setForeground(Color.RED);
                            balls.setText("Неверное значение!");
                            start.setEnabled(true);
                        }
                        Utils.user = user.getText();
                        user.setForeground(Color.BLUE);
                        user.setEditable(false);
                        stop.setEnabled(true);
                        pause.setEnabled(true);

                        Breakout.board.initBoard();
                        start.setEnabled(false);
                        return;
                    }
                    case "Стоп": {
                        Breakout.board.stopGame();
                        start.setEnabled(true);
                        user.setEditable(true);
                        speed.setEditable(true);
                        balls.setEditable(true);
                        stop.setEnabled(false);
                        pause.setEnabled(false);
                        return;
                    }
                    case "Пауза": {
                        Utils.pause = !Utils.pause;
                        pause.setText("Возобновить");
                        return;
                    }

                    case "Возобновить": {
                        Utils.pause = !Utils.pause;
                        pause.setText("Пауза");
                        return;
                    }
                }
            }
        }
    }
}
