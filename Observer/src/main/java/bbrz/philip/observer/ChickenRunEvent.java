package bbrz.philip.observer;

public class ChickenRunEvent implements EventListener {

    @Override
    public void update(EventTypes evType, String data) {
        if (evType == EventTypes.CHICKEN_RUN_EVENT) {
            System.out.println(data + "\n" +
                    "you run chase the chicken\n");
        }
    }
}
