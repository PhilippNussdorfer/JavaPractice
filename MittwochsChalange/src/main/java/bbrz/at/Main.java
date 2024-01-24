package bbrz.at;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        var rnd = new Random();

        System.out.println("A is Bigger Same than B:");
        for (int i = 0; i < 100; i++) {
            int A = rnd.nextInt(200) - 100;
            int B = rnd.nextInt(200) - 100;

            System.out.println(A + " >= " + B);
            System.out.println("A: => " + A);
            System.out.println("B: => " + B);

            if (A >= B && biggerSame(A, B)) {
                System.out.println(biggerSame(A, B));
                System.out.println("Congratulations you made it!\n<----------->\n");

            } else if (!(A >= B) && !biggerSame(A, B)) {
                System.out.println(biggerSame(A, B));
                System.out.println("Congratulations you made it!\n<----------->\n");

            } else {
                System.out.println(biggerSame(A, B));
                System.out.println("You failed!\n<----------->\n");
            }
        }

        System.out.println("<" + "-".repeat(50) + ">" + "\nA is Smaller Same than B:");
        for (int i = 0; i < 100; i++) {
            int A = rnd.nextInt(200) - 100;
            int B = rnd.nextInt(200) - 100;

            System.out.println(A + " <= " + B);
            System.out.println("A: => " + A);
            System.out.println("B: => " + B);

            if (A <= B && smallerSame(A, B)) {
                System.out.println(smallerSame(A, B));
                System.out.println("Congratulations you made it!\n<----------->\n");

            } else if (!(A <= B) && !smallerSame(A, B)) {
                System.out.println(smallerSame(A, B));
                System.out.println("Congratulations you made it!\n<----------->\n");

            } else {
                System.out.println(smallerSame(A, B));
                System.out.println("You failed!\n<----------->\n");
            }
        }
    }

    private static boolean biggerSame(int A, int B) {
        if (A == B) {
            return true;
        }

        else if (isNegative(A) && isNegative(B)) {
            return !isNegative(A + toPositive(B));
        }

        else if (!isNegative(A) && !isNegative(B)) {
            return !isNegative(A + toNegative(B));
        }

        else if (isNegative(A)) {
            return false;
        }

        else return isNegative(B);
    }

    private static boolean smallerSame(int A, int B) {
        if (A == B) {
            return true;
        } else {
            return !biggerSame(A, B);
        }
    }

    private static boolean isNegative(int num) {
        if (signed(num) != 0) {
            return true;
        }
        return false;
    }

    private static int signed(int value) {
        return value & 0x80000000;
    }

    private static int toPositive(int negativeNumber) {

        int positiveNumber = (isNegative(negativeNumber)) ? -negativeNumber : negativeNumber;

        return positiveNumber;
    }

    private static int toNegative(int positiveNumber) {
        return(!isNegative(positiveNumber)) ? -positiveNumber : positiveNumber;
    }
}