package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;
import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchTest {
    Search search;

    @Mock
    DependencyBundle db;
    @Mock
    Notification notification;
    @Mock
    GridBuilder gridBuilder;
    @Mock
    Bookmark bookmark;
    @Mock
    GridPane grid;

    @BeforeEach
    void setUp() {
        search = new Search();
    }

    @Test
    void searchLogic() {
        //var txtField = search.searchLogic(db, notification, gridBuilder);
    }

    @Test
    void updateSearch() {
        /*var txtField = search.searchLogic(db, notification, gridBuilder);
        txtField.setText("Hello world");
        Mockito.when(db.getSearchField()).thenReturn(new TextField());

        search.updateSearch(grid, gridBuilder, notification, db);
        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(Mockito.any(GridPane.class), Mockito.anyList(), Mockito.any(Notification.class), Mockito.any(DependencyBundle.class));*/
    }
}