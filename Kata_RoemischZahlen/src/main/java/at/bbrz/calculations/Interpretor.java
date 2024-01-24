package at.bbrz.calculations;

import java.util.ArrayList;
import java.util.List;

public class Interpretor {

    List<Calc> calcList = new ArrayList<>();

    public Interpretor() {
        init();
    }

    private void init() {
        calcList.add(new Add());
        calcList.add(new Subtract());
        calcList.add(new Div());
        calcList.add(new Multiplei());
    }

    public int interpret(int firstNum, char operator, int secNum) {
        for (Calc calc : calcList) {
            if (calc.getOperator() == operator) {
                return calc.getResult(firstNum, secNum);
            }
        }
        System.out.println("this operator:" + operator + " dose not exist");
        return 0;
    }
}
