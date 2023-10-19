package fr.epu.fleets;

import fr.epu.vehicles.ElectricBike;
import fr.epu.vehicles.ElectricCar;
import fr.epu.vehicles.ElectricVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FleetOfVehiclesTest {

    FleetOfVehicles<ElectricVehicle> fleetOfVehicles;


    double maxRange;

    /**
     * Not a good test, but it is just to show how to use the generic class
     * I should decompose this test in several tests
     * But here it's only for the lecture
     */
    @Test
    void testSimpleFleetOfVehicles() {
        FleetOfVehicles<ElectricVehicle>fleetOfVehicles = new FleetOfVehicles();
        assertEquals(0, fleetOfVehicles.size());
        ElectricVehicle electricVehicle = new ElectricCar(30, "AB-123-CD");
        fleetOfVehicles.add(electricVehicle);
        assertEquals(1, fleetOfVehicles.size());
        assertTrue(fleetOfVehicles.contains(electricVehicle));
        //Without the equals method, we cannot check if the vehicle is already in the fleet
        assertFalse(fleetOfVehicles.contains(new ElectricCar(30, "AB-123-CD")));

        fleetOfVehicles.add(new ElectricCar(40, "AB-125-XS"));
        assertEquals(2, fleetOfVehicles.size());

        ElectricVehicle electricVehicle2 = fleetOfVehicles.get(1);
        assertEquals(40,electricVehicle2.getBatteryCapacity());
        assertEquals(30, fleetOfVehicles.get(0).getBatteryCapacity());
        //assertEquals("AB-125-XS", electricVehicle2.getRegistrationNumber());
    }

    /**
     * Not a good test, but it is just to show how to use the generic class
     * I should decompose this test in several tests
     * But here it's only for the lecture
     */
    @Test
    void testGenericOnASubclassBike(){
        FleetOfVehicles<ElectricBike>fleetOfVehicles = new FleetOfVehicles();
        assertEquals(0, fleetOfVehicles.size());
        ElectricBike electricVehicle = new ElectricBike(30, new double[]{0.2, 0.5, 0.8});
        fleetOfVehicles.add(electricVehicle);
        ElectricBike bike = fleetOfVehicles.get(0);
        assertEquals(0, bike.getPedalAssistLevel());


    }
    @BeforeEach
    void setUp() {
      fleetOfVehicles = new FleetOfVehicles();

        //Only to trace my tests
        double temp = 0;
        ElectricVehicle electricVehicle = new ElectricCar(30, "AB-123-CD");
        maxRange += electricVehicle.calculateMaxRange();
        //System.out.println(electricVehicle + " : " + (maxRange - temp) + " km");
        temp = maxRange;
        fleetOfVehicles.add(electricVehicle);

        electricVehicle = new ElectricCar(40, "AB-125-XS");
        maxRange += electricVehicle.calculateMaxRange();
        //System.out.println(electricVehicle + " : " + (maxRange - temp) + " km");
        temp = maxRange;
        fleetOfVehicles.add(electricVehicle);

        electricVehicle = new ElectricBike(20, new double[]{0.2, 0.5, 0.8});
        maxRange += electricVehicle.calculateMaxRange();
        //System.out.println(electricVehicle + " : " + (maxRange - temp) + " km");
        temp = maxRange;
        fleetOfVehicles.add(electricVehicle);

        electricVehicle = new ElectricBike(30, new double[]{0.2, 0.5, 0.8});
        maxRange += electricVehicle.calculateMaxRange();
        //System.out.println(electricVehicle + " : " + (maxRange - temp) + " km");
        temp = maxRange;
        fleetOfVehicles.add(electricVehicle);

       // System.out.println("Total max range : " + maxRange + " km");
        //System.out.println(fleetOfVehicles);
    }

    @Test
    void testCalculateTotalMaxRange() {
        assertEquals(maxRange, fleetOfVehicles.calculateTotalMaxRange());
    }

    @Test
    void testCalculateAverageMaxRange() {
        assertEquals(maxRange/4, fleetOfVehicles.calculateAverageMaxRange());

    }

    @Test
    void testChargeFullAllVehicles() {
        fleetOfVehicles.chargeFullAllVehicles();
        for (ElectricVehicle vehicle : fleetOfVehicles) {
            assertEquals(vehicle.getBatteryCapacity(), vehicle.getCurrentCharge());
        }
    }

    @Test
    void testChargeVehiclesBelowPercentage() {
        //All the vehicles are not charged before
        for (ElectricVehicle vehicle : fleetOfVehicles) {
            assertTrue(vehicle.chargePercentage()<50);
        }
        fleetOfVehicles.chargeVehiclesBelowPercentage(50);
        for (ElectricVehicle vehicle : fleetOfVehicles) {
            assertFalse(vehicle.chargePercentage()<50);
        }

        fleetOfVehicles.driveVehicles(  90);
        int numberOfVehiclesToCharge = 0;
        for (ElectricVehicle vehicle : fleetOfVehicles) {
            if (vehicle.chargePercentage()<50)
                numberOfVehiclesToCharge++;
        }
        assertTrue(numberOfVehiclesToCharge>0);
        assertEquals(3, numberOfVehiclesToCharge);

        fleetOfVehicles.chargeVehiclesBelowPercentage(50);

        for (ElectricVehicle vehicle : fleetOfVehicles) {
            assertFalse(vehicle.chargePercentage()<50);
        }

    }

    @Test
    void testCalculateMinDistance() {
        double minDistance = fleetOfVehicles.calculateMinDistance();
        assertEquals(100, minDistance);
    }
    @Test
    void testCalculateMaxDistance() {
        double maxDistance = fleetOfVehicles.calculateMaxDistance();
        assertEquals(200, maxDistance);
    }
    @Test
    void testDriveVehicles() {
        double distance = 0;
        double totalDistance = fleetOfVehicles.driveVehicles(distance);
        assertEquals(distance, totalDistance);

        fleetOfVehicles.chargeFullAllVehicles();
        distance = fleetOfVehicles.calculateMinDistance();
        assertEquals(100, distance);
        totalDistance = fleetOfVehicles.driveVehicles(distance);
        assertEquals(distance*4, totalDistance);

        fleetOfVehicles.chargeFullAllVehicles();
        distance = fleetOfVehicles.calculateMaxDistance();
        assertEquals(200, distance);
        totalDistance = fleetOfVehicles.driveVehicles(distance);
        //only one can drive the max distance
        assertEquals(distance, totalDistance);
    }
}