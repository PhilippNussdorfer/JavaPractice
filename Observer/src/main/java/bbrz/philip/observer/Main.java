package bbrz.philip.observer;

public class Main {
    public static void main(String[] args) {

        ThieveEvent thieve = new ThieveEvent();
        ChickenRunEvent chickenRun = new ChickenRunEvent();
        OutsideEvent outside = new OutsideEvent();

        EventManager evManager = new EventManager();

        evManager.addListener(thieve);
        evManager.addListener(chickenRun);
        evManager.addListener(outside);

        evManager.eventNotifier(EventTypes.OUTSIDE_EVENT, "The Doors of the chicken coop are opened ");
        evManager.eventNotifier(EventTypes.CHICKEN_RUN_EVENT, "Thy are running Away");
        evManager.eventNotifier(EventTypes.THIEVE_EVENT, "A chicken thieve broke in to the chicken coop");

        evManager.removeListener(thieve);
        evManager.removeListener(outside);

        evManager.eventNotifier(EventTypes.OUTSIDE_EVENT, "The Doors of the chicken coop are opened ");
        evManager.eventNotifier(EventTypes.CHICKEN_RUN_EVENT, "Thy are running Away");
        evManager.eventNotifier(EventTypes.THIEVE_EVENT, "A chicken thieve broke in to the chicken coop");
    }
}