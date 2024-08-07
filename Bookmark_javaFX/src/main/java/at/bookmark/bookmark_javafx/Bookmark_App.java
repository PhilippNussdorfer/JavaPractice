package at.bookmark.bookmark_javafx;

import at.bookmark.bookmark_javafx.Exceptions.IsAlreadySetException;
import at.bookmark.bookmark_javafx.GUITools.*;
import at.bookmark.bookmark_javafx.tools.OutputWrapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;

public class Bookmark_App extends Application {
    private final String fileSeparator = FileSystems.getDefault().getSeparator();
    private final File file = new File("C:"+ fileSeparator +"Java Programs"+ fileSeparator +"Bookmark"+ fileSeparator +"Bookmarks.txt");
    private final File dir = new File("C:"+ fileSeparator +"Java Programs"+ fileSeparator +"Bookmark");
    private final GridPane gridSearch = new GridPane();
    private final GridPane gridMain = new GridPane();
    private double width = 1280;
    private double height = 640;
    private final Image icon = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/logo/Bookmark.png")));
    private final Notification notification = new Notification(icon);
    private final GridBuilder gridBuilder = new GridBuilder(getHostServices());
    private final AddWindowBuilder addWindowBuilder = new AddWindowBuilder();
    private DependencyBundle dependencyBundle;

    public void launch_app() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        initDependencyBundle();
        loadAndSetupForWindow(stage);

        Label lbl_search = new Label("Search:");
        Button btn_add = new Button("Add Bookmark");

        TextField txt_search = searchFieldSetup();
        if (txt_search == null)
            notification.notify("txt_search element is null!", Alert.AlertType.ERROR);

        btn_add.setOnAction(e -> addWindowBuilder.addWindow(notification, gridBuilder, dependencyBundle));
        setStage(stage, lbl_search, btn_add, txt_search);

        gridBuilder.setGrid(gridMain, dependencyBundle.getHandler().getBookmarks(), notification, dependencyBundle);
        dependencyBundle.getWindowCalc().saveOnCloseAction(stage, dependencyBundle);
    }

    private void initDependencyBundle() {
        try {
            this.dependencyBundle = new DependencyBundle(file, dir, new BufferedReader(new FileReader(file)));
        } catch (IOException exception) {
            new OutputWrapper().printOutLine(exception.getMessage());
        }
    }

    private TextField searchFieldSetup() {
        TextField txt_search = null;

        try {
            gridSetup();

            txt_search = dependencyBundle.getSearch().searchLogic(dependencyBundle, notification, gridBuilder);
            dependencyBundle.setSearchField(txt_search);

        } catch (IsAlreadySetException exception) {
            notification.notify(exception.getMessage(), Alert.AlertType.ERROR);
        }
        return txt_search;
    }

    private void gridSetup() throws IsAlreadySetException {
        gridMain.setHgap(10);
        gridMain.setVgap(10);
        gridMain.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), new Insets(-10, -10, -10, -10))));
        dependencyBundle.setGridMain(gridMain);

        gridSearch.setHgap(10);
        gridSearch.setVgap(10);
        gridSearch.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), new Insets(-10, -10, -10, -10))));
        dependencyBundle.setGridSearch(gridSearch);
    }

    private void loadAndSetupForWindow(Stage stage) {
        dependencyBundle.getWindowCalc().loadAndSetupWindowPosition(stage, dependencyBundle);

        width = dependencyBundle.getWindowCalc().getWidth();
        height = dependencyBundle.getWindowCalc().getHeight();
    }

    private void setStage(Stage stage, Label lbl_search, Button btn_add, TextField txt_search) {
        dependencyBundle.getStartNodes().addAll(List.of(txt_search, btn_add, lbl_search));

        ScrollPane scrollPane = getScrollPane(lbl_search, btn_add, txt_search);

        MenuBar menu = createAndFillMenuBar(32, stage);
        VBox vbox = new VBox(menu, scrollPane);

        Scene scene = new Scene(vbox, width - dependencyBundle.getWindowCalc().getAdjustScreen(), height);
        scene.setFill(Color.LIGHTGRAY);

        stage.setTitle("Bookmark");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();
    }

    private ScrollPane getScrollPane(Label lbl_search, Button btn_add, TextField txt_search) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(50);

        grid.add(lbl_search, 0, 0);
        grid.add(txt_search, 1, 0);
        grid.add(btn_add, 2, 0);
        grid.add(gridSearch, 1, 1);
        grid.add(gridMain, 1, 2);

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    private MenuBar createAndFillMenuBar(int fontSize, Stage stage) {
        MenuBar menu = new MenuBar();
        Menu fontSizeMenu = new Menu("Font Size");

        Menu fullscreen = createFullscreenMenu(stage);

        for (int i = 8; i < fontSize + 2; i += 2) {
            MenuItem size = new MenuItem(i + "");

            menuItemSetup(size, i);
            fontSizeMenu.getItems().add(size);
        }

        menu.getMenus().add(fontSizeMenu);
        menu.getMenus().add(fullscreen);
        return menu;
    }

    private void menuItemSetup(MenuItem size, int tmp) {
        size.setOnAction(t -> {
            dependencyBundle.getFontUpdater().setAppFont(tmp);
            dependencyBundle.getFontUpdater().updateFont(
                    dependencyBundle.getViewNodes(), dependencyBundle.getEditNodes(),
                    dependencyBundle.getEditNodes(), dependencyBundle.getAddNodes()
            );
        });
    }

    private Menu createFullscreenMenu(Stage stage) {
        Menu fullscreen = new Menu("Fullscreen");
        MenuItem fullscreenItem = new MenuItem("activate");

        fullscreen.getItems().add(fullscreenItem);

        fullscreenItem.setOnAction(t -> stage.setFullScreen(true));
        return fullscreen;
    }
}