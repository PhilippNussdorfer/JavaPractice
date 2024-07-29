package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;
import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import at.bookmark.bookmark_javafx.bookmark.BookmarkHandler;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class LogicCoreTest {

    public static final String TITLE = "Some Book Title";
    public static final String PAGE = "12";
    public static final String LINK = "http://SOME/unsecure/Page";
    private LogicCore logic;
    private final int INDEX = 1;

    @Mock
    private DependencyBundle dependencyBundle;
    @Mock
    private Notification notification;
    @Mock
    private GridBuilder gridBuilder;
    @Mock
    private List<Bookmark> bookmarks;
    @Mock
    private List<Bookmark> result;
    @Mock
    private GridPane gridPane;
    @Mock
    private ObservableList<Node> nodes;
    @Mock
    private Search search;
    @Mock
    private List<Node> viewNodes;
    @Mock
    private BookmarkHandler handler;
    @Mock
    Stage stage;

    @BeforeEach
    void setUp() {
        logic = new LogicCore();
    }

    @Test
    void searchViewGetsCleared() {
        String EMPTY_INPUT = "";
        Mockito.when(dependencyBundle.getGridSearch()).thenReturn(gridPane);
        Mockito.when(gridPane.getChildren()).thenReturn(nodes);

        logic.updateViewAndSearchView(dependencyBundle, notification, gridBuilder, EMPTY_INPUT);

        Mockito.verify(nodes, Mockito.times(1)).clear();
    }

    @Test
    void searchForItemsInBookmark() {
        String INPUT = "lE";
        mockitoInit();
        Mockito.when(search.searchForBookmark(INPUT, dependencyBundle)).thenReturn(bookmarks);
        Mockito.when(search.searchForBookmark(INPUT, dependencyBundle)).thenReturn(result);

        logic.updateViewAndSearchView(dependencyBundle, notification, gridBuilder, INPUT);

        Mockito.verify(viewNodes, Mockito.times(1)).clear();
        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(gridPane, result, notification, dependencyBundle);
        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(gridPane, bookmarks, notification, dependencyBundle);
    }

    @Test
    void failedAtAdding() {
        Mockito.when(dependencyBundle.getHandler()).thenReturn(handler);
        Mockito.when(handler.addNewBookmark(TITLE, PAGE, LINK)).thenReturn(false);

        logic.refreshGridsAndSaveChangesAfterAdding(notification, gridBuilder, dependencyBundle, stage, TITLE, PAGE, LINK);

        Mockito.verify(notification, Mockito.times(1)).notify("Please use a link that is usable, this link is invalid: ' http://SOME/unsecure/Page '!", Alert.AlertType.ERROR);
    }

    @Test
    void addAndUpdateViewAndSaveChanges() {
        mockitoInit();
        Mockito.when(handler.addNewBookmark(TITLE, PAGE, LINK)).thenReturn(true);


        logic.refreshGridsAndSaveChangesAfterAdding(notification, gridBuilder, dependencyBundle, stage, TITLE, PAGE, LINK);

        Mockito.verify(notification, Mockito.times(1)).notify("Added Bookmark for: Some Book Title", Alert.AlertType.INFORMATION);
        Mockito.verify(viewNodes, Mockito.times(1)).clear();
        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(gridPane, bookmarks, notification, dependencyBundle);
        Mockito.verify(search, Mockito.times(1)).updateSearch(gridPane, gridBuilder, notification, dependencyBundle);
        Mockito.verify(handler, Mockito.times(1)).saveInFile();
        Mockito.verify(stage, Mockito.times(1)).close();
    }

    @Test
    void failedAtEditing() {
        Mockito.when(dependencyBundle.getHandler()).thenReturn(handler);
        Mockito.when(handler.editBookmark(INDEX, TITLE, PAGE, LINK)).thenReturn(false);

        logic.refreshGridsAndSaveChangesAfterEditing(INDEX, notification, gridBuilder, dependencyBundle, stage, TITLE, PAGE, LINK);

        Mockito.verify(notification, Mockito.times(1)).notify("Please use a link that is usable, this link is invalid: ' http://SOME/unsecure/Page '!", Alert.AlertType.ERROR);
    }

    @Test
    void editAndUpdateViewAndSaveChanges() {
        mockitoInit();
        Mockito.when(handler.editBookmark(INDEX, TITLE, PAGE, LINK)).thenReturn(true);


        logic.refreshGridsAndSaveChangesAfterEditing(INDEX, notification, gridBuilder, dependencyBundle, stage, TITLE, PAGE, LINK);

        Mockito.verify(notification, Mockito.times(1)).notify("Edited Bookmark for: Some Book Title", Alert.AlertType.INFORMATION);
        Mockito.verify(viewNodes, Mockito.times(1)).clear();
        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(gridPane, bookmarks, notification, dependencyBundle);
        Mockito.verify(search, Mockito.times(1)).updateSearch(gridPane, gridBuilder, notification, dependencyBundle);
        Mockito.verify(handler, Mockito.times(1)).saveInFile();
        Mockito.verify(stage, Mockito.times(1)).close();
    }

    @Test
    void deleteRefreshAndSave() {
        mockitoInit();

        logic.deleteOrganiseAndReload(INDEX, TITLE, gridBuilder, dependencyBundle, notification);

        Mockito.verify(bookmarks, Mockito.times(1)).remove(INDEX);
        Mockito.verify(handler, Mockito.times(1)).collapseBookmarks();
        Mockito.verify(handler, Mockito.times(1)).saveInFile();
        Mockito.verify(notification, Mockito.times(1)).notify("Deleted Some Book Title", Alert.AlertType.INFORMATION);
        Mockito.verify(viewNodes, Mockito.times(1)).clear();
        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(gridPane, bookmarks, notification, dependencyBundle);
        Mockito.verify(search, Mockito.times(1)).updateSearch(gridPane, gridBuilder, notification, dependencyBundle);
    }

    private void mockitoInit() {
        Mockito.when(dependencyBundle.getHandler()).thenReturn(handler);
        Mockito.when(dependencyBundle.getViewNodes()).thenReturn(viewNodes);
        Mockito.when(dependencyBundle.getGridMain()).thenReturn(gridPane);
        Mockito.when(dependencyBundle.getGridSearch()).thenReturn(gridPane);
        Mockito.when(handler.getBookmarks()).thenReturn(bookmarks);
        Mockito.when(dependencyBundle.getSearch()).thenReturn(search);
    }
}