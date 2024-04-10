package at.bookmark.bookmark_javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Bookmark_App extends Application {

    private final BookmarkHandler handler = new BookmarkHandler();
    private final GridPane gridSearch = new GridPane();
    private final GridPane gridMain = new GridPane();
    private Font appFont = new Font(16);
    private final int popupWidth = 360;
    private final int popupHeight = 120;
    private final Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/logo/Bookmark.png")));

    private final List<Button> buttons = new ArrayList<>();
    private final List<Label> labels = new ArrayList<>();
    private final List<TextField> txtFields = new ArrayList<>();
    private final List<Button> viewBtn_s = new ArrayList<>();
    private final List<Label> viewLbl_s = new ArrayList<>();

    private Label lbl_search;
    private TextField txt_search;
    private Button btn_add;

    private TextField txt_edit_title;
    private TextField txt_edit_page;
    private TextField txt_edit_link;
    private Label lbl_edit_title;
    private Label lbl_edit_page;
    private Label lbl_edit_link;
    private Button btn_edit_edit;

    private TextField txt_add_title;
    private TextField txt_add_page;
    private TextField txt_add_link;
    private Label lbl_add_title;
    private Label lbl_add_page;
    private Label lbl_add_link;
    private Button btn_add_add;

    public void launch_app() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        initNodes();
        int width = 1280;
        int height = 640;

        gridMain.setHgap(10);
        gridMain.setVgap(10);
        gridMain.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));

        gridSearch.setHgap(10);
        gridSearch.setVgap(10);

        lbl_search.setText("Search:");
        btn_add.setText("Add");

        txt_search.setPrefWidth(width - (double) (width / 3));
        txt_search.textProperty().addListener((observer, oldValue, newValue) -> {

            if (newValue.equals("")) {
                gridSearch.getChildren().clear();
            } else {
                var results = searchForBookmark(newValue);

                viewLbl_s.clear();
                viewBtn_s.clear();

                setGrid(gridSearch, results);
                setGrid(gridMain, handler.getBookmarks());
            }
        });

        btn_add.setOnAction(e -> addWindow());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(lbl_search, 0, 0);
        grid.add(txt_search, 1, 0);
        grid.add(btn_add, 2, 0);
        grid.add(gridSearch, 1, 1);
        grid.add(gridMain, 1, 2);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToHeight(true);

        MenuBar menu = new MenuBar();
        Menu fontSize = new Menu("Font Size");

        for (int i = 8; i < 34; i += 2) {
            MenuItem size = new MenuItem(i + "");
            int tmp = i;

            size.setOnAction(t -> {
                appFont = new Font(tmp);
                updateFont();
            });

            fontSize.getItems().add(size);
        }

        menu.getMenus().add(fontSize);

        VBox vbox = new VBox(menu, scrollPane);

        Scene scene = new Scene(vbox, width, height);
        scene.setFill(Color.LIGHTGRAY);

        stage.setTitle("Bookmark");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();

        setGrid(gridMain, handler.getBookmarks());
    }

    private void editWindow(int id) {
        Stage editStage = new Stage();
        Bookmark bookmark = handler.getBookmarks().get(id);

        txt_edit_title.setText(bookmark.getTitle());
        txt_edit_page.setText(bookmark.getPage());
        txt_edit_link.setText(bookmark.getLink());

        lbl_edit_title.setText("Title:");
        lbl_edit_page.setText("Page:");
        lbl_edit_link.setText("Link:");

        btn_edit_edit.setText("Save Changes");

        btn_edit_edit.setOnAction(e -> {
            var res = handler.edit(id, txt_edit_title.getText(), txt_edit_page.getText(), txt_edit_link.getText());
            if (res) {
                notification("Edited Bookmark for: " + txt_edit_title.getText(), Alert.AlertType.INFORMATION);

                viewBtn_s.clear();
                viewLbl_s.clear();

                setGrid(gridMain, handler.getBookmarks());
                handler.save();
                editStage.close();
            } else {
                notification("Please use a link that is usable, this link is invalid: ' " + txt_edit_link.getText() + " '!", Alert.AlertType.ERROR);
            }
        });

        GridPane pane = new GridPane();
        pane.add(lbl_edit_title, 0, 0);
        pane.add(txt_edit_title, 1, 0);
        pane.add(lbl_edit_page, 0, 1);
        pane.add(txt_edit_page, 1, 1);
        pane.add(lbl_edit_link, 0 , 2);
        pane.add(txt_edit_link, 1, 2);
        pane.add(btn_edit_edit, 0, 3);


        editStage.setTitle("Add Bookmark");
        editStage.setScene(new Scene(pane, popupWidth, popupHeight));
        editStage.getIcons().add(icon);
        editStage.show();
    }

    private void notification(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        stage.show();
    }

    private void deleteNotify(int id, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure want to delete: " + title);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            handler.getBookmarks().remove(id);
            handler.shuffleList();
            handler.save();

            notification("Deleted " + title, Alert.AlertType.INFORMATION);

            viewLbl_s.clear();
            viewBtn_s.clear();

            setGrid(gridMain, handler.getBookmarks());
        }
    }

    private void addWindow() {
        Stage addStage = new Stage();

        lbl_add_title.setText("Title:");
        lbl_add_page.setText("Page:");
        lbl_add_link.setText("Link:");

        btn_add_add.setText("Add New Bookmark");

        btn_add_add.setOnAction(e -> {
            var res = handler.add(txt_add_title.getText(), txt_add_page.getText(), txt_add_link.getText());
            if (res) {
                notification("Added Bookmark for: " + txt_add_title.getText(), Alert.AlertType.INFORMATION);

                viewBtn_s.clear();
                viewLbl_s.clear();

                setGrid(gridMain, handler.getBookmarks());

                handler.save();
                addStage.close();
            } else {
                notification("Please use a link that is usable, this link is invalid: ' " + txt_add_link.getText() + " '!", Alert.AlertType.ERROR);
            }
        });

        GridPane pane = new GridPane();
        pane.add(lbl_add_title, 0, 0);
        pane.add(txt_add_title, 1, 0);
        pane.add(lbl_add_page, 0, 1);
        pane.add(txt_add_page, 1, 1);
        pane.add(lbl_add_link, 0 , 2);
        pane.add(txt_add_link, 1, 2);
        pane.add(btn_add_add, 0, 3);


        addStage.setTitle("Add Bookmark");
        addStage.setScene(new Scene(pane, popupWidth, popupHeight));
        addStage.getIcons().add(icon);
        addStage.show();
    }

    private List<Bookmark> searchForBookmark(String input) {
        List<Bookmark> results = new ArrayList<>();

        for (Bookmark bookmark : handler.getBookmarks()) {
            if (bookmark.getTitle().toLowerCase().contains(input.toLowerCase())) {
                results.add(bookmark);
            }
        }

        return results;
    }

    private void setGrid(GridPane grid, List<Bookmark> bookmarks) {
        grid.getChildren().clear();

        for (Bookmark bookmark : bookmarks) {

            Label lbl_view_num = new Label((bookmark.getNumeration() + 1) + "");
            Label lbl_view_page = new Label(bookmark.getTitle());
            Label lbl_view_title = new Label(bookmark.getPage());

            Button btn_view_remove = new Button("Delete");
            Button btn_view_edit = new Button("Edit");
            Button btn_view_link = new Button("Book Link");

            viewLbl_s.addAll(List.of(lbl_view_page, lbl_view_title, lbl_view_num));
            viewBtn_s.addAll(List.of(btn_view_remove, btn_view_edit, btn_view_link));

            int id = bookmark.getNumeration();
            btn_view_link.setOnAction(e -> getHostServices().showDocument(bookmark.getLink()));
            btn_view_edit.setOnAction(e -> editWindow(id));
            btn_view_remove.setOnAction(e -> deleteNotify(id, bookmark.getTitle()));

            grid.add(lbl_view_num, 0, bookmark.getNumeration());
            grid.add(lbl_view_title, 1, bookmark.getNumeration());
            grid.add(lbl_view_page, 2, bookmark.getNumeration());
            grid.add(btn_view_edit, 3, bookmark.getNumeration());
            grid.add(btn_view_remove, 4, bookmark.getNumeration());
            grid.add(btn_view_link, 5, bookmark.getNumeration());
        }

        updateFont();
    }

    private void updateFont() {
        for (Button btn : buttons) {
            btn.setFont(appFont);
        }

        for (Label lbl : labels) {
            lbl.setFont(appFont);
        }

        for (TextField txt : txtFields) {
            txt.setFont(appFont);
        }

        for (Label lbl : viewLbl_s) {
            lbl.setFont(appFont);
        }

        for (Button btn : viewBtn_s) {
            btn.setFont(appFont);
        }
    }

    private void initNodes() {
        buttons.addAll(List.of(
                btn_add = new Button(),

                btn_edit_edit = new Button(),

                btn_add_add = new Button()
        ));

        labels.addAll(List.of(
                lbl_search = new Label(),

                lbl_edit_title = new Label(),
                lbl_edit_page = new Label(),
                lbl_edit_link = new Label(),

                lbl_add_title = new Label(),
                lbl_add_page = new Label(),
                lbl_add_link = new Label()
        ));

        txtFields.addAll(List.of(
                txt_search = new TextField(),

                txt_edit_title = new TextField(),
                txt_edit_page = new TextField(),
                txt_edit_link = new TextField(),

                txt_add_title = new TextField(),
                txt_add_page = new TextField(),
                txt_add_link = new TextField()
        ));

        updateFont();
    }
}
