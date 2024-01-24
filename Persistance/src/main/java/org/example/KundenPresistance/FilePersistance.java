package org.example.KundenPresistance;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.Interfaces.Persistance;
import org.example.Classes.Kunde;
import org.example.ReaderAndWriter.BufferedReaderAndWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilePersistance implements Persistance<Kunde, String> {
    @Getter
    protected final String fileName = "TestFile";
    @NonNull
    private final BufferedReaderAndWriter<Kunde> readerAndWriter;

    @Override
    public void save(Kunde kunde) {
        readerAndWriter.writeInFile(kunde);
    }

    public void closeWriter() {
        readerAndWriter.closeWriter();
    }

    public void deleteFile() {
        readerAndWriter.deleteFile();
    }

    @Override
    public Kunde load(String kundenNummer, BufferedReader reader) {
        var stringList = readerAndWriter.readFile(reader);
        List<Kunde> kunden = new ArrayList<>();

        for (String str : stringList) {
            var strArr = str.split(";");
            kunden.add(new Kunde(strArr[0], strArr[2], Integer.parseInt(strArr[1]), this));
        }

        for (Kunde kunde : kunden) {
            if (kunde.getKundenNummer().equals(kundenNummer))
                return kunde;
        }
        return null;
    }

    public List<String> loadList(BufferedReader reader) {
        return readerAndWriter.readFile(reader);
    }
}
