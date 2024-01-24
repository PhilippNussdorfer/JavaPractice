package org.example.Classes;

import org.example.Interfaces.Persistance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MitarbeiterTest {
    @Mock
    Persistance<Mitarbeiter, SVNr> db;

    @Test
    void save() {
        Mitarbeiter mitarbeiter = new Mitarbeiter("1a", "Hansl", new SVNr("1"), db);
        mitarbeiter.save();
        Mockito.verify(db, Mockito.times(1)).save(mitarbeiter);
    }
}