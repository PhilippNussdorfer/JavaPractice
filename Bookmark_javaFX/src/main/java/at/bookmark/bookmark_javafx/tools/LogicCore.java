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

    public void refreshGridsAndSaveChangesAfterAdding(Notification notification, GridBuilder gridBuilder, DependencyBundle dB, Stage stage, String title, String page, String link) {
        var res = dB.getHandler().addNewBookmark(title, page, link);
        if (res) {
            notification.notify("Added Bookmark for: " + title, Alert.AlertType.INFORMATION);

            dB.getViewNodes().clear();
            gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), notification, dB);

            dB.getSearch().updateSearch(dB.getGridSearch(), gridBuilder, notification, dB);
            dB.getHandler().saveInFile();
            stage.close();
        } else {
            notification.notify("Please use a link that is usable, this link is invalid: ' " + link + " '!", Alert.AlertType.ERROR);
        }
    }

    public void refreshGridsAndSaveChangesAfterEditing(int id, Notification notification, GridBuilder gridBuilder, DependencyBundle dB,
                                                       Stage stage, TextField txt_edit_title, TextField txt_edit_page, TextField txt_edit_link) {

        var res = dB.getHandler().editBookmark(id, txt_edit_title.getText(), txt_edit_page.getText(), txt_edit_link.getText());
        if (res) {
            notification.notify("Edited Bookmark for: " + txt_edit_title.getText(), Alert.AlertType.INFORMATION);

            dB.getViewNodes().clear();
            gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), notification, dB);

            dB.getSearch().updateSearch(dB.getGridSearch(), gridBuilder, notification, dB);
            dB.getHandler().saveInFile();
            stage.close();
        } else {
            notification.notify("Please use a link that is usable, this link is invalid: ' " + txt_edit_link.getText() + " '!", Alert.AlertType.ERROR);
        }
    }
}
