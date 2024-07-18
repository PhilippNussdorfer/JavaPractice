package server.filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TxtWriter implements FileWriter {

    @Override
    public boolean writeInFile(String directory, String filename, String content) {
        File dir = new File(directory);
        String fileLocation = dir.getAbsolutePath() + "/" + Paths.get(filename).getFileName();

        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(fileLocation));
            writer.write(content);
            writer.close();

            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }
}
