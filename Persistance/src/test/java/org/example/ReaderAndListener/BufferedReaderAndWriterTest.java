package org.example.ReaderAndListener;

import org.example.Classes.Kunde;
import org.example.ReaderAndWriter.BufferedReaderAndWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BufferedReaderAndWriterTest {

    @Mock
    BufferedReader reader;
    @Mock
    BufferedWriter writer;
    @Mock
    File deletableFile;
    @Mock
    IOException ioException;
    @Mock
    Kunde kunde;

    @Captor
    ArgumentCaptor<String> captor;

    BufferedReaderAndWriter<Kunde> readerAndWriter;

    @BeforeEach
    void setUp() {
        readerAndWriter = new BufferedReaderAndWriter<>(writer, deletableFile);
    }

    @Test
    void readFile() throws IOException {
        Mockito.when(reader.readLine()).thenReturn("1", null);

        var res = readerAndWriter.readFile(reader);
        assertEquals("1", res.get(0));

        Mockito.when(reader.readLine()).thenThrow(ioException);
        readerAndWriter.readFile(reader);
        Mockito.verify(ioException, Mockito.times(1)).printStackTrace();
    }

    @Test
    void writeInFile() throws IOException {
        Mockito.when(kunde.toString()).thenReturn("1");
        readerAndWriter.writeInFile(kunde);
        Mockito.verify(writer).write(captor.capture());
        assertEquals("1\n", captor.getValue());

        Mockito.doThrow(ioException).when(writer).write(Mockito.anyString());
        readerAndWriter.writeInFile(kunde);
        Mockito.verify(ioException, Mockito.times(1)).printStackTrace();
    }

    @Test
    void closeWriter() throws IOException {
        readerAndWriter.closeWriter();
        Mockito.verify(writer, Mockito.times(1)).close();

        Mockito.doThrow(ioException).when(writer).close();
        readerAndWriter.closeWriter();
        Mockito.verify(ioException, Mockito.times(1)).printStackTrace();
    }

    @Test
    void deleteFile() throws RuntimeException {
        Mockito.when(deletableFile.exists()).thenReturn(true);

        readerAndWriter.deleteFile();
        Mockito.verify(deletableFile, Mockito.times(1)).delete();
    }
}