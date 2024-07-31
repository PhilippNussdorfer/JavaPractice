package at.bookmark.bookmark_javafx.bookmark;

import at.bookmark.bookmark_javafx.save_and_load.WriterReader;
import lombok.Getter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookmarkHandler {
    @Getter
    private List<Bookmark> bookmarks = new ArrayList<>();
    private final WriterReader writerReader;

    public BookmarkHandler(WriterReader writerReader, BufferedReader reader) {
        this.writerReader = writerReader;
        initBookmarks(reader);
    }

    private void initBookmarks(BufferedReader reader) {
        bookmarks = writerReader.loadFile(reader);
    }

    private boolean isLink(String link) {
        try {
            URL url = URI.create(link).toURL();
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException | IllegalArgumentException e) {
            return false;
        }
    }

    public void saveInFile(BufferedWriter writer) {
        writerReader.writeFile(bookmarks, writer);
    }

    public boolean editBookmark(int id, String title, String page, String link) {
        if (isLink(link)) {
            bookmarks.get(id).setTitle(title);
            bookmarks.get(id).setPage(page);
            bookmarks.get(id).setLink(link);
            return true;
        }
        return false;
    }

    public boolean addNewBookmark(String title, String page, String link) {
        if (isLink(link)) {
            int num = bookmarks.get(bookmarks.size() - 1).getNum();
            bookmarks.add(new Bookmark(title, page, link, num + 1));
            return true;
        }
        return false;
    }

    public void collapseBookmarks() {
        List<Bookmark> collapsedBookmark = new ArrayList<>();
        int count = 0;

        for (Bookmark bookmark : bookmarks) {
            bookmark.setNum(count);
            collapsedBookmark.add(bookmark);
            count++;
        }

        bookmarks = collapsedBookmark;
    }
}
