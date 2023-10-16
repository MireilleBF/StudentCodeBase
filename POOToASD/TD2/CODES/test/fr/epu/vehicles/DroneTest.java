package fr.epu.vehicles;

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

}