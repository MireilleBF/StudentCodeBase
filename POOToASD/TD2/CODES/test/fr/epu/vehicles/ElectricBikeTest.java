package fr.epu.vehicles;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ElectricBikeTest extends fr.epu.vehicles.ElectricVehicleTest {

    ElectricBike bike;

    @BeforeEach
    void setUp() {
        //to reuse the tests from ElectricVehicleTest we need to initialise the electricVehicle
        //with the same batteryCapacity and ENERGY_CONSUMPTION_PER_KM_DEFAULT by default
        bike = new ElectricBike(batteryCapacity, new double[]{ENERGY_CONSUMPTION_PER_KM_DEFAULT, 0.5, 0.8});
        electricVehicle = bike;
    }

    @org.junit.jupiter.api.Test
    void testInitialise() {
        assertEquals(0, bike.getCurrentCharge());
        assertEquals(0, bike.getPedalAssistLevel());
    }
    @org.junit.jupiter.api.Test
    void testInitialiseBike() {
        ElectricBike bike = new ElectricBike(batteryCapacity, new double[]{ENERGY_CONSUMPTION_PER_KM_DEFAULT, 0.5, 0.8});
        assertEquals(0, bike.getCurrentCharge());
        assertEquals(0, bike.getPedalAssistLevel());
        assertEquals(0.2, bike.getEnergyConsumptionPerKilometer());

        assertEquals(0.5, bike.getEnergyConsumptionForAssistLevel(1));
        assertEquals(0.8, bike.getEnergyConsumptionForAssistLevel(2));

        assertEquals(150, bike.calculateMaxRange());
    }

    @org.junit.jupiter.api.Test
    void testMaxRangeOfBike() {
        ElectricBike bike = new ElectricBike(batteryCapacity, new double[]{ENERGY_CONSUMPTION_PER_KM_DEFAULT, 0.5, 0.8});
        assertEquals(0, bike.getPedalAssistLevel());
        assertEquals(0.2, bike.getEnergyConsumptionPerKilometer());
        assertEquals(150, bike.calculateMaxRange());

        bike.setPedalAssistLevel(1);
        assertEquals(1, bike.getPedalAssistLevel());
        assertEquals(0.5, bike.getEnergyConsumptionPerKilometer());
        assertEquals(batteryCapacity / 0.5, bike.calculateMaxRange());

        bike.setPedalAssistLevel(2);
        assertEquals(2, bike.getPedalAssistLevel());
        assertEquals(0.8, bike.getEnergyConsumptionPerKilometer());
        assertEquals(batteryCapacity / 0.8, bike.calculateMaxRange());
    }

    @org.junit.jupiter.api.Test
    void testMaxDistanceOfBike() {
        ElectricBike bike = new ElectricBike(batteryCapacity, new double[]{ENERGY_CONSUMPTION_PER_KM_DEFAULT, 0.5, 0.8});
        assertEquals(0, bike.getPedalAssistLevel());
        assertEquals(0.2, bike.getEnergyConsumptionPerKilometer());
        assertEquals(0, bike.calculateMaxDistance());
        bike.charge(3);
        assertEquals(15, bike.calculateMaxDistance());
        bike.setPedalAssistLevel(1);
        assertEquals(1, bike.getPedalAssistLevel());
        assertEquals(0.5, bike.getEnergyConsumptionPerKilometer());
        assertEquals(batteryCapacity/10/ 0.5, bike.calculateMaxDistance());

        bike.setPedalAssistLevel(2);
        assertEquals(2, bike.getPedalAssistLevel());
        assertEquals(0.8, bike.getEnergyConsumptionPerKilometer());
        assertEquals(batteryCapacity/10 / 0.8, bike.calculateMaxDistance());
    }

    @org.junit.jupiter.api.Test
    void testCalculateMaxRange() {
        assertEquals(batteryCapacity/0.2, bike.calculateMaxRange());
        bike.setPedalAssistLevel(1);
        assertEquals(batteryCapacity/0.5, bike.calculateMaxRange());
        bike.setPedalAssistLevel(2);
        assertEquals(batteryCapacity/0.8, bike.calculateMaxRange());
        bike.setPedalAssistLevel(4);
        assertEquals(batteryCapacity/0.8, bike.calculateMaxRange());
    }

    @org.junit.jupiter.api.Test
    void testGetSetPedalAssistLevel() {
        assertEquals(0, bike.getPedalAssistLevel());
        bike.setPedalAssistLevel(1);
        assertEquals(1, bike.getPedalAssistLevel());
        bike.setPedalAssistLevel(2);
        assertEquals(2, bike.getPedalAssistLevel());
        bike.setPedalAssistLevel(4);
        assertEquals(2, bike.getPedalAssistLevel());
        bike.setPedalAssistLevel(-1);
        assertEquals(0, bike.getPedalAssistLevel());

    }


}