package org.example.Classes;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.Interfaces.Persistance;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Mitarbeiter {
    private final String mitarbeiterNummer, name;
    private final SVNr sozialVersicherungsNr;
    private final Persistance<Mitarbeiter, SVNr> db;

    public void save() {
        db.save(this);
    }

    @Override
    public String toString() {
        return name + ";" + mitarbeiterNummer + ";" + sozialVersicherungsNr.getSvNr();
    }
}
