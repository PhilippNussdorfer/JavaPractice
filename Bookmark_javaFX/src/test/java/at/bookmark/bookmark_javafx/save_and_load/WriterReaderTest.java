package at.bookmark.bookmark_javafx.save_and_load;

import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import at.bookmark.bookmark_javafx.tools.OutputWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WriterReaderTest {
    private WriterReader writerReader;

    @Mock
    File file;
    @Mock
    BufferedWriter writer;
    @Mock
    BufferedReader reader;
    @Mock
    List<Bookmark> bookmarks;
    @Mock
    Iterator<Bookmark> iterator;
    @Mock
    Bookmark bookmark;
    @Mock
    OutputWrapper wrapper;
    @Mock
    IOException exception;
    @Mock
    InputStream inputStream;
    @Mock
    OutputStream outputStream;
    @Mock
    Properties properties;

    @BeforeEach
    void setUp() {
        writerReader = new WriterReader(file, file, wrapper);
    }

    @Test
    void writeFileWithoutErrorsAndDirDoseExist() throws IOException {
        Mockito.when(file.mkdirs()).thenReturn(true);
        Mockito.when(file.createNewFile()).thenReturn(true);
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(bookmarks.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true, false);
        Mockito.when(iterator.next()).thenReturn(bookmark);

        writerReader.writeFile(bookmarks, writer);

        Mockito.verify(file, Mockito.times(1)).mkdirs();
        Mockito.verify(file, Mockito.times(1)).createNewFile();
        Mockito.verify(file, Mockito.times(1)).getName();
        Mockito.verify(file, Mockito.times(1)).exists();
        Mockito.verify(writer, Mockito.times(1)).write("0|null|null|null\n");
        Mockito.verify(writer, Mockito.times(1)).close();
    }

    @Test
    void writeFileThrowsIOException() throws IOException {
        Mockito.when(file.mkdirs()).thenReturn(false);
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.doThrow(exception).when(writer).write(Mockito.anyString());
        Mockito.when(bookmarks.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true, false);
        Mockito.when(iterator.next()).thenReturn(bookmark);
        Mockito.when(exception.getMessage()).thenReturn("IOException");

        writerReader.writeFile(bookmarks, writer);

        Mockito.verify(file, Mockito.times(1)).mkdirs();
        Mockito.verify(file, Mockito.times(1)).exists();
        Mockito.verify(wrapper, Mockito.times(1)).printOutLine("An error occurred: IOException");
    }

    @Test
    void loadFile() throws IOException {
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(reader.readLine()).thenReturn("0|Gmx|2|https://www.gmx.at/", "a|Gmx|2|https://www.gmx.at/", null);

        var result = writerReader.loadFile(reader);

        Mockito.verify(file, Mockito.times(1)).exists();
        Mockito.verify(reader, Mockito.times(3)).readLine();
        Mockito.verify(reader, Mockito.times(1)).close();

        assertEquals(2, result.size());
        assertEquals(0, result.get(0).getNum());
        assertEquals(1, result.get(1).getNum());
    }

    @Test
    void loadFileHasIOException() throws IOException {
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.doThrow(exception).when(reader).readLine();
        Mockito.when(exception.getMessage()).thenReturn("IOException");

        writerReader.loadFile(reader);

        Mockito.verify(file, Mockito.times(1)).exists();
        Mockito.verify(reader, Mockito.times(1)).readLine();
        Mockito.verify(wrapper, Mockito.times(1)).printOutLine("IOException");
    }

    @Test
    void loadConfig() throws IOException {
        writerReader.loadConfig(properties, inputStream);

        Mockito.verify(properties, Mockito.times(1)).load(inputStream);
    }

    @Test
    void loadConfigThrowsIOException() throws IOException {
        Mockito.doThrow(exception).when(properties).load(inputStream);
        Mockito.when(exception.getMessage()).thenReturn("IOException");

        writerReader.loadConfig(properties, inputStream);

        Mockito.verify(wrapper, Mockito.times(1)).printOutLine("IOException");
    }

    @Test
    void saveConfig() throws IOException {
        writerReader.saveConfig(12, 12, 150, 300, 0, false, 16,
                properties, outputStream);

        Mockito.verify(properties, Mockito.times(7)).setProperty(Mockito.anyString(), Mockito.any());
        Mockito.verify(properties, Mockito.times(1)).store(outputStream, null);
    }

    @Test
    void saveConfigThrowsException() throws IOException {
        Mockito.doThrow(exception).when(properties).store(outputStream, null);
        Mockito.when(exception.getMessage()).thenReturn("IOException");

        writerReader.saveConfig(12, 12, 150, 300, 0, false, 16,
                properties, outputStream);

        Mockito.verify(properties, Mockito.times(7)).setProperty(Mockito.anyString(), Mockito.any());
        Mockito.verify(wrapper, Mockito.times(1)).printOutLine("IOException");
    }

    @Test
    void getFile() {
        assertEquals(file, writerReader.getFile());
    }
}