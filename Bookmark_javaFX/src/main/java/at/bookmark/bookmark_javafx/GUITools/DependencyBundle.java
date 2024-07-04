package at.bookmark.bookmark_javafx.GUITools;

import at.bookmark.bookmark_javafx.BookmarkHandler;
import at.bookmark.bookmark_javafx.WindowCalc;
import at.bookmark.bookmark_javafx.WriterReader;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class DependencyBundle {
    public final WindowCalc windowCalc = new WindowCalc();
    public final List<Node> startNodes = new ArrayList<>();
    public final List<Node> editNodes = new ArrayList<>();
    public final List<Node> addNodes = new ArrayList<>();
    public final List<Node> viewNodes = new ArrayList<>();
    public final FontUpdater fontUpdater = new FontUpdater();
    public final WriterReader writerReader = new WriterReader();
    public final BookmarkHandler handler = new BookmarkHandler();
}
