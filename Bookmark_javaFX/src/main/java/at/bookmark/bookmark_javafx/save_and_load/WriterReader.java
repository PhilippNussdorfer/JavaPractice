package at.bookmark.bookmark_javafx.save_and_load;

import at.bookmark.bookmark_javafx.bookmark.Bookmark;
import at.bookmark.bookmark_javafx.tools.OutputWrapper;
import lombok.Getter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WriterReader {
    @Getter
    private final File file;
    private final File directory;
    private final OutputWrapper wrapper;

    public WriterReader(File file, File directory, OutputWrapper wrapper) {
        this.file = file;
        this.directory = directory;
        this.wrapper = wrapper;
    }

    public void writeFile(List<Bookmark> bookmarkList, BufferedWriter bufferedWriter) {
        try {

            if (directory.mkdirs()) {
                if (file.createNewFile()) {
                    wrapper.printOutLine("File created: " + file.getName());
                }
            }

            if (file.exists()) {
                for (Bookmark bookmark : bookmarkList) {
                    bufferedWriter.write(bookmark.getNum() + "|" + bookmark.getTitle() + "|" + bookmark.getPage() + "|" + bookmark.getLink() + "\n");
                }

                bufferedWriter.close();
            }
        } catch (IOException e) {
            wrapper.printOutLine("An error occurred: " + e.getMessage());
        }
    }

    public List<Bookmark> loadFile(BufferedReader bufferedReader) {
        List<Bookmark> bookmarks = new ArrayList<>();
        int count = 0;

        if (file.exists()) {
            try {
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
                wrapper.printOutLine(e.getMessage());
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

    public Properties loadConfig(Properties prop, InputStream inputStream) {
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            wrapper.printOutLine(e.getMessage());
        }

        return prop;
    }

    public void saveConfig(double x,  double y, int width, int height, int screenIndex, boolean isFullscreen, double fontSize, Properties prop, OutputStream output) {

        prop.setProperty("x", String.valueOf(x));
        prop.setProperty("y", String.valueOf(y));
        prop.setProperty("width", String.valueOf(width));
        prop.setProperty("height", String.valueOf(height));
        prop.setProperty("screenIndex", String.valueOf(screenIndex));
        prop.setProperty("isFullscreen", String.valueOf(isFullscreen));
        prop.setProperty("fontSize", String.valueOf(fontSize));

        try {
            prop.store(output, null);
        } catch (IOException e) {
            wrapper.printOutLine(e.getMessage());
        }
    }
}
