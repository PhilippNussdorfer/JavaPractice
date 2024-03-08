package bbrz.textadventure.entity;

import java.util.Random;

public class AttackCalc {
    public int getsAttacked(int dmg, int armor) {
        double damageReduction = (double) (armor) / 100;
        int actualDmg = (int) (dmg - dmg * damageReduction);

        if (actualDmg == 0) {
            actualDmg = 1;
        }

        return actualDmg;
    }

    public int getDmgRole(int dmg) {
        int minDmg = (int) ((double) (dmg) * 0.80);

        return new Random().nextInt(minDmg - 1, dmg + 1);
    }
}
