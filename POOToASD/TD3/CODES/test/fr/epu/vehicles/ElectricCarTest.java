package fr.epu.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricCarTest extends ElectricVehicleTest {

    ElectricCar car;

    final String licensePlate = "AB-123-CD";
    final String brand = "Tesla";
    final String model = "Model S";

    @Override
    @BeforeEach
    void setUp() {
        car = new ElectricCar(batteryCapacity, licensePlate, brand + " " + model);
        electricVehicle = new ElectricCar(batteryCapacity, licensePlate);
    }

    @Test
    void testInitCar() {
        assertEquals(0, car.getCurrentCharge());
        assertEquals(licensePlate, car.getLicensePlate());
        assertFalse(car.isOnCoolingSystem());
        assertEquals(brand + " " + model, car.getModel());
        assertEquals("Not defined", ((ElectricCar) electricVehicle).getModel());
        assertTrue(car.isAvailable());
    }

    @Test
    void testSetAvailable() {
        assertTrue(car.isAvailable());
        car.setAvailable(false);
        assertFalse(car.isAvailable());
        car.setAvailable(true);
        assertTrue(car.isAvailable());
    }

    @Test
    void testTurnOnCoolingSystem() {
        assertFalse(car.isOnCoolingSystem());
        car.turnOnCoolingSystem();
        assertTrue(car.isOnCoolingSystem());
        car.turnOffCoolingSystem();
        assertFalse(car.isOnCoolingSystem());
    }


    @Test
    void testGetEnergyConsumptionPerKilometer() {
        double energyPerKm = car.getEnergyConsumptionPerKilometer();
        assertEquals(0.2, energyPerKm);
        car.turnOnCoolingSystem();
        assertEquals(0.2 * ElectricCar.COOLING_SYSTEM_FACTOR,
                car.getEnergyConsumptionPerKilometer());
        assertTrue(energyPerKm < car.getEnergyConsumptionPerKilometer());
        car.turnOffCoolingSystem();
        assertEquals(0.2, car.getEnergyConsumptionPerKilometer());
    }

    @Test
    void testMaxRangeOfCar() {
        double maxRange = car.calculateMaxRange();
        assertEquals(150, maxRange);
        car.turnOnCoolingSystem();
        assertTrue(maxRange > car.calculateMaxRange());
        assertEquals(150 / ElectricCar.COOLING_SYSTEM_FACTOR,
                car.calculateMaxRange());
        car.turnOffCoolingSystem();
        assertEquals(150, car.calculateMaxRange());
    }

    @Test
    void testMatch() {
        assertTrue(car.match("AB-123-CD"));
        assertFalse(car.match("AB-123-CE"));
        assertTrue(car.match("123"));
        assertTrue(car.match("Tesla"));
        assertTrue(car.match("S"));
    }
}