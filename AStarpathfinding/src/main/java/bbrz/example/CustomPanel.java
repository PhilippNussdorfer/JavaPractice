package bbrz.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomPanel extends JPanel {

    private final int maxCol = 15;
    private final int maxRow = 10;
    private final int nodeSize = 70;
    private final CustomButton[][] node = new CustomButton[maxCol][maxRow];
    private CustomButton startNode, goalNode, currentNode;
    private final List<CustomButton> openList = new ArrayList<>();
    private final List<CustomButton> checkedList = new ArrayList<>();
    boolean goalReached = false;

    public CustomPanel() {
        int screenWidth = nodeSize * maxCol;
        int screenHeight = nodeSize * maxRow;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow, maxCol));
        //this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        this.requestFocusInWindow();

        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {

                node[col][row] = new CustomButton(col, row);
                this.add(node[col][row]);
            }
        }

        /*setStartNode(1, 7);
        setGoalNode(14, 0);

        setSolidNode(10, 2);
        setSolidNode(10, 3);
        setSolidNode(10, 4);
        setSolidNode(10, 5);
        setSolidNode(10, 6);
        setSolidNode(10, 7);
        setSolidNode(6, 2);
        setSolidNode(7, 2);
        setSolidNode(8, 2);
        setSolidNode(9, 2);
        setSolidNode(11, 7);
        setSolidNode(12, 7);
        setSolidNode(6, 1);

        setCostOnNodes();*/
    }

    /*private void setStartNode(int col, int row) {
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(int col, int row) {
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }

    private void setSolidNode(int col, int row) {
        node[col][row].setAsSolid();
    }

    private void setCostOnNodes() {
        for (int row = 0; row < maxRow; row++) {
            for (int col = 0; col < maxCol; col++) {
                getCost(node[col][row]);
            }
        }
    }

    private  void  getCost(CustomButton node) {
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());
        node.setGCost(xDistance + yDistance);

        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());
        node.setHCost(xDistance + yDistance);

        node.setFCost(node.getGCost() + node.getHCost());

        if (node != startNode && node != goalNode) {
            node.setText("<html>F:" + node.getFCost() + "<br>G:" + node.getGCost() + "<br>H:" + node.getHCost() + "</html>");
        }
    }*/
}
