package bbrz.philip.observer;

public class ThieveEvent implements EventListener {

    @Override
    public void update(EventTypes evType, String data) {
        if (evType == EventTypes.THIEVE_EVENT) {
            System.out.println(data + "\n" +
                    "3 chicken are missing\n");
        }
    }
}
