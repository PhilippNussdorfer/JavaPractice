package bbrez.at.NewSpringBootTryWithInjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProgrammingClasses {

    private PresenceService service;
    private ApplicationOutput output;
    private GiveGrades giveGrades;
    private List<String> studentNameList;

    @Autowired
    public ProgrammingClasses(PresenceService service, ApplicationOutput output, GiveGrades giveGrades) {
        this.service = service;
        this.output = output;
        this.giveGrades = giveGrades;
    }

    public void presenceControl(int amountMax) {
        output.info("Are all here?");
        studentNameList = service.precedeAre(amountMax);
        int precened = studentNameList.size();

        output.info(precened + " are here");
        output.info("Students are here.");
        output.info("-----------------");

        for (String name : studentNameList) {
            output.info(name);
        }
    }

    public void testsNow() {
        for (Result res : tests()) {
            output.info(res.toString());
        }
    }

    private List<Result> tests() {
        List<Result> resultList = new ArrayList<>();
        for (String name : studentNameList) {
            resultList.add(giveGrades.res(name));
        }
        return resultList;
    }
}
