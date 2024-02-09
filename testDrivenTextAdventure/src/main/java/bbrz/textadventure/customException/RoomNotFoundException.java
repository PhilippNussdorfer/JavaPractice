package bbrz.textadventure.customException;

public class RoomNotFoundException extends Exception {

    public RoomNotFoundException(String errMsg) {
        super(errMsg);
    }
}
