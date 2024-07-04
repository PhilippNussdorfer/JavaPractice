package at.bookmark.bookmark_javafx.GUITools;

import at.bookmark.bookmark_javafx.Bookmark;
import javafx.application.HostServices;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;

public class GridBuilder {
    private final EditWindowBuilder edit = new EditWindowBuilder();
    private final HostServices services;

    public GridBuilder(HostServices services) {
        this.services = services;
    }

    public void setGrid(GridPane grid, List<Bookmark> bookmarks, Notification notification, DependencyBundle dB) {
        grid.getChildren().clear();
        int count = 0;

        for (Bookmark bookmark : bookmarks) {

            Label lbl_view_num = new Label((bookmark.getNumeration() + 1) + "");
            Label lbl_view_page = new Label(bookmark.getTitle());
            Label lbl_view_title = new Label(bookmark.getPage());

            Button btn_view_remove = new Button("Delete");
            Button btn_view_edit = new Button("Edit");
            Button btn_view_link = new Button("Book Link");

            dB.viewNodes.addAll(List.of(lbl_view_page, lbl_view_title, lbl_view_num, btn_view_remove, btn_view_edit, btn_view_link));

            int id = bookmark.getNumeration();
            btn_view_link.setOnAction(e -> services.showDocument(bookmark.getLink()));
            btn_view_edit.setOnAction(e -> edit.editWindow(id, notification, this, grid, dB));
            btn_view_remove.setOnAction(e -> notification.deleteNotify(id, bookmark.getTitle(), grid, this, dB));

            grid.add(lbl_view_num, 0, count);
            grid.add(lbl_view_title, 1, count);
            grid.add(lbl_view_page, 2, count);
            grid.add(btn_view_edit, 3, count);
            grid.add(btn_view_remove, 4, count);
            grid.add(btn_view_link, 5, count);

            count ++;
        }

        dB.fontUpdater.updateFont(dB.viewNodes);
    }
}
