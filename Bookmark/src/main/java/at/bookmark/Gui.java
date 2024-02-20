package at.bookmark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gui {
    private JPanel mainPanel;
    private JTextField searchBar;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnEdit;
    private JPanel contentPanel;
    private JPanel searchPanel;
    private JScrollPane scrollContent;
    private JScrollPane scrollSearch;
    private final ContentHandler handler = new ContentHandler();
    private final JFrame frame = new JFrame("Bookmark");

    public static void main(String[] args) {
        Gui gui = new Gui();

        gui.frame.setContentPane(gui.mainPanel);
        gui.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.frame.setSize(540, 480);
        gui.frame.setVisible(true);

        gui.initButtons();
        gui.initContentPanelAndSearchPanel();
        gui.initDesign();
    }

    private void initDesign() {
        frame.setIconImage(handler.icon.getImage());
        mainPanel.setBackground(Color.GRAY);
    }

    private void addContent() {

        JTextField txtTitle = new JTextField();
        JLabel lblTitle = new JLabel("Book Title:");
        JTextField txtPage = new JTextField();
        JLabel lblPage = new JLabel("Book Page:");
        JTextField txtLink = new JTextField();
        JLabel lblLink = new JLabel("Book Link:");
        JButton btnAddContent = new JButton("Add Bookmark");

        Window window = new Window("Add Bookmark", lblTitle, txtTitle, lblPage, txtPage, lblLink, txtLink, btnAddContent);
        window.setGrid(4, 1, 10, 10);

        btnAddContent.addActionListener(e -> {
            if (!txtTitle.getText().isEmpty() && !txtLink.getText().isEmpty() && handler.isLink(txtLink.getText())) {
                handler.addNewBookmark(txtTitle.getText(), txtPage.getText(), txtLink.getText());
                handler.updateContent(contentPanel);
            } else {
                handler.createErrorWindow("Could not add the Bookmark because there was no title or an invalid weblink");
            }
        });

        window.createWindow(320, 240);
    }

    private void initButtons() {
        btnAdd.addActionListener(e-> addContent());
        btnEdit.addActionListener(e-> {
            this.selectionFormEdit();
        });
        btnRemove.addActionListener(e-> {
            this.selectionFormDelete();
        });
    }

    private void initContentPanelAndSearchPanel() {
        GridLayout grid = new GridLayout(0,3, 10, 10);

        contentPanel.setLayout(grid);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        handler.addLoadedBookmarks();
        handler.updateContent(contentPanel);

        searchPanel.setLayout(new GridLayout(0, 3, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        scrollContent.getVerticalScrollBar().setUnitIncrement(10);
        scrollSearch.getVerticalScrollBar().setUnitIncrement(10);
        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent key) {
                searchPanel.removeAll();

                String searchWord = (searchBar.getText() + key.getKeyChar()).toLowerCase();

                for (Bookmark bookmark : handler.getBookmarks()) {
                    if (bookmark.getTitle().toLowerCase().contains(searchWord)) {
                        searchPanel.add(bookmark.getTitleLabel());
                        searchPanel.add(bookmark.getPageLabel());
                        searchPanel.add(bookmark.getLinkBtn());
                    }
                }

                searchPanel.revalidate();
                searchPanel.repaint();
            }
        });
    }

    private void selectionFormDelete() {
        JButton deleteBtn = new JButton("Remove");
        DefaultListModel<String> listModel = getStringDefaultListModel();

        JList jList = new JList<>(listModel);

        Window window = new Window("Select to Delete", jList, deleteBtn);
        window.setFlow();

        deleteBtn.addActionListener(e -> {
            if (!jList.isSelectionEmpty()) {
                handler.getBookmarks().remove(jList.getSelectedIndex());
                handler.updateContent(contentPanel);
                listModel.remove(jList.getSelectedIndex());
            } else {
                handler.createErrorWindow("Please Select an title from the list!");
            }
        });

        window.createWindow(480, 240);
    }

    private void selectionFormEdit() {
        JButton editBtn = new JButton("Edit Selected");

        JList jList = new JList<>(getStringDefaultListModel());
        contentPanel.add(jList);
        contentPanel.add(editBtn);

        Window window = new Window("Select to Edit", jList, editBtn);
        window.setFlow();

        editBtn.addActionListener(e -> {
            if (!jList.isSelectionEmpty()) {
                jList.setModel(getStringDefaultListModel());
                handler.getBookmarks().get(jList.getSelectedIndex()).editForm(contentPanel, handler);
            } else {
                handler.createErrorWindow("Please Select an title from the list!");
            }
        });

        window.createWindow(480, 240);
    }

    private DefaultListModel<String> getStringDefaultListModel() {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Bookmark bookmark : this.handler.getBookmarks()) {
            listModel.addElement(bookmark.getTitle());
        }
        return listModel;
    }
}
