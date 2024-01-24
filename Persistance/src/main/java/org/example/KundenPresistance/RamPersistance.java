package org.example.KundenPresistance;

import lombok.Getter;
import org.example.Interfaces.Persistance;
import org.example.Classes.Kunde;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

@Getter
public class RamPersistance implements Persistance<Kunde, String> {
    protected Map<String, Kunde> saveMap = new HashMap<>();

    @Override
    public void save(Kunde kunde) {
        saveMap.put(kunde.getKundenNummer(), kunde);
    }

    @Override
    public Kunde load(String kundenNummer, BufferedReader reader) {
        return saveMap.get(kundenNummer);
    }
}
