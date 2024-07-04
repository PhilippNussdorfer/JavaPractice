package at.bookmark.bookmark_javafx.GUITools;

import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import window.WindowCalc;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class EditWindowBuilder {

    public void editWindow(int id, Notification notification, GridBuilder gridBuilder, GridPane grid, DependencyBundle dB) {
        dB.editNodes.clear();

        Stage editStage = new Stage();
        WindowCalc.setStagePosition(editStage, notification.getX() + 200, notification.getY() + 200);
        Bookmark bookmark = dB.handler.getBookmarks().get(id);

        TextField txt_edit_title = new TextField(bookmark.getTitle());
        TextField txt_edit_page = new TextField(bookmark.getPage());
        TextField txt_edit_link = new TextField(bookmark.getLink());

        Label lbl_edit_title = new Label("Title:");
        Label lbl_edit_page = new Label("Page:");
        Label lbl_edit_link = new Label("Link:");

        Button btn_edit = new Button("Save Changes");

        btn_edit.setOnAction(e -> {
            var res = dB.handler.editBookmark(id, txt_edit_title.getText(), txt_edit_page.getText(), txt_edit_link.getText());
            if (res) {
                notification.notify("Edited Bookmark for: " + txt_edit_title.getText(), Alert.AlertType.INFORMATION);

                dB.viewNodes.clear();

                gridBuilder.setGrid(grid, dB.handler.getBookmarks(), notification, dB);
                dB.handler.saveInFile();
                editStage.close();
            } else {
                notification.notify("Please use a link that is usable, this link is invalid: ' " + txt_edit_link.getText() + " '!", Alert.AlertType.ERROR);
            }
        });

        GridPane pane = new GridPane();
        pane.add(lbl_edit_title, 0, 0);
        pane.add(txt_edit_title, 1, 0);
        pane.add(lbl_edit_page, 0, 1);
        pane.add(txt_edit_page, 1, 1);
        pane.add(lbl_edit_link, 0 , 2);
        pane.add(txt_edit_link, 1, 2);
        pane.add(btn_edit, 0, 3);

        dB.editNodes.addAll(List.of(btn_edit, lbl_edit_page, lbl_edit_link, lbl_edit_title, txt_edit_page, txt_edit_link, txt_edit_title));

        editStage.setTitle("Add Bookmark");
        editStage.setScene(new Scene(pane, dB.fontUpdater.getPopupWidth(), dB.fontUpdater.getPopupHeight()));
        editStage.getIcons().add(notification.getIcon());
        editStage.show();

        dB.fontUpdater.updateFont(dB.editNodes);
    }
}
