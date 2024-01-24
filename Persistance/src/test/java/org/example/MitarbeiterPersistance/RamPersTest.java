package org.example.MitarbeiterPersistance;

import org.example.Classes.Mitarbeiter;
import org.example.Classes.SVNr;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RamPersTest {

    @Mock
    Mitarbeiter mitarbeiter;
    @Mock
    SVNr svNr;

    RamPers ram = new RamPers();

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        Mockito.when(svNr.getSvNr()).thenReturn("1");
        Mockito.when(mitarbeiter.getSozialVersicherungsNr()).thenReturn(svNr);
        ram.save(mitarbeiter);
        assertEquals(1, ram.ramMap.size());
        assertEquals(mitarbeiter, ram.ramMap.get("1"));
    }

    @Test
    void load() {
        Mockito.when(svNr.getSvNr()).thenReturn("1");
        Mockito.when(mitarbeiter.getSozialVersicherungsNr()).thenReturn(svNr);
        ram.save(mitarbeiter);
        assertEquals(mitarbeiter, ram.load(new SVNr("1"), null));
    }
}