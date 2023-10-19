package fr.epu.vehicles;

import fr.epu.tracking.PositionNotAvailableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneTest {

    @Test
    void testInitialiseDrone() {
        Drone drone = new Drone(30, 0.2);
        assertEquals(30, drone.getBatteryCapacity());
        assertEquals(0, drone.getCurrentCharge());
        assertEquals(0.2, drone.getEnergyConsumptionPerKilometer());
    }

    @Test
    void testGetPosition() throws PositionNotAvailableException {
        Drone drone = new Drone(30, 0.2);

        assertEquals(0, drone.getPosition().getX());
        assertEquals(0, drone.getPosition().getY());
        drone.takeOff();
        assertNotEquals(0, drone.getPosition().getX());
        assertNotEquals(0, drone.getPosition().getY());
        drone.returnToBase();
        assertEquals(0, drone.getPosition().getX());
        assertEquals(0, drone.getPosition().getY());
    }

}