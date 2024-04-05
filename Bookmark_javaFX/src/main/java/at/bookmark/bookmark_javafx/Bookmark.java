package at.bookmark.bookmark_javafx;

public class Bookmark {
    private String title;
    private String page;
    private String link;
    private int num;

    public Bookmark(int num, String title, String page, String link) {
        this.title = title;
        this.page = page;
        this.link = link;
        this.num = num;
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

    public int getNumeration() {
        return this.num;
    }

    public void setNumeration(int num) {
        this.num = num;
    }
}
