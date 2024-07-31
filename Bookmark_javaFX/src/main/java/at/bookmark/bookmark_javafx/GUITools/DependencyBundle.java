package at.bookmark.bookmark_javafx.GUITools;

import at.bookmark.bookmark_javafx.Exceptions.IsAlreadySetException;
import at.bookmark.bookmark_javafx.bookmark.BookmarkHandler;
import at.bookmark.bookmark_javafx.tools.LogicCore;
import at.bookmark.bookmark_javafx.tools.OutputWrapper;
import at.bookmark.bookmark_javafx.tools.Search;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import window.WindowCalc;
import at.bookmark.bookmark_javafx.save_and_load.WriterReader;
import javafx.scene.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Search and the two GridPane's need to be set as early as possible so to avoid anomaly's
 */
@Getter
public class DependencyBundle {
    private final WindowCalc windowCalc = new WindowCalc();
    private final List<Node> startNodes = new ArrayList<>();
    private final List<Node> editNodes = new ArrayList<>();
    private final List<Node> addNodes = new ArrayList<>();
    private final List<Node> viewNodes = new ArrayList<>();
    private final FontUpdater fontUpdater = new FontUpdater();
    private final WriterReader writerReader;
    private final BookmarkHandler handler;
    private final Search search = new Search();
    private final LogicCore logic = new LogicCore();
    private TextField searchField = null;
    private GridPane gridMain = null;
    private GridPane gridSearch = null;

    public DependencyBundle(String dir, String pathToFile) {
        BookmarkHandler handler = null;

        var tmp = new WriterReader(new File(pathToFile), new File(dir), new OutputWrapper());
        this.writerReader = tmp;

        try {
            handler = new BookmarkHandler(tmp, new BufferedReader(new FileReader(writerReader.getFile())));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        this.handler = handler;
    }

    public String getSearchFieldInput() {
        return this.searchField.getText();
    }

    public void setSearchField(TextField field) throws IsAlreadySetException {
        if (this.searchField == null)
            this.searchField = field;

        else
            throw new IsAlreadySetException("The SearchField object can only be set once!");
    }

    public void setGridMain(GridPane grid) throws IsAlreadySetException {
        if (this.gridMain == null)
            this.gridMain = grid;

        else
            throw new IsAlreadySetException("The gridMain object can only be set once!");
    }

    public void setGridSearch(GridPane grid) throws IsAlreadySetException {
        if (this.gridSearch == null)
            this.gridSearch = grid;

        else
            throw new IsAlreadySetException("The gridSearch object can only be set once!");
    }

    public BufferedWriter assemblyBufferedWriter() {
        try {
            return new BufferedWriter(new FileWriter(writerReader.getFile()));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }
}
