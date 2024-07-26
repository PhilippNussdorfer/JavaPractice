package server.filewriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public interface FileWriter {
    boolean writeInFile(String directory, String filename, String content);
    static void zipFile(String directory, String filename) {
        File dir = new File(directory);
        String fileLocation = dir.getAbsolutePath() + "/" + Paths.get(filename).getFileName();

        try {
            FileOutputStream fileOut = new FileOutputStream(fileLocation + ".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fileOut);

            File fileToZip = new File(fileLocation);
            FileInputStream fileIn = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[4096];
            int length;

            while((length = fileIn.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }

            zipOut.close();
            fileIn.close();
            fileOut.close();

            File file = new File(fileLocation);
            if (!file.delete())
                System.out.println("Delete failed");

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
