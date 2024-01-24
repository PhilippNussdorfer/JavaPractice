package org.example.Classes;

import org.example.Interfaces.Persistance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KundeTest {
    @Mock
    Persistance<Kunde, String> db;

    @Test
    void save() {
        Kunde kunde = new Kunde("Hannes", "2", 22, db);
        kunde.save();
        Mockito.verify(db, Mockito.times(1)).save(kunde);
    }
}