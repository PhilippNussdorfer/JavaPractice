package bbrz.philip.observer;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private final List <EventListener> listenersList = new ArrayList<>();

    public EventManager() {

    }

    public void addListener(EventListener listener) {
        this.listenersList.add(listener);
    }

    public void removeListener(EventListener listener) {
        this.listenersList.remove(listener);
    }

    public void eventNotifier(EventTypes evType, String data) {
        for (EventListener listener : this.listenersList) {
            listener.update(evType, data);
        }
    }
}
