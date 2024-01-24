package org.example.MitarbeiterPersistance;

import org.example.Classes.Mitarbeiter;
import org.example.Classes.SVNr;
import org.example.ReaderAndWriter.BufferedReaderAndWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilePersTest {

    @Mock
    BufferedReaderAndWriter<Mitarbeiter> readerAndWriter;
    @Mock
    Mitarbeiter mitarbeiter;
    @Mock
    BufferedReader reader;

    FilePers file;

    @BeforeEach
    void beforeEach() {
        file = new FilePers(readerAndWriter);
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
        var mitarList = List.of("Anna;1b;1", "Hans;2a;2");
        Mockito.when(readerAndWriter.readFile(reader)).thenReturn(mitarList);
        var mitarbeiter = file.load(new SVNr("2"), reader);

        assertEquals("Hans", mitarbeiter.getName());
        assertEquals("2a", mitarbeiter.getMitarbeiterNummer());
        assertEquals("2", mitarbeiter.getSozialVersicherungsNr().getSvNr());
    }

    @Test
    void getNullFromLoad() {
        var MitarList = List.of("Anna;1b;1", "Hans;2a;2");
        Mockito.when(readerAndWriter.readFile(reader)).thenReturn(MitarList);
        var mitarbeiter = file.load(new SVNr("3"), reader);

        assertNull(mitarbeiter);
    }

    @Test
    void save() {
        file.save(mitarbeiter);
        Mockito.verify(readerAndWriter, Mockito.times(1)).writeInFile(mitarbeiter);
    }
}