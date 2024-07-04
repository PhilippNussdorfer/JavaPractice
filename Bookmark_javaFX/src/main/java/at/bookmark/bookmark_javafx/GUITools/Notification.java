package at.bookmark.bookmark_javafx.GUITools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.List;
import java.util.Optional;

public class Notification {
    public final Image icon;
    private double x;
    private double y;

    public double getX() {
        List<Window> open = Stage.getWindows().stream().filter(Window::isShowing).toList();
        x = open.get(0).getX();
        return x;
    }

    public double getY() {
        List<Window> open = Stage.getWindows().stream().filter(Window::isShowing).toList();
        y = open.get(0).getY();
        return y;
    }

    public Notification(Image icon) {
        this.icon = icon;
    }

    private void setAlertPosition(Alert alert) {
        alert.setX(x + 200);
        alert.setY(y + 200);
    }

    public void notify(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        setAlertPosition(alert);
        alert.setContentText(msg);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon);
        stage.show();
    }

    public void deleteNotify(int id, String title, GridPane grid, GridBuilder gridBuilder, DependencyBundle dB) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        setAlertPosition(alert);
        alert.setContentText("Are you sure want to delete: " + title);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dB.handler.getBookmarks().remove(id);
            dB.handler.collapseBookmarks();
            dB.handler.saveInFile();

            notify("Deleted " + title, Alert.AlertType.INFORMATION);

            dB.viewNodes.clear();

            gridBuilder.setGrid(grid, dB.handler.getBookmarks(), this, dB);
        }
    }
}
