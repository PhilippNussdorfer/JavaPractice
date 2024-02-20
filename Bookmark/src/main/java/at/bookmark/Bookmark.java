package at.bookmark;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

@Getter
public class Bookmark {
    private String title;
    private String page;
    private String link;

    public Bookmark(String title, String page, String link) {
        this.title = title;
        this.page = page;
        this.link = link;
    }

    public JButton getLinkBtn() {
        JButton linkBtn = new JButton("Book Link");
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        ContentHandler handler = new ContentHandler();

        linkBtn.addActionListener(e -> {
            if (desktop != null) {
                try {
                    desktop.browse(new URI(link));
                } catch (Exception exception) {
                    handler.createErrorWindow("An error Occurred while trying to connect to the page.");
                }
            } else {
                handler.createErrorWindow("desktop function is not supported.");
            }
        });

        return linkBtn;
    }

    public JLabel getTitleLabel() {
        return new JLabel(this.title);
    }

    public JLabel getPageLabel() {
        return new JLabel(this.page);
    }

    private void edit(String title, String page, String link) {
        if (!title.trim().isEmpty()) {
            this.title = title;
        }

        if (!page.trim().isEmpty()) {
            this.page = page;
        }

        if (!link.trim().isEmpty() && new ContentHandler().isLink(link)) {
            this.link = link;
        }
    }

    public void editForm(JPanel content, ContentHandler handler) {
        JTextField txtTitle = new JTextField(this.title);
        JLabel lblTitle = new JLabel("Book Title:");
        JTextField txtPage = new JTextField(this.page);
        JLabel lblPage = new JLabel("Book Page:");
        JTextField txtLink = new JTextField(this.link);
        JLabel lblLink = new JLabel("Book Link:");
        JButton btnAddContent = new JButton("Edit Bookmark");

        Window window = new Window("Edit Bookmark", lblTitle, txtTitle, lblPage, txtPage, lblLink, txtLink, btnAddContent);
        window.setGrid(4, 1, 10, 10);

        btnAddContent.addActionListener(e -> {
            this.edit(txtTitle.getText(), txtPage.getText(), txtLink.getText());
            handler.updateContent(content);
        });

        window.createWindow(480, 240);
    }
}
