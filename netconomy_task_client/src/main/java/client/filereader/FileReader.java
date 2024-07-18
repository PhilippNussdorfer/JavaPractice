package client.filereader;

import java.util.List;
import java.util.Map;

public interface FileReader<T> {
    T readFile(String fileLocation);
}
