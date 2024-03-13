package at.bookmark.bookmark_javafx;

public class Bookmark {
    private String title;
    private String page;
    private String link;

    public Bookmark(String title, String page, String link) {
        this.title = title;
        this.page = page;
        this.link = link;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPage() {
        return this.page;
    }

    public String getLink() {
        return this.link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
