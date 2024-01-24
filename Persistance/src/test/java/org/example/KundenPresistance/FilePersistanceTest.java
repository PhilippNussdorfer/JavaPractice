package org.example.KundenPresistance;

import org.example.Classes.Kunde;
import org.example.ReaderAndWriter.BufferedReaderAndWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilePersistanceTest {
    @Mock
    BufferedReaderAndWriter<Kunde> readerAndWriter;
    @Mock
    Kunde kunde;
    @Mock
    BufferedReader reader;

    FilePersistance file;

    @BeforeEach
    void setUp() {
        file = new FilePersistance(readerAndWriter);
    }

    @Test
    void deleteAndCloseWriter() {
        file.deleteFile();
        Mockito.verify(readerAndWriter, Mockito.times(1)).deleteFile();

        file.closeWriter();
        Mockito.verify(readerAndWriter, Mockito.times(1)).closeWriter();
    }

    @Test
    void loadList() {
        file.loadList(reader);
        Mockito.verify(readerAndWriter, Mockito.times(1)).readFile(reader);
    }

    @Test
    void load() {
        var kundenList = List.of("Anna;33;1", "Hans;44;2");
        Mockito.when(readerAndWriter.readFile(reader)).thenReturn(kundenList);
        var kunde = file.load("2", reader);

        assertEquals("Hans", kunde.getName());
        assertEquals(44, kunde.getAlter());
        assertEquals("2", kunde.getKundenNummer());
    }

    @Test
    void getNullFromLoad() {
        var kundenList = List.of("Anna;33;1", "Hans;44;2");
        Mockito.when(readerAndWriter.readFile(reader)).thenReturn(kundenList);
        var kunde = file.load("3", reader);

        assertNull(kunde);
    }

    @Test
    void save() {
        file.save(kunde);
        Mockito.verify(readerAndWriter, Mockito.times(1)).writeInFile(kunde);
    }
}