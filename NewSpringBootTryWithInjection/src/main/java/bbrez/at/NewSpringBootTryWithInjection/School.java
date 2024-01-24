package bbrez.at.NewSpringBootTryWithInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class School {

    @Autowired
    private SchoolClockService service;
    @Autowired
    private EnglishClass eClass;
    @Autowired
    private  ProgrammingClasses pClass;

    public void startSchool() {
        service.ringPause();
        service.ringPauseEnd();
        eClass.presenceControl(20);
        pClass.presenceControl(33);
        pClass.testsNow();
    }
}
