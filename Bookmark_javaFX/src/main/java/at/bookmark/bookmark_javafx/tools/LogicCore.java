package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;

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
}
