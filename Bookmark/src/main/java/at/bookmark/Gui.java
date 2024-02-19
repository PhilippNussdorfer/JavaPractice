package at.bookmark;

import javax.swing.*;

public class Gui {
    private JPanel mainPanel;

    public static void main(String[] args) {
        Gui gui = new Gui();

        JFrame frame = new JFrame("Gui");
        frame.setContentPane(gui.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
