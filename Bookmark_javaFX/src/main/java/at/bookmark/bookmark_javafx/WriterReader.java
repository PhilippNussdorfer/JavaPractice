package at.bookmark.bookmark_javafx;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

public class WriterReader {
    private final String fileSeparator = FileSystems.getDefault().getSeparator();
    private final String path = "C:"+ fileSeparator +"Java Programs"+ fileSeparator +"Bookmark"+ fileSeparator +"Bookmarks.txt";
    private final String dir = "C:"+ fileSeparator +"Java Programs"+ fileSeparator +"Bookmark";

    public void writeFile(List<Bookmark> bookmarkList) {
        try {
            File dir = new File(this.dir);
            File file = new File(path);

            if (dir.mkdirs()) {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                }
            }

            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (Bookmark bookmark : bookmarkList) {
                    bufferedWriter.write(bookmark.getTitle() + "|" + bookmark.getPage() + "|" + bookmark.getLink() + "\n");
                }

                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List<Bookmark> loadFile() {
        File file = new File(path);
        List<Bookmark> bookmarks = new ArrayList<>();

        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String currentLine;

                while((currentLine = bufferedReader.readLine()) != null) {
                    String[] arr = currentLine.split("\\|");
                    bookmarks.add(new Bookmark(arr[0], arr[1], arr[2]));
                }
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return bookmarks;
    }
}
