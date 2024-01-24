package at.bbrz;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        GetRegistry eu = new EURegistry();
        GetRegistry as = new ASIARegistry();

        List<GetRegistry> kontinente = new ArrayList<>();

        kontinente.add(eu);
        kontinente.add(as);
        for (GetRegistry kontinent : kontinente) {
            kontinent.getCountry();

            System.out.println("<------------------------------------------------------------------>");
        }
    }
}
