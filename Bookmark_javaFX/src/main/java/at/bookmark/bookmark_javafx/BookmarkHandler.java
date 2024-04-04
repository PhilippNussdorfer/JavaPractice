package at.bookmark.bookmark_javafx;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookmarkHandler {
    private List<Bookmark> bookmarks = new ArrayList<>();
    private final WriterReader writerReader = new WriterReader();

    public BookmarkHandler() {
        init();
    }

    private void init() {
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

    public void save() {
        writerReader.writeFile(bookmarks);
    }

    public boolean edit(int id, String title, String page, String link) {
        if (isLink(link)) {
            bookmarks.get(id).setTitle(title);
            bookmarks.get(id).setPage(page);
            bookmarks.get(id).setLink(link);
            return true;
        }
        return false;
    }

    public boolean add(String title, String page, String link) {
        if (isLink(link)) {
            int num = bookmarks.get(bookmarks.size() - 1).getNumeration();
            bookmarks.add(new Bookmark(num + 1, title, page, link));
            return true;
        }
        return false;
    }

    public List<Bookmark> getBookmarks() {
        return this.bookmarks;
    }
}
