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

    public TextField searchLogic(DependencyBundle dB, Notification notification, GridBuilder gridBuilder) {
        TextField txt_search = new TextField();
        txt_search.setPrefWidth(860);
        txt_search.textProperty().addListener((observer, oldValue, newValue) -> dB.getLogic().updateViewAndSearchView(dB, notification, gridBuilder, newValue));

        return txt_search;
    }

    public List<Bookmark> searchForBookmark(String input, DependencyBundle dB) {
        List<Bookmark> results = new ArrayList<>();
        if (input.isEmpty())
            return results;

        for (Bookmark bookmark : dB.getHandler().getBookmarks()) {
            if (bookmark.getTitle().toLowerCase().contains(input.toLowerCase())) {
                results.add(bookmark);
            }
        }

        return results;
    }

    public void updateSearch(GridPane grid, GridBuilder gridBuilder, Notification notification, DependencyBundle dB) {
        gridBuilder.setGrid(grid, searchForBookmark(dB.getSearchFieldInput(), dB), notification, dB);
    }
}
