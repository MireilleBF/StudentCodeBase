package fr.epu.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricVehicleTest {

    ElectricVehicle electricVehicle;
    double batteryCapacity = 30;
    static double ENERGY_CONSUMPTION_PER_KM_DEFAULT = 0.2;

    @BeforeEach
    void setUp() {
        electricVehicle = new ElectricVehicle(batteryCapacity, ENERGY_CONSUMPTION_PER_KM_DEFAULT);
    }

    @org.junit.jupiter.api.Test
    void testInitialiseVE() {
        assertEquals(batteryCapacity, electricVehicle.getBatteryCapacity());
        assertEquals(0, electricVehicle.getCurrentCharge());
        System.out.println("To check type : " + electricVehicle);
    }


    @org.junit.jupiter.api.Test
    void testChargeValid() {
        assertTrue((electricVehicle.charge(     10)));
        assertEquals(10, electricVehicle.getCurrentCharge());
        assertTrue((electricVehicle.charge(     20)));
        assertEquals(30, electricVehicle.getCurrentCharge());
    }
    @org.junit.jupiter.api.Test
    void testChargeNotValid() {
        assertFalse((electricVehicle.charge(     100)));
        assertEquals(0, electricVehicle.getCurrentCharge());
        assertTrue((electricVehicle.charge(     10)));
        assertEquals(10, electricVehicle.getCurrentCharge());
        assertFalse((electricVehicle.charge(     21)));
        assertEquals(10, electricVehicle.getCurrentCharge());
    }

    @org.junit.jupiter.api.Test
    void testCheckChargeParameter() {
        assertFalse((electricVehicle.charge(-10)));
        assertEquals(0, electricVehicle.getCurrentCharge());
    }



    @Test
    void testChargeToFull() {
        electricVehicle.charge(10);
        double charge = electricVehicle.chargeToFull();
        assertEquals(batteryCapacity, electricVehicle.getCurrentCharge());
        assertEquals(batteryCapacity-10, charge);
    }



    @org.junit.jupiter.api.Test
    void testChargeWith0() {
        assertFalse((electricVehicle.charge(0)));
        assertEquals(0, electricVehicle.getCurrentCharge());
    }
    @Test
    void testChargeToFullOneTime() {
        double charge = electricVehicle.chargeToFull();
        assertEquals(30, electricVehicle.getCurrentCharge());
        assertEquals(30, charge);
    }

    @Test
    void testConnexions() {
        assertFalse(electricVehicle.isConnected());
        assertTrue(electricVehicle.connect());
        assertTrue(electricVehicle.isConnected());
        assertFalse(electricVehicle.connect());
        assertTrue(electricVehicle.isConnected());
        electricVehicle.disconnect();
        assertFalse(electricVehicle.isConnected());
    }

    @org.junit.jupiter.api.Test
    void testCalculateMaxRange() {
        assertEquals(batteryCapacity/ ENERGY_CONSUMPTION_PER_KM_DEFAULT, electricVehicle.calculateMaxRange());
    }

    @org.junit.jupiter.api.Test
    void testCalculateMaxDistance() {
        assertEquals(0, electricVehicle.getCurrentCharge());
        assertEquals(electricVehicle.getCurrentCharge()/ ENERGY_CONSUMPTION_PER_KM_DEFAULT, electricVehicle.calculateMaxDistance());
        electricVehicle.charge(10);
        assertEquals(10/ ENERGY_CONSUMPTION_PER_KM_DEFAULT, electricVehicle.calculateMaxDistance());
    }
    @Test
    void testDrive() {
        double maxRange = electricVehicle.calculateMaxRange();
        //No charge
        assertFalse(electricVehicle.drive(10));
        electricVehicle.charge(30);
        assertTrue(electricVehicle.drive(10));
        maxRange -= 10;
        assertEquals(28, electricVehicle.getCurrentCharge());
        assertTrue(electricVehicle.drive(100));
        maxRange -= 100;
        System.out.println(maxRange);
        assertEquals(8, electricVehicle.getCurrentCharge());
        assertTrue(electricVehicle.drive(maxRange));
        assertEquals(0, electricVehicle.getCurrentCharge());
        assertFalse(electricVehicle.drive(1));
    }


}