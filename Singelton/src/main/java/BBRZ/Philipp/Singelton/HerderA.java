package BBRZ.Philipp.Singelton;

import java.util.Random;

public class HerderA {

    public HerderA() {
        herderA();
    }

    public void herderA() {

        Random rnd = new Random();
        int randNum = rnd.nextInt(2, 20);

        for (int i = 0; i < randNum; i++) {
            SingletonSheepCounter.getInstance().countSheep();
        }
    }
}