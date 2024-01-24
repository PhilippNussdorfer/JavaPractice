package bbrz.at;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileWriterReader {

    public static List<String> readTextFile(File file) {
        List<String> data = new ArrayList<>();
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine().replace("\\",""));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }

    public static void createFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeFile(File file , List<String> stringList) {
        try (PrintWriter writer = new PrintWriter(file)) {
            stringList.forEach(writer::println);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
