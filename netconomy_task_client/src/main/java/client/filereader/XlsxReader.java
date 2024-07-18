package client.filereader;

import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class XlsxReader implements FileReader<Map<Integer, List<String>>> {

    @Override
    public Map<Integer, List<String>> readFile(String fileLocation) {
        Map<Integer, List<String>> data = new HashMap<>();

        try (FileInputStream file = new FileInputStream(fileLocation);
             ReadableWorkbook wb = new ReadableWorkbook(file)) {

            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(row -> {
                    data.put(row.getRowNum(), new ArrayList<>());

                    for (Cell cell : row) {
                        data.get(row.getRowNum()).add(cell.getRawValue());
                    }
                });
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        return data;
    }
}
