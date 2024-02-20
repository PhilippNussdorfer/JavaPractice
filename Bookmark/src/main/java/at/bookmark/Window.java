package at.bookmark;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Window {
    private List<JComponent> componentList;
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(panel);
    @Getter
    private GridLayout grid;
    @Getter
    private FlowLayout flow;



    public Window(String windowName, JComponent ... componentList) {
        this.componentList = new ArrayList<>(List.of(componentList));
        this.panel.setName(windowName);

        initComponents();
        initDesign();
    }

    private void initDesign() {
        panel.setBackground(Color.GRAY);
        frame.setIconImage(new ContentHandler().icon.getImage());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }

    private void initComponents() {
        for (JComponent component : componentList) {
            panel.add(component);
        }
    }

    public void setGrid (int row, int col, int vGab, int hGab) {
        grid = new GridLayout(row, col, hGab, vGab);
        panel.setLayout(grid);
    }

    public void setFlow() {
        flow = new FlowLayout();
        panel.setLayout(flow);
    }

    public void createWindow(int width, int height) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.add(scrollPane);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void closeWindow() {
        frame.dispose();
    }
}
