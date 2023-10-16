package fr.epu.charging;

import fr.epu.vehicles.ElectricBike;
import fr.epu.vehicles.ElectricCar;
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


    @Test
    void testInheritance(){
        ChargingStation chargingStation = new ChargingStation("Charging Station 1", 10);
        ElectricVehicle ev = new ElectricVehicle(30);
        double charge = chargingStation.connectToChargingPoint(ev);
        assertEquals(ev.getBatteryCapacity(), ev.getCurrentCharge());
        ElectricCar ec = new ElectricCar(30, "ABC123");
        charge = chargingStation.connectToChargingPoint(ec);
        assertEquals(ec.getBatteryCapacity(), ec.getCurrentCharge());
        ElectricBike eb = new ElectricBike(30, new double[]{0.1,0.2,0.5});
        charge = chargingStation.connectToChargingPoint(ec);
        assertEquals(ec.getBatteryCapacity(), ec.getCurrentCharge());
        ev = ec;
        //Forbidden  : ec = ev;
        //Forbidden  : ec = eb;

    }

    @Test
    void testInheritanceSecondVersion(){
        ChargingStation chargingStation = new ChargingStation("Charging Station 1", 10);
        ElectricVehicle ev = new ElectricVehicle(30);
        testIsFullCharged(chargingStation, ev);
        ElectricCar ec = new ElectricCar(30, "ABC123");
        testIsFullCharged(chargingStation, ec);
        ElectricBike eb = new ElectricBike(30, new double[]{0.1,0.2,0.5});
        testIsFullCharged(chargingStation, eb);
    }

    private static void testIsFullCharged(ChargingStation chargingStation, ElectricVehicle ev) {
        double charge = chargingStation.connectToChargingPoint(ev);
        assertEquals(ev.getBatteryCapacity(), ev.getCurrentCharge());
    }

}