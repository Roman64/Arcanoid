package com.kovats.breakout;

import com.kovats.breakout.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class Breakout extends JFrame {

    public static Board board = new Board();
    public static Menu menu = new Menu();

    public Breakout() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        add(menu, BorderLayout.SOUTH);
        setResizable(true);
        setTitle("Arcanoid");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setSize(Utils.WIDTH, Utils.HEIGH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("res/icon.png") );
        setIconImage(image);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Breakout game = new Breakout();
                game.setVisible(true);
            }
        });
    }
}
