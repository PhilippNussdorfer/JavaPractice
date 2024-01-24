package bbrz.at.SpringBootTestDatabase;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

@Component
public class WriteAndReadFile {

    public String read(File fileToRead) {
        StringBuilder buildStr = new StringBuilder();
        try {
            Scanner reader = new Scanner(fileToRead);
            while (reader.hasNextLine()) {
                buildStr.append(reader.nextLine());
            }
            reader.close();
            return buildStr.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }
        return null;
    }

    public void write(File fileToWrite, String txt) {
        try {
            if (!fileToWrite.exists()) {
                if (fileToWrite.createNewFile()) {
                    System.out.println("Created file!");
                }
            }
            FileWriter writer = new FileWriter(fileToWrite);
            writer.write(txt);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }
    }
}
