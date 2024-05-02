package at.bookmark.bookmark_javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.util.*;

public class Bookmark_App extends Application {

    private final String config = "config.prop";
    private final WriterReader writerReader = new WriterReader();
    private final BookmarkHandler handler = new BookmarkHandler();
    private final GridPane gridSearch = new GridPane();
    private final GridPane gridMain = new GridPane();
    private Font appFont = new Font(16);
    private final int popupWidth = 480;
    private final int popupHeight = 240;
    private double x = 0;
    private double y = 0;
    double width = 1280;
    double height = 640;
    private final Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/logo/Bookmark.png")));
    private int screenIndex = 0;
    private boolean isFullscreen = false;

    private final List<Node> startNodes = new ArrayList<>();
    private final List<Node> editNodes = new ArrayList<>();
    private final List<Node> addNodes = new ArrayList<>();
    private final List<Node> viewNodes = new ArrayList<>();

    public void launch_app() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        loadAndSetupWindowPosition(stage);

        gridMain.setHgap(10);
        gridMain.setVgap(10);
        gridMain.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));

        gridSearch.setHgap(10);
        gridSearch.setVgap(10);

        Label lbl_search = new Label("Search:");
        Button btn_add = new Button("Add Bookmark");

        TextField txt_search = new TextField();
        txt_search.setPrefWidth(860);
        txt_search.textProperty().addListener((observer, oldValue, newValue) -> {

            if (newValue.equals("")) {
                gridSearch.getChildren().clear();
            } else {
                var results = searchForBookmark(newValue);

                viewNodes.clear();

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

        startNodes.addAll(List.of(txt_search, btn_add, lbl_search));

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToHeight(true);

        MenuBar menu = createAndFillMenuBar(32, stage);
        VBox vbox = new VBox(menu, scrollPane);

        Scene scene = new Scene(vbox, width, height);
        scene.setFill(Color.LIGHTGRAY);

        stage.setTitle("Bookmark");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();

        setGrid(gridMain, handler.getBookmarks());

        stage.setOnCloseRequest(event -> writerReader.saveConfig(stage.getX(), stage.getY(), (int) stage.getWidth(), (int) stage.getHeight(),
                getScreenIndex(stage.getX(), stage.getY(), (int) stage.getWidth(), (int) stage.getHeight()), stage.isFullScreen(), config));
    }

    private void loadAndSetupWindowPosition(Stage stage) {
        Screen screen;
        Rectangle2D screenBounds;

        loadConfigProperties();

        if (!isFullscreen) {
            if (x != 0 || y != 0) {
                if (screenIndex >= 0 && Screen.getScreens().size() > 1 && screenIndex < Screen.getScreens().size()) {
                    screen = Screen.getScreens().get(screenIndex);
                } else {
                    screen = Screen.getPrimary();
                }

                screenBounds = screen.getVisualBounds();
                if (width > screenBounds.getWidth())
                    width = screenBounds.getWidth();
                if (height > screenBounds.getHeight())
                    height = screenBounds.getHeight();

                if (x < screenBounds.getMinX()) {
                    x = screenBounds.getMinX();
                }
                else if (x + width > screenBounds.getMaxX()) {
                    x = screenBounds.getMaxX() - width;
                }

                if (y < screenBounds.getMinY()) {
                    y = screenBounds.getMinY();
                }
                else if (y + height > screenBounds.getMaxY()) {
                    y = screenBounds.getMaxY() - height;
                }
            }
        } else {
            stage.setFullScreen(true);
        }
        setStagePosition(stage, x, y);
    }

    private void loadConfigProperties() {
        Properties prop = writerReader.loadConfig(config);
        if (prop != null) {
            try {
                x = Double.parseDouble(prop.getProperty("x"));
                y = Double.parseDouble(prop.getProperty("y"));
                width = Integer.parseInt(prop.getProperty("width"));
                height = Integer.parseInt(prop.getProperty("height"));
                screenIndex = Integer.parseInt(prop.getProperty("screenIndex"));
                isFullscreen = Boolean.parseBoolean(prop.getProperty("isFullscreen"));

            } catch (NumberFormatException e) {
                System.out.println("One or multiple numbers for the monitor position are missing");
            }
        }
    }

    private int getScreenIndex(double x, double y, int width, int height) {
        Rectangle2D windowBounds = new Rectangle2D(x, y, width, height);

        int maxIntersection = -18, screenIndex = 0;

        for (Screen screen : Screen.getScreens()) {
            Rectangle2D screenBounds = screen.getBounds();

            double widthInter = screenBounds.getWidth() - windowBounds.getWidth();
            double heightInter = screenBounds.getHeight() - windowBounds.getHeight();
            System.out.println(widthInter);
            System.out.println(heightInter);

            if (widthInter > maxIntersection && heightInter > maxIntersection) {
                return screenIndex;
            } else {
                screenIndex ++;
            }
        }

        return screenIndex;
    }

    private MenuBar createAndFillMenuBar(int fontsize, Stage stage) {
        MenuBar menu = new MenuBar();
        Menu fontSize = new Menu("Font Size");

        Menu fullscreen = createFullscreenMenu(stage);

        for (int i = 8; i < fontsize + 2; i += 2) {
            MenuItem size = new MenuItem(i + "");
            int tmp = i;

            size.setOnAction(t -> {
                appFont = new Font(tmp);
                updateFont(startNodes, addNodes, editNodes, viewNodes);
            });

            fontSize.getItems().add(size);
        }

        menu.getMenus().add(fontSize);
        menu.getMenus().add(fullscreen);
        return menu;
    }

    private Menu createFullscreenMenu(Stage stage) {
        Menu fullscreen = new Menu("Fullscreen");
        MenuItem fullscreenItem = new MenuItem("activate");

        fullscreen.getItems().add(fullscreenItem);

        fullscreenItem.setOnAction(t -> stage.setFullScreen(true));
        return fullscreen;
    }

    private void editWindow(int id) {
        editNodes.clear();

        Stage editStage = new Stage();
        setStagePosition(editStage, x + 200, y + 200);
        Bookmark bookmark = handler.getBookmarks().get(id);

        TextField txt_edit_title = new TextField(bookmark.getTitle());
        TextField txt_edit_page = new TextField(bookmark.getPage());
        TextField txt_edit_link = new TextField(bookmark.getLink());

        Label lbl_edit_title = new Label("Title:");
        Label lbl_edit_page = new Label("Page:");
        Label lbl_edit_link = new Label("Link:");

        Button btn_edit = new Button("Save Changes");

        btn_edit.setOnAction(e -> {
            var res = handler.editBookmark(id, txt_edit_title.getText(), txt_edit_page.getText(), txt_edit_link.getText());
            if (res) {
                notification("Edited Bookmark for: " + txt_edit_title.getText(), Alert.AlertType.INFORMATION);

                viewNodes.clear();

                setGrid(gridMain, handler.getBookmarks());
                handler.saveInFile();
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
        pane.add(btn_edit, 0, 3);

        editNodes.addAll(List.of(btn_edit, lbl_edit_page, lbl_edit_link, lbl_edit_title, txt_edit_page, txt_edit_link, txt_edit_title));

        editStage.setTitle("Add Bookmark");
        editStage.setScene(new Scene(pane, popupWidth, popupHeight));
        editStage.getIcons().add(icon);
        editStage.show();

        updateFont(editNodes);
    }

    private void notification(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        setAlertPosition(alert);
        alert.setContentText(msg);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        stage.show();
    }

    private void deleteNotify(int id, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        setAlertPosition(alert);
        alert.setContentText("Are you sure want to delete: " + title);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            handler.getBookmarks().remove(id);
            handler.collapseBookmarks();
            handler.saveInFile();

            notification("Deleted " + title, Alert.AlertType.INFORMATION);

            viewNodes.clear();

            setGrid(gridMain, handler.getBookmarks());
        }
    }

    private void setAlertPosition(Alert alert) {
        alert.setX(x + 200);
        alert.setY(y + 200);
    }

    private void addWindow() {
        addNodes.clear();

        Stage addStage = new Stage();
        setStagePosition(addStage, x + 200, y + 200);

        TextField txt_add_title = new TextField();
        TextField txt_add_page = new TextField();
        TextField txt_add_link = new TextField();

        Label lbl_add_title = new Label("Title:");
        Label lbl_add_page = new Label("Page:");
        Label lbl_add_link = new Label("Link:");

        Button btn_add = new Button("Add New Bookmark");

        btn_add.setOnAction(e -> {
            var res = handler.addNewBookmark(txt_add_title.getText(), txt_add_page.getText(), txt_add_link.getText());
            if (res) {
                notification("Added Bookmark for: " + txt_add_title.getText(), Alert.AlertType.INFORMATION);

                viewNodes.clear();

                setGrid(gridMain, handler.getBookmarks());

                handler.saveInFile();
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
        pane.add(btn_add, 0, 3);

        addNodes.addAll(List.of(btn_add, lbl_add_page, lbl_add_link, lbl_add_title, txt_add_title, txt_add_page, txt_add_link));

        addStage.setTitle("Add Bookmark");
        addStage.setScene(new Scene(pane, popupWidth, popupHeight));
        addStage.getIcons().add(icon);
        addStage.show();

        updateFont(addNodes);
    }

    private void setStagePosition(Stage addStage, double windowXPos, double windowYPos) {
        addStage.setX(windowXPos);
        addStage.setY(windowYPos);
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

            viewNodes.addAll(List.of(lbl_view_page, lbl_view_title, lbl_view_num, btn_view_remove, btn_view_edit, btn_view_link));

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

        updateFont(viewNodes);
    }

    @SafeVarargs
    private void updateFont(List<Node> ... nodes) {
        for (List<Node> nodeList : nodes) {
            for (Node node : nodeList) {
                updateNodeFont(node);
            }
        }
    }

    private void updateNodeFont(Node node) {
        if (node instanceof Button) {
            ((Button) node).setFont(appFont);
        }
        if (node instanceof TextArea) {
            ((TextArea) node).setFont(appFont);
        }
        if (node instanceof Label) {
            ((Label) node).setFont(appFont);
        }
    }
}