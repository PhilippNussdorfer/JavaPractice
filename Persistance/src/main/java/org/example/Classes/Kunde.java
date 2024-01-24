package org.example.Classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.Interfaces.Persistance;

@AllArgsConstructor
@Getter
public class Kunde {
    private String name, kundenNummer;
    private int alter;
    private Persistance<Kunde, String> db;

    public void save() {
        db.save(this);
    }

    @Override
    public String toString() {
        return name + ";" + alter + ";" + kundenNummer;
    }
}
