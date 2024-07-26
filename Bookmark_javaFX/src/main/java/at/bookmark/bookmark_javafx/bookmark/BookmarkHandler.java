package at.bookmark.bookmark_javafx.bookmark;

import at.bookmark.bookmark_javafx.save_and_load.WriterReader;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookmarkHandler {
    @Getter
    private List<Bookmark> bookmarks = new ArrayList<>();
    private final WriterReader writerReader = new WriterReader();

    public BookmarkHandler() {
        initBookmarks();
    }

    private void initBookmarks() {
        bookmarks = writerReader.loadFile();
    }

    private boolean isLink(String link) {
        try {
            URL url = new URL(link);
            url.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public void saveInFile() {
        writerReader.writeFile(bookmarks);
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
