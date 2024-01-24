package bbrez.at.NewSpringBootTryWithInjection;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class ApplicationOutput {
    public void info(String str) {
        log.info(str);
    }
}
