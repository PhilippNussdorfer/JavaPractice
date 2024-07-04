package at.bookmark.bookmark_javafx.GUITools;

import at.bookmark.bookmark_javafx.WindowCalc;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class AddWindowBuilder {

    public void addWindow(Notification notification, GridBuilder gridBuilder, GridPane grid, DependencyBundle dB) {
        dB.addNodes.clear();

        Stage addStage = new Stage();
        WindowCalc.setStagePosition(addStage, notification.getX() + 200, notification.getY() + 200);

        TextField txt_add_title = new TextField();
        TextField txt_add_page = new TextField();
        TextField txt_add_link = new TextField();

        Label lbl_add_title = new Label("Title:");
        Label lbl_add_page = new Label("Page:");
        Label lbl_add_link = new Label("Link:");

        Button btn_add = new Button("Add New Bookmark");

        btn_add.setOnAction(e -> {
            var res = dB.handler.addNewBookmark(txt_add_title.getText(), txt_add_page.getText(), txt_add_link.getText());
            if (res) {
                notification.notify("Added Bookmark for: " + txt_add_title.getText(), Alert.AlertType.INFORMATION);

                dB.viewNodes.clear();

                gridBuilder.setGrid(grid, dB.handler.getBookmarks(), notification, dB);

                dB.handler.saveInFile();
                addStage.close();
            } else {
                notification.notify("Please use a link that is usable, this link is invalid: ' " + txt_add_link.getText() + " '!", Alert.AlertType.ERROR);
            }
        });

        GridPane pane = new GridPane();
        pane.add(lbl_add_title, 0, 0);
        pane.add(txt_add_title, 1, 0);
        pane.add(lbl_add_page, 0, 1);
        pane.add(txt_add_page, 1, 1);
        pane.add(lbl_add_link, 0 , 2);
        pane.add(txt_add_link, 1, 2);
        pane.add(btn_add, 0, 3);

        dB.addNodes.addAll(List.of(btn_add, lbl_add_page, lbl_add_link, lbl_add_title, txt_add_title, txt_add_page, txt_add_link));

        addStage.setTitle("Add Bookmark");
        addStage.setScene(new Scene(pane, dB.fontUpdater.popupWidth, dB.fontUpdater.popupHeight));
        addStage.getIcons().add(notification.icon);
        addStage.show();

        dB.fontUpdater.updateFont(dB.addNodes);
    }
}
