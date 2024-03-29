package at.bookmark.bookmark_javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Bookmark_App extends Application {

    private final BookmarkHandler handler = new BookmarkHandler();
    private final GridPane gridSearch = new GridPane();
    private final GridPane gridMain = new GridPane();

    private final int popupWidth = 360;
    private final int popupHeight = 120;
    private final Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/logo/Bookmark.png")));

    public void launch_app() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        int width = 1280;
        int height = 640;

        gridMain.setHgap(10);
        gridMain.setVgap(10);

        Label lblSearch = new Label("Search:");
        TextField txtSearch = new TextField();
        Button btnAdd = new Button("Add");

        txtSearch.setPrefWidth(width - width / 3);
        txtSearch.textProperty().addListener((observer, oldValue, newValue) -> {

            if (newValue.equals("")) {
                gridSearch.getChildren().clear();
            } else {
                var results = searchForBookmark(newValue);
                setGrid(gridSearch, results);
            }
        });

        btnAdd.setOnAction(e -> addWindow());


        FlowPane flow = new FlowPane(lblSearch, txtSearch, btnAdd, gridSearch, gridMain);
        flow.setHgap(10);
        flow.setVgap(10);

        ScrollPane scrollPane = new ScrollPane(flow);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, width, height);
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

        TextField txtTitle = new TextField(bookmark.getTitle());
        TextField txtPage = new TextField(bookmark.getPage());
        TextField txtLink = new TextField(bookmark.getLink());

        Label lblTitle = new Label("Title:");
        Label lblPage = new Label("Page:");
        Label lblLink = new Label("Link:");

        Button btnEdit = new Button("Save Changes");

        btnEdit.setOnAction(e -> {
            var res = handler.edit(id, txtTitle.getText(), txtPage.getText(), txtLink.getText());
            if (res) {
                notification("Edited Bookmark for: " + txtTitle.getText(), Alert.AlertType.INFORMATION);
                setGrid(gridMain, handler.getBookmarks());
                handler.save();
                editStage.close();
            } else {
                notification("Please use a link that is usable, this link is invalid: ' " + txtLink.getText() + " '!", Alert.AlertType.ERROR);
            }
        });

        GridPane pane = new GridPane();
        pane.add(lblTitle, 0, 0);
        pane.add(txtTitle, 1, 0);
        pane.add(lblPage, 0, 1);
        pane.add(txtPage, 1, 1);
        pane.add(lblLink, 0 , 2);
        pane.add(txtLink, 1, 2);
        pane.add(btnEdit, 0, 3);


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
            handler.save();
            notification("Deleted " + title, Alert.AlertType.INFORMATION);
            setGrid(gridMain, handler.getBookmarks());
        }
    }

    private void addWindow() {
        Stage addStage = new Stage();

        TextField txtTitle = new TextField();
        TextField txtPage = new TextField();
        TextField txtLink = new TextField();

        Label lblTitle = new Label("Title:");
        Label lblPage = new Label("Page:");
        Label lblLink = new Label("Link:");

        Button btnAdd = new Button("Add New Bookmark");

        btnAdd.setOnAction(e -> {
            var res = handler.add(txtTitle.getText(), txtPage.getText(), txtLink.getText());
            if (res) {
                notification("Added Bookmark for: " + txtTitle.getText(), Alert.AlertType.INFORMATION);
                setGrid(gridMain, handler.getBookmarks());
                handler.save();
                addStage.close();
            } else {
                notification("Please use a link that is usable, this link is invalid: ' " + txtLink.getText() + " '!", Alert.AlertType.ERROR);
            }
        });

        GridPane pane = new GridPane();
        pane.add(lblTitle, 0, 0);
        pane.add(txtTitle, 1, 0);
        pane.add(lblPage, 0, 1);
        pane.add(txtPage, 1, 1);
        pane.add(lblLink, 0 , 2);
        pane.add(txtLink, 1, 2);
        pane.add(btnAdd, 0, 3);


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
        int row = 0;

        for (Bookmark bookmark : bookmarks) {

            Label lblTitle = new Label(bookmark.getTitle());
            Label lblPage = new Label(bookmark.getPage());
            Button btnLink = new Button("Book Link");
            Button btnRemove = new Button("Delete");
            Button btnEdit = new Button("Edit");

            int id = row;
            btnLink.setOnAction(e -> getHostServices().showDocument(bookmark.getLink()));
            btnEdit.setOnAction(e -> editWindow(id));
            btnRemove.setOnAction(e -> deleteNotify(id, bookmark.getTitle()));

            grid.add(lblTitle, 0, row);
            grid.add(lblPage, 1, row);
            grid.add(btnEdit, 2, row);
            grid.add(btnRemove, 3, row);
            grid.add(btnLink, 4, row);
            row++;
        }
    }
}
