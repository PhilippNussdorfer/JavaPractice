package at.bookmark.bookmark_javafx.GUITools;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import lombok.Getter;

import java.util.List;

public class FontUpdater {

    private Font appFont = new Font(16);
    @Getter
    private final int popupWidth = 480;
    @Getter
    private final int popupHeight = 240;

    public void setAppFont(double size) {
        if (size < 8)
            this.appFont = new Font(8);
        else
            this.appFont = new Font(size);
    }

    public Font getAppFont() {
        return this.appFont;
    }

    @SafeVarargs
    public final void updateFont(List<Node>... nodes) {
        for (List<Node> nodeList : nodes) {
            for (Node node : nodeList) {
                updateNodeFont(node);
            }
        }
    }

    private void updateNodeFont(Node node) {
        if (node instanceof Button) {
            ((Button) node).setFont(appFont);
        }
        if (node instanceof TextArea) {
            ((TextArea) node).setFont(appFont);
        }
        if (node instanceof Label) {
            ((Label) node).setFont(appFont);
        }
    }
}
