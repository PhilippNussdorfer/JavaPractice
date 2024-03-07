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

    public void updateContentPanel(JPanel contentPanel) {
        contentPanel.removeAll();
        int count = 1;

        initTop(contentPanel);

        for (Bookmark bookmark : bookmarks) {
            contentPanel.add(new JLabel("" + count));
            contentPanel.add(bookmark.getTitleLabel());
            contentPanel.add(bookmark.getPageLabel());
            contentPanel.add(bookmark.getLinkBtn());

            JButton btnEdit = new JButton("edit");
            JButton btnRemove = new JButton("Remove");

            btnEdit.addActionListener(e -> bookmark.editForm(contentPanel, this));
            btnRemove.addActionListener(e -> {
                bookmarks.remove(bookmark);
                this.updateContentPanel(contentPanel);
            });

            contentPanel.add(btnEdit);
            contentPanel.add(btnRemove);

            count++;
        }
        contentPanel.updateUI();
        readerAndWriter.writeFile(bookmarks);
    }

    private void initTop(JPanel contentPanel) {
        contentPanel.add(new JLabel("Count"));
        contentPanel.add(new JLabel("Title"));
        contentPanel.add(new JLabel("Page"));
        contentPanel.add(new JLabel(""));
        contentPanel.add(new JLabel(""));
        contentPanel.add(new JLabel(""));
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
