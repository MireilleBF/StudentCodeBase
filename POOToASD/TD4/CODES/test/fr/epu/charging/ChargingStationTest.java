package fr.epu.charging;

import fr.epu.vehicles.ElectricVehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargingStationTest {

    @Test
    void testInitialiseChargingStation() {
        ChargingStation chargingStation = new ChargingStation("Charging Station 1", 10);
        assertEquals(10, chargingStation.getAvailableChargingPoints());
        EnergyProvider provider = chargingStation.getEnergyProvider();
        assertEquals("EDF", provider.getProviderName());
        assertEquals("Solar", provider.getEnergySource());
    }

    @Test
    void testConnect() {
        ChargingStation chargingStation = new ChargingStation("Charging Station 1", 10);
        ElectricVehicle cityCar = new ElectricVehicle(30);
        double charge = chargingStation.connectToChargingPoint(cityCar);
        assertEquals(9, chargingStation.getAvailableChargingPoints());
        assertEquals(30, cityCar.getCurrentCharge());
        assertEquals(30, charge);
        chargingStation.disconnectFromChargingPoint(cityCar);
        assertEquals(10, chargingStation.getAvailableChargingPoints());
    }



}