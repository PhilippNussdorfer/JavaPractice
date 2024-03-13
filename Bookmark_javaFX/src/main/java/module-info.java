module at.bookmark.bookmark_javafx {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.fxml;
    requires lombok;


    //opens at.bookmark.bookmark_javafx to Javafx.graphics;
    //exports at.bookmark.bookmark_javafx;
    opens at.bookmark.bookmark_javafx to javafx.fxml;
    exports at.bookmark.bookmark_javafx;
}