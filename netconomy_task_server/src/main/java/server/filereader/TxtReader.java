package server.filereader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TxtReader implements FileReader<String> {

    @Override
    public String readFile(String fileLocation) {
        StringBuilder strBuilder = new StringBuilder();

        try(InputStream in = Files.newInputStream(Paths.get(fileLocation));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line);
            }
            return strBuilder.toString();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
