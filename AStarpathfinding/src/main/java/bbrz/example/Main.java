package bbrz.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("A Star pathfinding test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CustomPanel());
        frame.setVisible(true);
        frame.pack();
    }
}