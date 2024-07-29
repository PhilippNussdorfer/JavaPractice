package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;
import javafx.scene.control.Alert;
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
                                                       Stage stage, String title, String page, String link) {

        var res = dB.getHandler().editBookmark(id, title, page, link);
        if (res) {
            notification.notify("Edited Bookmark for: " + title, Alert.AlertType.INFORMATION);

            dB.getViewNodes().clear();
            gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), notification, dB);

            dB.getSearch().updateSearch(dB.getGridSearch(), gridBuilder, notification, dB);
            dB.getHandler().saveInFile();
            stage.close();
        } else {
            notification.notify("Please use a link that is usable, this link is invalid: ' " + link + " '!", Alert.AlertType.ERROR);
        }
    }

    public void deleteOrganiseAndReload(int id, String title, GridBuilder gridBuilder, DependencyBundle dB, Notification notification) {
        dB.getHandler().getBookmarks().remove(id);
        dB.getHandler().collapseBookmarks();
        dB.getHandler().saveInFile();

        notification.notify("Deleted " + title, Alert.AlertType.INFORMATION);
        dB.getViewNodes().clear();

        gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), notification, dB);
        dB.getSearch().updateSearch(dB.getGridSearch(), gridBuilder, notification, dB);
    }
}
