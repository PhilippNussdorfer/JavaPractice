package BBRZ.Philipp.Singelton;

public class SingletonSheepCounter {

    private static SingletonSheepCounter instance;
    private int sheepCount;

    private SingletonSheepCounter() {

    }

    public static SingletonSheepCounter getInstance() {
        if (instance == null) {
            instance = new SingletonSheepCounter();
        }
        return instance;
    }

    public void countSheep() {

        this.sheepCount++;
    }

    public int getSheepCount() {

        return this.sheepCount;
    }
}
