package at.bookmark;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ContentHandler {

    @Getter
    private final List<Bookmark> bookmarks = new ArrayList<>();
    @Getter
    ImageIcon icon;
    private final ReaderAndWriter readerAndWriter = new ReaderAndWriter();

    public ContentHandler() {
        URL url = ClassLoader.getSystemResource("icon/Bookmark.png");
        if (url != null) {
            icon = new ImageIcon(url);
        }
    }

    public void addLoadedBookmarks() {
        this.bookmarks.addAll(readerAndWriter.loadFile());
    }

    public void addNewBookmark(String title, String page, String link) {
        Bookmark bookmark = new Bookmark(title, page, link);
        bookmarks.add(bookmark);
    }

    public void updateContent(JPanel content) {
        content.removeAll();
        for (Bookmark bookmark : bookmarks) {
            content.add(bookmark.getTitleLabel());
            content.add(bookmark.getPageLabel());
            content.add(bookmark.getLinkBtn());
        }
        content.updateUI();
        readerAndWriter.writeFile(bookmarks);
    }

    public boolean isLink(String link) {
        try {
            new URL(link).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public void createErrorWindow(String errorMsg) {
        JLabel lblErrorTxt = new JLabel(errorMsg, SwingConstants.CENTER);
        lblErrorTxt.setForeground(Color.red);
        JButton okBtn = new JButton("OK");

        Window errWindow = new Window("Error", lblErrorTxt, okBtn);
        errWindow.setGrid(2, 1, 10, 10);

        okBtn.addActionListener(e -> {
            errWindow.closeWindow();
        });

        errWindow.createWindow(320, 120);
    }
}
