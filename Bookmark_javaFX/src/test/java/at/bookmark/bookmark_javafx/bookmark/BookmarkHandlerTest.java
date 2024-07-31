package at.bookmark.bookmark_javafx.bookmark;

import at.bookmark.bookmark_javafx.save_and_load.WriterReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookmarkHandlerTest {
    BookmarkHandler handler;

    @Mock
    WriterReader writerReader;
    @Mock
    Bookmark bookmark;
    @Mock
    BufferedReader reader;
    @Mock
    BufferedWriter writer;


    @BeforeEach
    void setUp() {
        handler = new BookmarkHandler(writerReader);
        handler.getBookmarks().add(bookmark);
    }

    @Test
    void saveInFileAndLoadFile() {
        handler.saveInFile();

        Mockito.verify(writerReader, Mockito.times(1)).writeFile(handler.getBookmarks(), writer);
        Mockito.verify(writerReader, Mockito.times(1)).loadFile(reader);
    }

    @Test
    void editBookmark() {
        Mockito.when(bookmark.getTitle()).thenReturn("Some Title");
        Mockito.when(bookmark.getPage()).thenReturn("12");
        Mockito.when(bookmark.getLink()).thenReturn("https://de.wikipedia.org/wiki");

        assertTrue(handler.editBookmark(0, "Some Title", "12", "https://de.wikipedia.org/wiki"));
        assertEquals("Some Title", handler.getBookmarks().get(0).getTitle());
        assertEquals("12", handler.getBookmarks().get(0).getPage());
        assertEquals("https://de.wikipedia.org/wiki", handler.getBookmarks().get(0).getLink());
    }

    @Test
    void editBookmarkFailed() {
        assertFalse(handler.editBookmark(0, "Some Title", "12", "Hello/World"));
    }

    @Test
    void addNewBookmark() {
        assertTrue(handler.addNewBookmark("Some Title", "12", "https://de.wikipedia.org/wiki"));
        assertEquals("Some Title", handler.getBookmarks().get(1).getTitle());
        assertEquals("12", handler.getBookmarks().get(1).getPage());
        assertEquals("https://de.wikipedia.org/wiki", handler.getBookmarks().get(1).getLink());
    }

    @Test
    void addNewBookmarkFailed() {
        assertFalse(handler.addNewBookmark("Some Title", "12", "de.wikipedia.org/wiki"));
    }

    @Test
    void getBookmarks() {
        assertEquals(1, handler.getBookmarks().size());
        assertEquals(bookmark, handler.getBookmarks().get(0));
    }

    @Test
    void collapseBookmarks() {
        handler.collapseBookmarks();

        Mockito.verify(bookmark, Mockito.times(1)).setNum(0);
    }
}