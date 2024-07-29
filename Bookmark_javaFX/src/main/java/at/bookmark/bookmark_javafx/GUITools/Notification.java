package at.bookmark.bookmark_javafx.GUITools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class Notification {
    @Getter
    private final Image icon;
    private double x;
    private double y;

    public Notification(Image icon) {
        this.icon = icon;
    }

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

    public void deleteNotify(int id, String title, GridBuilder gridBuilder, DependencyBundle dB) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        setAlertPosition(alert);
        alert.setContentText("Are you sure want to delete: " + title);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteOrganiseAndReload(id, title, gridBuilder, dB);
        }
    }

    private void deleteOrganiseAndReload(int id, String title, GridBuilder gridBuilder, DependencyBundle dB) {
        dB.getHandler().getBookmarks().remove(id);
        dB.getHandler().collapseBookmarks();
        dB.getHandler().saveInFile();

        notify("Deleted " + title, Alert.AlertType.INFORMATION);
        dB.getViewNodes().clear();

        gridBuilder.setGrid(dB.getGridMain(), dB.getHandler().getBookmarks(), this, dB);
        dB.getSearch().updateSearch(dB.getGridSearch(), gridBuilder, this, dB);
    }
}
