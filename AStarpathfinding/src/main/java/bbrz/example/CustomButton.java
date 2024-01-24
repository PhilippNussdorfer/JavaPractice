package bbrz.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
public class CustomButton extends JButton implements ActionListener {

    @Setter
    private CustomButton parent;
    private final int col;
    private final int row;
    /*@Setter
    private int gCost;
    @Setter
    private int hCost;
    @Setter
    private int fCost;*/
    private Boolean start = false;
    private Boolean goal = false;
    private Boolean solid = false;
    private Boolean open = false;
    private Boolean checked = false;

    public CustomButton (int col, int row) {
        this.col = col;
        this.row = row;

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        setBackground(Color.ORANGE);
    }

    public void setAsStart() {
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setText("Start");
        start = true;
    }

    public void setAsGoal() {
        setBackground(Color.YELLOW);
        setForeground(Color.BLACK);
        setText("Goal");
        goal = true;
    }

    public void  setAsSolid() {
        setBackground(Color.BLACK);
        solid = true;
    }

    public void setAsOpen() {
        open = true;
    }

    public void  setAsChecked() {
        if (!start && !goal) {
            setBackground(Color.PINK);
            setForeground(Color.BLUE);
        }
        checked = true;
    }

    public void setAsPath() {
        setBackground(Color.GREEN);
        setForeground(Color.PINK);
    }
}
