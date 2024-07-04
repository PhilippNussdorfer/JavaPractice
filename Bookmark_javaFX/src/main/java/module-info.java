module at.bookmark.bookmark_javafx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires lombok;

    opens at.bookmark.bookmark_javafx to javafx.fxml;
    exports at.bookmark.bookmark_javafx;
    exports window;
    opens window to javafx.fxml;
    exports at.bookmark.bookmark_javafx.save_and_load;
    opens at.bookmark.bookmark_javafx.save_and_load to javafx.fxml;
    exports at.bookmark.bookmark_javafx.bookmark;
    opens at.bookmark.bookmark_javafx.bookmark to javafx.fxml;
}