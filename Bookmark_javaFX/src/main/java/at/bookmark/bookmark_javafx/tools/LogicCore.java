package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogicCore {
    public void updateViewAndSearchView(DependencyBundle dB, Notification notification, GridBuilder gridBuilder, String newValue) {
        if (newValue.equals("")) {
            dB.getGridSearch().getChildren().clear();
        } else {
            var results = dB.getSearch().searchForBookmark(newValue, dB);

            dB.getViewNodes().clear();

            gridBuilder.setGrid(dB.getGridSearch(), results, notification, dB);
            gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), notification, dB);
        }
    }

    public void refreshGridsAfterAdding(Notification notification, GridBuilder gridBuilder, DependencyBundle dB, Stage addStage, TextField txt_add_title, TextField txt_add_page, TextField txt_add_link) {
        var res = dB.getHandler().addNewBookmark(txt_add_title.getText(), txt_add_page.getText(), txt_add_link.getText());
        if (res) {
            notification.notify("Added Bookmark for: " + txt_add_title.getText(), Alert.AlertType.INFORMATION);

            dB.getViewNodes().clear();

            gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), notification, dB);
            dB.getSearch().updateSearch(dB.getGridSearch(), gridBuilder, notification, dB);

            dB.getHandler().saveInFile();
            addStage.close();
        } else {
            notification.notify("Please use a link that is usable, this link is invalid: ' " + txt_add_link.getText() + " '!", Alert.AlertType.ERROR);
        }
    }
}
