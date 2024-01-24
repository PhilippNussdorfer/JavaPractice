package bbrez.at.NewSpringBootTryWithInjection;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log
public class EnglishClass {

    @Autowired
    private PresenceService service;

    public void presenceControl(int amountMax) {
        log.info("Are all here?");
        int hereAre = service.precedeAre(amountMax).size();
        log.info(hereAre + "are here");
    }
}
