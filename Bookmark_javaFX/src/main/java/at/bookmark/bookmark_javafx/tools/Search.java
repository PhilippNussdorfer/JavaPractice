package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;
import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Search {
    //private final TextField txt_search = new TextField(); <-- Brauch input von irgend woher

    public TextField searchLogic(GridPane gridSearch, GridPane gridMain, DependencyBundle dependencyBundle, Notification notification, GridBuilder gridBuilder) {
        TextField txt_search = new TextField();
        txt_search.setPrefWidth(860);
        txt_search.textProperty().addListener((observer, oldValue, newValue) -> {

            if (newValue.equals("")) {
                gridSearch.getChildren().clear();
            } else {
                var results = dependencyBundle.search.searchForBookmark(newValue, dependencyBundle);

                dependencyBundle.viewNodes.clear();

                gridBuilder.setGrid(gridMain, gridSearch, results, notification, dependencyBundle);
                gridBuilder.setGrid(gridMain, gridSearch, dependencyBundle.handler.getBookmarks(), notification, dependencyBundle);
            }
        });

        return txt_search;
    }

    public List<Bookmark> searchForBookmark(String input, DependencyBundle dB) {
        List<Bookmark> results = new ArrayList<>();

        for (Bookmark bookmark : dB.handler.getBookmarks()) {
            if (bookmark.getTitle().toLowerCase().contains(input.toLowerCase())) {
                results.add(bookmark);
            }
        }

        return results;
    }

    public void updateSearch(GridPane gridMain, GridPane gridSearch, GridBuilder gridBuilder, Notification notification, DependencyBundle dB) {
        gridBuilder.setGrid(gridMain, gridSearch, searchForBookmark("txt_search.getText()", dB), notification, dB);
    }
}
