package at.bookmark.bookmark_javafx.GUITools;

import window.WindowCalc;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class AddWindowBuilder {

    public void addWindow(Notification notification, GridBuilder gridBuilder, DependencyBundle dB) {
        dB.getAddNodes().clear();

        Stage addStage = new Stage();
        WindowCalc.setStagePosition(addStage, notification.getX() + 200, notification.getY() + 200);

        TextField txt_add_title = new TextField();
        TextField txt_add_page = new TextField();
        TextField txt_add_link = new TextField();

        Label lbl_add_title = new Label("Title:");
        Label lbl_add_page = new Label("Page:");
        Label lbl_add_link = new Label("Link:");

        Button btn_add = new Button("Add New Bookmark");

        btn_add.setOnAction(e -> dB.getLogic().refreshGridsAndSaveChangesAfterAdding(notification, gridBuilder, dB, addStage, txt_add_title, txt_add_page, txt_add_link));

        GridPane pane = new GridPane();
        pane.add(lbl_add_title, 0, 0);
        pane.add(txt_add_title, 1, 0);
        pane.add(lbl_add_page, 0, 1);
        pane.add(txt_add_page, 1, 1);
        pane.add(lbl_add_link, 0 , 2);
        pane.add(txt_add_link, 1, 2);
        pane.add(btn_add, 0, 3);

        dB.getAddNodes().addAll(List.of(btn_add, lbl_add_page, lbl_add_link, lbl_add_title, txt_add_title, txt_add_page, txt_add_link));

        addStage.setTitle("Add Bookmark");
        addStage.setScene(new Scene(pane, dB.getFontUpdater().getPopupWidth(), dB.getFontUpdater().getPopupHeight()));
        addStage.getIcons().add(notification.getIcon());
        addStage.show();

        dB.getFontUpdater().updateFont(dB.getAddNodes());
    }
}
