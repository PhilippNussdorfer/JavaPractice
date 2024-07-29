package at.bookmark.bookmark_javafx.tools;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.GridBuilder;
import at.bookmark.bookmark_javafx.GUITools.Notification;
import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import at.bookmark.bookmark_javafx.bookmark.BookmarkHandler;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SearchTest {

    private Search search;
    @Mock
    private DependencyBundle dependencyBundle;
    @Mock
    private List<Bookmark> bookmarks;
    @Mock
    private BookmarkHandler handler;
    @Mock
    private Bookmark bookmark;
    @Mock
    private Bookmark bookmarkSec;
    @Mock
    private Bookmark bookmarkTired;
    @Mock
    private Iterator<Bookmark> iterator;
    @Mock
    private GridBuilder gridBuilder;
    @Mock
    private GridPane gridPane;
    @Mock
    private Notification notification;

    @BeforeEach
    void setUp() {
        search = new Search();
    }

    @Test
    void searchForBookmark() {
        Mockito.when(dependencyBundle.getHandler()).thenReturn(handler);
        Mockito.when(handler.getBookmarks()).thenReturn(bookmarks);
        Mockito.when(bookmarks.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true, true, true, false);
        Mockito.when(iterator.next()).thenReturn(bookmark, bookmarkSec, bookmarkTired);
        Mockito.when(bookmark.getTitle()).thenReturn("LEO");
        Mockito.when(bookmarkSec.getTitle()).thenReturn("lego");
        Mockito.when(bookmarkTired.getTitle()).thenReturn("Hello");

        var result = search.searchForBookmark("lE", dependencyBundle);

        assertEquals(result.get(0), bookmark);
        assertEquals(result.get(1), bookmarkSec);
    }

    @Test
    void updateSearch() {
        String searchedWord = "no";
        Mockito.when(dependencyBundle.getSearchFieldInput()).thenReturn(searchedWord);
        Mockito.when(dependencyBundle.getHandler()).thenReturn(handler);
        Mockito.when(handler.getBookmarks()).thenReturn(bookmarks);
        Mockito.when(bookmarks.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(false);

        search.updateSearch(gridPane, gridBuilder, notification, dependencyBundle);

        Mockito.verify(gridBuilder, Mockito.times(1)).setGrid(gridPane, search.searchForBookmark(searchedWord, dependencyBundle), notification, dependencyBundle);
    }
}