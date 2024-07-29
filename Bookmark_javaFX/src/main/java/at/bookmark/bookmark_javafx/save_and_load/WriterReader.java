package at.bookmark.bookmark_javafx.save_and_load;

import at.bookmark.bookmark_javafx.bookmark.Bookmark;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
                    bufferedWriter.write(bookmark.getNum() + "|" + bookmark.getTitle() + "|" + bookmark.getPage() + "|" + bookmark.getLink() + "\n");
                }

                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public List<Bookmark> loadFile() {
        File file = new File(path);
        List<Bookmark> bookmarks = new ArrayList<>();
        int count = 0;

        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String currentLine;

                while((currentLine = bufferedReader.readLine()) != null) {
                    String[] bookmarkArr = currentLine.split("\\|");
                    if (isParseAble(bookmarkArr[0]) && Integer.parseInt(bookmarkArr[0]) == count) {
                        bookmarks.add(new Bookmark(bookmarkArr[1], bookmarkArr[2], bookmarkArr[3], Integer.parseInt(bookmarkArr[0])));
                    } else {
                        bookmarks.add(new Bookmark(bookmarkArr[1], bookmarkArr[2], bookmarkArr[3], count));
                    }
                    count ++;
                }
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return bookmarks;
    }

    private boolean isParseAble(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Properties loadConfig(String filename) {
        Properties prop = new Properties();
        String path = "C:" + fileSeparator + "Java Programs" + fileSeparator + "Bookmark" + fileSeparator + filename;

        try (InputStream inputStream = new FileInputStream(path)) {
            prop.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return prop;
    }

    public void saveConfig(double x,  double y, int width, int height, int screenIndex, boolean isFullscreen, double fontSize, String filename) {
        Properties prop = new Properties();
        String path = "C:" + fileSeparator + "Java Programs" + fileSeparator + "Bookmark" + fileSeparator + filename;

        prop.setProperty("x", String.valueOf(x));
        prop.setProperty("y", String.valueOf(y));
        prop.setProperty("width", String.valueOf(width));
        prop.setProperty("height", String.valueOf(height));
        prop.setProperty("screenIndex", String.valueOf(screenIndex));
        prop.setProperty("isFullscreen", String.valueOf(isFullscreen));
        prop.setProperty("fontSize", String.valueOf(fontSize));

        try (OutputStream output = new FileOutputStream(path)) {
            prop.store(output, null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
