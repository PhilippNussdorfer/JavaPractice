package org.example.MitarbeiterPersistance;

import org.example.Classes.Mitarbeiter;
import org.example.Classes.SVNr;
import org.example.Interfaces.Persistance;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RamPers implements Persistance<Mitarbeiter, SVNr> {

    protected Map<String, Mitarbeiter> ramMap = new HashMap<>();

    @Override
    public void save(Mitarbeiter object) {
        ramMap.put(object.getSozialVersicherungsNr().getSvNr(), object);
    }

    @Override
    public Mitarbeiter load(SVNr key, BufferedReader reader) {
        return ramMap.get(key.getSvNr());
    }
}
