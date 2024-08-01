package at.bookmark.bookmark_javafx.GUITools;

import at.bookmark.bookmark_javafx.Exceptions.IsAlreadySetException;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DependencyBundleTest {

    DependencyBundle dependencyBundle;

    @Mock
    File file;
    @Mock
    BufferedReader reader;
    @Mock
    GridPane gridPane;

    @BeforeEach
    void setUp() {
        dependencyBundle = new DependencyBundle(file, file, reader);
    }

    @Test
    void setMainAndSearchGrid() throws IsAlreadySetException {
        dependencyBundle.setGridMain(gridPane);
        dependencyBundle.setGridSearch(gridPane);

        assertEquals(gridPane, dependencyBundle.getGridMain());
        assertEquals(gridPane, dependencyBundle.getGridSearch());
    }

    @Test
    void setMainGridAndSearchThrowsException() throws IsAlreadySetException {
        dependencyBundle.setGridMain(gridPane);
        dependencyBundle.setGridSearch(gridPane);

        var exception = assertThrows(IsAlreadySetException.class, ()-> dependencyBundle.setGridMain(gridPane));
        assertEquals("The gridMain object can only be set once!" ,exception.getMessage());

        exception = assertThrows(IsAlreadySetException.class, ()-> dependencyBundle.setGridSearch(gridPane));
        assertEquals("The gridSearch object can only be set once!" ,exception.getMessage());
    }
}