package fr.epu.vehicles;

import fr.epu.tracking.Position;
//import fr.epu.tracking.PositionNotAvailableException;
import fr.epu.tracking.Trackable;

/**
 * This class represents a drone.
 * A drone can be in two states: flying or not flying.
 * Some codes are commented because they are not used in the current version of the lesson.
 */
public class Drone extends ElectricVehicle implements Trackable {

    public Drone(double batteryCapacity, double energyConsumptionPerKilometer) {
        super(batteryCapacity, energyConsumptionPerKilometer);
    }

    private static final Position BASE_POSITION = new Position(0, 0);
    //private static final double MAX_DISTANCE = 100;
    boolean isFlying = false;

    public boolean isFlying() {
        return isFlying;
    }

    @Override
    public Position getPosition() { //throws PositionNotAvailableException {
        Position p = new Position(Math.random(), Math.random());
        if (isFlying()) {
            /* if (p.distance(BASE_POSITION) > MAX_DISTANCE) {
                throw new PositionNotAvailableException();
            } else
             */
                return p;
        }
        else
            return BASE_POSITION;
    }



    public void takeOff() {
        isFlying = true;
    }
    public void returnToBase() {
        isFlying = false;
    }
}
