package com.kovats.breakout;

import com.kovats.breakout.utils.Utils;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TablePoints implements Serializable {

    private TreeMap<Integer, String> points = new TreeMap<>(Comparator.reverseOrder());
    private int position = 1;

    public TablePoints() {
        try {
            InputStream stream = getClass().getResourceAsStream("res/points.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            while (reader.ready()) {
                String s = reader.readLine().replaceAll("\\D+", "");
                points.put(Integer.parseInt(s), reader.readLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveAndPrint() {

        StringBuilder value = new StringBuilder();
        value.append("Игрок: ").append(Utils.user).append("\tTime: ").append(Utils.timeOnGame.getText().replaceAll(" ", "")).
                append("\tBalls: ").append(Utils.balls).append("\tMaxSpeed: ").append(Utils.maxSpeed);
        points.put(Utils.points, value.toString());

        while (points.size() > 10) {
            points.remove(points.lastKey());
        }

        for (Map.Entry<Integer, String> entry : points.entrySet()) {
            System.out.printf("%2d%s%s%-10s", position, ": ", "Points: ", entry.getKey());
            String[] strings = entry.getValue().split("\t");
            for (String string : strings) {
                System.out.printf("%-20s", string);
            }
            System.out.println();
            position++;
        }

        position = 1;

        try {
            String path = new File("").getAbsolutePath();
            PrintWriter writer = new PrintWriter(new FileWriter(new File(path + "\\out\\production\\breakout\\com\\kovats\\breakout\\res\\points.txt")));
            for (Map.Entry<Integer, String> entry : points.entrySet()) {
                writer.println(entry.getKey());
                writer.println(entry.getValue());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
