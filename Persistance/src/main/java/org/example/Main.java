package org.example;

import org.example.Classes.Kunde;
import org.example.Classes.Mitarbeiter;
import org.example.Classes.SVNr;
import org.example.KundenPresistance.FilePersistance;
import org.example.MitarbeiterPersistance.FilePers;
import org.example.ReaderAndWriter.BufferedReaderAndWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        var fileMit = getFilePers();
        //var ramMit = new RamPers();

        var file = getFilePersistance();
        //var ram = new RamPersistance();

        if (file != null) {
            List<Kunde> kundenList = new ArrayList<>();
            kundenList.add(new Kunde("Stefan", "1", 33, file));
            kundenList.add(new Kunde("Anna", "2", 22, file));

            for (Kunde kunde : kundenList) {
                kunde.save();
            }
            file.closeWriter();

            System.out.println(file.load("1", getReader(file.getFileName())));

            var list = file.loadList(getReader(file.getFileName()));

            for (String kunde : list) {
                System.out.println(kunde);
            }

            file.deleteFile();
            //System.out.println(ram.load("2", null).toString());
            System.out.println("\n");
            //************************************************************************************************************//
        }

        if (fileMit != null) {
            List<Mitarbeiter> mitarbeiterList = new ArrayList<>();
            mitarbeiterList.add(new Mitarbeiter("1a", "Henry", new SVNr("1"), fileMit));
            mitarbeiterList.add(new Mitarbeiter("1b", "Anna", new SVNr("2"), fileMit));

            for (Mitarbeiter mitarbeiter : mitarbeiterList) {
                mitarbeiter.save();
            }
            fileMit.closeWriter();

            var mitList = fileMit.loadList(getReader(fileMit.getFileName()));

            for (String mitarbeiter : mitList) {
                System.out.println(mitarbeiter);
            }

            System.out.println(fileMit.load(new SVNr("1"), getReader(fileMit.getFileName())));

            //System.out.println(ramMit.load(new SVNr("1"), null).toString());
            fileMit.deleteFile();
        }
    }

    private static BufferedReaderAndWriter getReaderAndWriter(File file) {
        try {
            return new BufferedReaderAndWriter(new BufferedWriter(new FileWriter(file)), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedReader getReader(String fileName) {
        try {
            return new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static FilePers getFilePers() {
        String fileName = "MitarbeiterFile";
        var writerAndReader = getReaderAndWriter(new File(fileName));
        if (writerAndReader != null)
            return new FilePers(writerAndReader);
        return null;
    }

    private static FilePersistance getFilePersistance() {
        String fileName = "TestFile";
        var writerAndReader = getReaderAndWriter(new File(fileName));
        if (writerAndReader != null)
            return new FilePersistance(writerAndReader);
        return null;
    }
}
