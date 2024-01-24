package org.example.KundenPresistance;

import org.example.Classes.Kunde;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RamPersistanceTest {

    @Mock
    Kunde kunde;

    RamPersistance ram = new RamPersistance();

    @BeforeEach
    void setUp() {
    }

    @Test
    void save() {
        Mockito.when(kunde.getKundenNummer()).thenReturn("1");
        ram.save(kunde);
        assertEquals(1, ram.saveMap.size());
    }

    @Test
    void load() {
        Mockito.when(kunde.getKundenNummer()).thenReturn("1");
        ram.save(kunde);
        var result = ram.load("1", null);

        assertEquals("1", result.getKundenNummer());
    }
}