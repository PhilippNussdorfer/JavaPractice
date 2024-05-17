package bbrz.textadventure.tools;

import bbrz.textadventure.locations.Location;
import bbrz.textadventure.locations.LocationPointer;
import bbrz.textadventure.locations.Position;

public class LocationPointerTool {

    public void addPointerToLocation(Location prev, Location current) {
        Position prevPos = prev.getPos();
        Position currentPos = current.getPos();

        if (currentPos != null && prevPos != null) {
            if (prevPos.getX() > currentPos.getX()) {
                prev.addPointers(new LocationPointer("W", current));
                current.addPointers(new LocationPointer("E", prev));
            }

            if (prevPos.getX() < currentPos.getX()) {
                prev.addPointers(new LocationPointer("E", current));
                current.addPointers(new LocationPointer("W", prev));
            }

            if (prevPos.getY() > currentPos.getY()) {
                prev.addPointers(new LocationPointer("N", current));
                current.addPointers(new LocationPointer("S", prev));
            }

            if (prevPos.getY() < currentPos.getY()) {
                prev.addPointers(new LocationPointer("S", current));
                current.addPointers(new LocationPointer("N", prev));
            }
        }
    }
}
