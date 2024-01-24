package bbrz.philip.observer;

public class OutsideEvent implements EventListener {

    @Override
    public void update(EventTypes evType, String data) {
        if (evType == EventTypes.OUTSIDE_EVENT) {
            System.out.println(data + "\n" +
                    "The chicken are happy and sunbathing\n");
        }
    }
}
