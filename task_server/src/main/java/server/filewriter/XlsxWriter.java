package server.filewriter;

import lombok.AllArgsConstructor;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
public class XlsxWriter implements FileWriter {
    private final int countOfColumns;

    @Override
    public boolean writeInFile(String directory, String filename, String content) {
        String[] strArr = content.split(";");
        File dir = new File(directory);
        String fileLocation = dir.getAbsolutePath() + "/" + Paths.get(filename).getFileName();

        try (OutputStream out = Files.newOutputStream(Paths.get(fileLocation));
             Workbook wb = new Workbook(out, "Task_Server", "1.0")) {

            Worksheet ws = wb.newWorksheet("Sheet 1");
            fillWorksheet(strArr, ws);

            return true;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    private void fillWorksheet(String[] strArr, Worksheet ws) {
        int index = 0;
        int row = 0;
        while (index < strArr.length) {

            for (int col = 0; col < countOfColumns; col++) {
                if (index >= strArr.length)
                    break;

                ws.value(row, col, strArr[index]);
                index ++;
            }

            row ++;
        }
    }
}
