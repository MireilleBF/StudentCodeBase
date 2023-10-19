package fr.epu.vehicles;

import fr.epu.tracking.Position;
import fr.epu.tracking.Trackable;

public class Drone extends ElectricVehicle implements Trackable {

    public Drone(double batteryCapacity, double energyConsumptionPerKilometer) {
        super(batteryCapacity, energyConsumptionPerKilometer);
    }

    @Override
    public Position getPosition() {
        return new Position(Math.random(), Math.random());
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
