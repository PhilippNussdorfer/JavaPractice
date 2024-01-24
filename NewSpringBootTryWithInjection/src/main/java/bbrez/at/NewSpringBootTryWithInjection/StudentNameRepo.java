package bbrez.at.NewSpringBootTryWithInjection;

import java.util.List;

public interface StudentNameRepo {
    List<String> studentName(String clas, int amount);
}
