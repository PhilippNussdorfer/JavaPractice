package org.example.ReaderAndWriter;

import lombok.AllArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class BufferedReaderAndWriter<T> {
    private BufferedWriter writer;
    private File file;

    public List<String> readFile(BufferedReader reader) {
        List<String> results = new ArrayList<>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public void writeInFile(T object) {
        try {
            writer.write(object.toString()+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWriter() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile() {
        if (file.exists())
            if (file.delete())
                System.out.println("Deleted file\n");
    }
}
