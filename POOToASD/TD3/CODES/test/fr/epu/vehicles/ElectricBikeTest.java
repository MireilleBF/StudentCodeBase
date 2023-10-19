package fr.epu.vehicles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElectricBikeTest extends fr.epu.vehicles.ElectricVehicleTest {

    ElectricBike bike;

    final double[] energyConsumptionLevels = new double[]{energyConsumptionPerKilometer, 0.5, 0.8};
    @BeforeEach
    void setUp() {
        ElectricBike.resetIdentifier();
        //to reuse the tests from ElectricVehicleTest we need to initialise the electricVehicle
        //with the same batteryCapacity and energyConsumptionPerKilometer by default
        bike = new ElectricBike(batteryCapacity,energyConsumptionLevels);
        electricVehicle = bike;
    }

    @org.junit.jupiter.api.Test
    void testInitialise() {
        assertEquals(0, bike.getCurrentCharge());
        assertEquals(0, bike.getPedalAssistLevel());
    }
    @org.junit.jupiter.api.Test
    void testInitialiseBike() {
        ElectricBike bike = new ElectricBike(batteryCapacity, energyConsumptionLevels);
        assertEquals(0, bike.getCurrentCharge());
        assertEquals(0, bike.getPedalAssistLevel());
        assertEquals(0.2, bike.getEnergyConsumptionPerKilometer());

        assertEquals(0.5, bike.getEnergyConsumptionForAssistLevel(1));
        assertEquals(0.8, bike.getEnergyConsumptionForAssistLevel(2));

        assertEquals(150, bike.calculateMaxRange());
        assertEquals("EB-2",bike.getName());
        assertTrue(bike.isAvailable());
    }

    @org.junit.jupiter.api.Test
    void testIdentifiers() {
        assertEquals("EB-1", bike.getName());
        ElectricBike bike2 = new ElectricBike(batteryCapacity, energyConsumptionLevels);
        assertEquals("EB-2", bike2.getName());
        ElectricBike bike3 = new ElectricBike(batteryCapacity, energyConsumptionLevels);
        assertEquals("EB-3", bike3.getName());
        assertEquals("EB-1", bike.getName());
    }

    @Test
    void testSetAvailable() {
        assertTrue(bike.isAvailable());
        bike.setAvailable(false);
        assertFalse(bike.isAvailable());
        bike.setAvailable(true);
        assertTrue(bike.isAvailable());
    }

    /*
    « EB-4 3 levels », le velo d’identifiant EB-4 matchera cette définition mais également les vélos ayant 3 et 4 niveaux d’assistances. Nous avons volontairement simplifié le problème
     */
    @Test
    void testMatch() {
        assertTrue(bike.match("EB-1"));
        assertTrue(bike.match("3 levels"));
        assertTrue(bike.match("*"));
        //Berk Berk Berk I Know @TODO
        assertTrue(bike.match("EB-3"));

        ElectricBike bike2 = new ElectricBike(batteryCapacity, new double[]{energyConsumptionPerKilometer, 0.5, 0.8, 0.9});
        assertEquals("EB-2", bike2.getName());
        assertTrue(bike2.match("EB-2"));
        assertFalse(bike2.match("3 levels"));;
        assertTrue(bike2.match("4 levels"));
        //Berk Berk Berk I Know @TODO
        assertTrue(bike2.match("EB-4"));
    }

        @org.junit.jupiter.api.Test
    void testMaxRangeOfBike() {
        ElectricBike bike = new ElectricBike(batteryCapacity, new double[]{energyConsumptionPerKilometer, 0.5, 0.8});
        assertEquals(0, bike.getPedalAssistLevel());
        assertEquals(3,bike.getNumberOfAvailableLevels());
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
        ElectricBike bike = new ElectricBike(batteryCapacity, new double[]{energyConsumptionPerKilometer, 0.5, 0.8});
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

}