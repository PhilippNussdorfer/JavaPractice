package org.example.MitarbeiterPersistance;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.Classes.Mitarbeiter;
import org.example.Classes.SVNr;
import org.example.Interfaces.Persistance;
import org.example.ReaderAndWriter.BufferedReaderAndWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class FilePers implements Persistance<Mitarbeiter, SVNr> {
    @Getter
    protected final String fileName = "MitarbeiterFile";
    @NonNull
    private final BufferedReaderAndWriter<Mitarbeiter> readerAndWriter;

    @Override
    public void save(Mitarbeiter object) {
        readerAndWriter.writeInFile(object);
    }

    public void closeWriter() {
        readerAndWriter.closeWriter();
    }

    public void deleteFile() {
        readerAndWriter.deleteFile();
    }

    @Override
    public Mitarbeiter load(SVNr key, BufferedReader reader) {
        var stringList = readerAndWriter.readFile(reader);
        List<Mitarbeiter> mitarbeiterList = new ArrayList<>();

        for (String str : stringList) {
            var strArr = str.split(";");
            mitarbeiterList.add(new Mitarbeiter(strArr[1], strArr[0], new SVNr(strArr[2]), this));
        }

        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            if (Objects.equals(mitarbeiter.getSozialVersicherungsNr().getSvNr(), key.getSvNr()))
                return mitarbeiter;
        }
        return null;
    }

    public List<String> loadList(BufferedReader reader) {
        return readerAndWriter.readFile(reader);
    }
}
