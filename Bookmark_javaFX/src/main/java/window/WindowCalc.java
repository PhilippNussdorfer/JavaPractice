package window;

import at.bookmark.bookmark_javafx.GUITools.DependencyBundle;
import at.bookmark.bookmark_javafx.GUITools.FontUpdater;
import at.bookmark.bookmark_javafx.save_and_load.WriterReader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Properties;

public class WindowCalc {

    private final String config = "config.prop";
    private double width;
    private double height;
    private double x;
    private double y;
    private boolean isFullscreen;
    private int screenIndex;
    private final int adjustScreen = 5;

    private double getCheckedHeight(Screen screen, double height) {
        int taskbarHeight = 60;

        if (height > screen.getBounds().getHeight() - taskbarHeight)
            height = screen.getBounds().getHeight() - taskbarHeight;
        return height;
    }

    private double getWidth(Stage stage, double width) {
        if (stage.getWidth() <= width) {
            width = stage.getWidth();

            if (width - this.width <= 40 && width - this.width >= 0) {
                width = this.width;
            }
        }
        return width;
    }

    private double getHeight(Stage stage, double height) {
        if (stage.getHeight() <= height) {
            height = stage.getHeight();

            if (height - this.height <= 40 && height - this.height >= 0) {
                height = this.height;
            }
        }
        return height;
    }

    public void loadAndSetupWindowPosition(Stage stage, DependencyBundle dB) {
        loadConfigProperties(dB.writerReader, dB.fontUpdater);

        if (!isFullscreen) {
            adjustWindowOnMonitor(getScreen());
        } else {
            stage.setFullScreen(true);
        }
        setStagePosition(stage, x - adjustScreen, y);
    }

    public static void setStagePosition(Stage stage, double windowXPos, double windowYPos) {
        stage.setX(windowXPos);
        stage.setY(windowYPos);
    }

    private Screen getScreen() {
        Screen screen;

        if (screenIndex >= 0 && Screen.getScreens().size() > 1 && screenIndex < Screen.getScreens().size()) {
            screen = Screen.getScreens().get(screenIndex);
        } else {
            screen = Screen.getPrimary();
        }

        return screen;
    }

    private Screen getCurrentScreenFromStage(Stage stage) {
        int screenIndex = getScreenIndex(stage.getX(), stage.getY(), (int) stage.getWidth(), (int) stage.getHeight());
        return Screen.getScreens().get(screenIndex);
    }

    private void adjustWindowOnMonitor(Screen screen) {
        Rectangle2D screenBounds;
        screenBounds = screen.getVisualBounds();

        if (width > screenBounds.getWidth())
            width = screenBounds.getWidth();
        if (height > screenBounds.getHeight())
            height = screenBounds.getHeight();

        adjustXPos(screenBounds);
        adjustYPos(screenBounds);
    }

    private void adjustYPos(Rectangle2D screenBounds) {
        if (y != 0) {
            if (y < screenBounds.getMinY()) {
                y = screenBounds.getMinY();
            }
            if (y + height > screenBounds.getMaxY()) {
                y = screenBounds.getMaxY() - height;
            }
        }
    }

    private void adjustXPos(Rectangle2D screenBounds) {
        if (x != 0) {
            if (x < screenBounds.getMinX()) {
                x = screenBounds.getMinX();
            }
            if (x + width > screenBounds.getMaxX()) {
                x = screenBounds.getMaxX() - width;
            }
        }
    }

    private void loadConfigProperties(WriterReader writerReader, FontUpdater fontUpdater) {
        Properties prop = writerReader.loadConfig(config);
        if (prop != null) {
            try {
                x = Double.parseDouble(prop.getProperty("x"));
                y = Double.parseDouble(prop.getProperty("y"));
                width = Integer.parseInt(prop.getProperty("width"));
                height = Integer.parseInt(prop.getProperty("height"));
                screenIndex = Integer.parseInt(prop.getProperty("screenIndex"));
                isFullscreen = Boolean.parseBoolean(prop.getProperty("isFullscreen"));
                fontUpdater.setAppFont(Double.parseDouble(prop.getProperty("fontSize")));

            } catch (NumberFormatException e) {
                System.out.println("One or multiple numbers for the monitor position are missing");
            }
        }
    }

    private int getScreenIndex(double x, double y, int width, int height) {
        int maxIntersection = -18, screenIndex = 0;
        var list = Screen.getScreensForRectangle(x, y, width, height);

        if (list.size() == 1) {
            for (Screen screen : Screen.getScreens()) {
                if (screen == list.get(0)) {
                    return screenIndex;
                } else {
                    screenIndex ++;
                }
            }
        }

        Rectangle2D windowBounds = new Rectangle2D(x, y, width, height);
        screenIndex = 0;

        for (Screen screen : Screen.getScreens()) {
            Rectangle2D screenBounds = screen.getBounds();

            double widthInter = screenBounds.getWidth() - windowBounds.getWidth();
            double heightInter = screenBounds.getHeight() - windowBounds.getHeight();

            if (widthInter > maxIntersection && heightInter > maxIntersection) {
                return screenIndex;
            } else {
                screenIndex ++;
            }
        }

        return screenIndex;
    }

    public void saveOnCloseAction(Stage stage, DependencyBundle dB) {
        stage.setOnCloseRequest(event -> {
            var screen = getCurrentScreenFromStage(stage);

            double width = screen.getBounds().getWidth();
            double height = screen.getBounds().getHeight();

            height = getHeight(stage, height);
            height = getCheckedHeight(screen, height);
            width = getWidth(stage, width);

            dB.writerReader.saveConfig(stage.getX(), stage.getY(), (int) width, (int) height,
                    getScreenIndex(stage.getX(), stage.getY(), (int) width, (int) height), stage.isFullScreen(), dB.fontUpdater.getAppFont().getSize(), config);
        });
    }

    public double getAdjustScreen() {
        return this.adjustScreen;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }
}
