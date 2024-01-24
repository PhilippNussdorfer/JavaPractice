package BBRZ.Philipp.Singelton;

public class Main {

    public static void main(String[] args) {

        new HerderA();
        new HerderB();
        System.out.println("There are : " + SingletonSheepCounter.getInstance().getSheepCount() + " Sheep on the meadow.");
    }
}
