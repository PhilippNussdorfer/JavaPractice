package bbrz.textadventure.tools;

import bbrz.textadventure.locations.Directions;
import bbrz.textadventure.locations.Location;
import bbrz.textadventure.locations.LocationPointer;
import bbrz.textadventure.locations.Position;

public class LocationPointerTool {

    public void addPointerToLocation(Location prev, Location current) {
        Position prevPos = prev.getPos();
        Position currentPos = current.getPos();

        if (currentPos != null && prevPos != null) {
            if (prevPos.getX() > currentPos.getX()) {
                prev.addPointers(new LocationPointer(Directions.WEST, current));
                current.addPointers(new LocationPointer(Directions.EAST, prev));
            }

            if (prevPos.getX() < currentPos.getX()) {
                prev.addPointers(new LocationPointer(Directions.EAST, current));
                current.addPointers(new LocationPointer(Directions.WEST, prev));
            }

            if (prevPos.getY() > currentPos.getY()) {
                prev.addPointers(new LocationPointer(Directions.NORTH, current));
                current.addPointers(new LocationPointer(Directions.SOUTH, prev));
            }

            if (prevPos.getY() < currentPos.getY()) {
                prev.addPointers(new LocationPointer(Directions.SOUTH, current));
                current.addPointers(new LocationPointer(Directions.NORTH, prev));
            }
        }
    }
}
