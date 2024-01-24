package bbrez.at.NewSpringBootTryWithInjection;

import org.springframework.stereotype.Component;

@Component
public class SchoolClockService {
    public void ringPause() {
        System.out.println("Ring Ring Ring - pause");
    }

    public void ringPauseEnd() {
        System.out.println("Ring Ring Ring - pause end");
    }
}
