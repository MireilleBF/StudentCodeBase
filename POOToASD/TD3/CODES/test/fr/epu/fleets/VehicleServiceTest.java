package fr.epu.fleets;

import fr.epu.vehicles.ElectricBike;
import fr.epu.vehicles.ElectricCar;
import fr.epu.vehicles.ElectricVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class VehicleServiceTest {

    VehicleService vehicleService;

    ElectricVehicle electricVehicle;
    ElectricBike ebike;
    ElectricCar ecar;
    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService(10);
        electricVehicle = new ElectricCar(30, "AB-123-CD");
        System.out.println("electricVehicle range: " + electricVehicle.calculateMaxRange() + " km");
        System.out.println("electricVehicle distance: " + electricVehicle.calculateMaxDistance() + " km");
        ebike = new ElectricBike(15, new double[]{0.15, 0.5, 0.8});
        System.out.println("ebike range: " + ebike.calculateMaxRange() + " km");
        System.out.println("ebike distance: " + ebike.calculateMaxDistance() + " km");
        ecar = new ElectricCar(40, "AB-125-XS");
        ecar.turnOnCoolingSystem();
        System.out.println("ecar range: " + ecar.calculateMaxRange() + " km");
        System.out.println("ecar distance: " + ecar.calculateMaxDistance() + " km");
    }

    void init() {
        vehicleService.addAvailableVehicle(electricVehicle);
        vehicleService.addAvailableVehicle(ebike);
        vehicleService.addAvailableVehicle(ecar);
    }

    void initChargeFull(){
        electricVehicle.chargeToFull();
        ebike.chargeToFull();
        ecar.chargeToFull();
    }

    void initCharge(double i){
        electricVehicle.charge(i);
        ebike.charge(i);
        ecar.charge(i);
    }

    @Test
    void testInit() {
        assertEquals(0, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(0, vehicleService.getNbOfAvailableVehiclesInCharge());
        assertEquals(0, vehicleService.getNbOfAvailableVehicles());
        init();
        assertEquals(0, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(0, vehicleService.getNbOfAvailableVehiclesInCharge());
        assertEquals(3, vehicleService.getNbOfAvailableVehicles());
    }

    @Test
    void testAddAvailableVehicle() {
        assertEquals(0, vehicleService.getNbOfAvailableVehicles());
        vehicleService.addAvailableVehicle(electricVehicle);
        assertEquals(1, vehicleService.getNbOfAvailableVehicles());
        vehicleService.addAvailableVehicle(ebike);
        assertEquals(2, vehicleService.getNbOfAvailableVehicles());
        vehicleService.addAvailableVehicle(ecar);
        assertEquals(3, vehicleService.getNbOfAvailableVehicles());
    }

    @Test
    void testAddElectricVehicleInRepair() {
        assertEquals(0, vehicleService.getNbOfVehiclesInRepair());
        vehicleService.addVehicleInRepair(electricVehicle);
        assertEquals(1, vehicleService.getNbOfVehiclesInRepair());
        vehicleService.addVehicleInRepair(ebike);
        assertEquals(2, vehicleService.getNbOfVehiclesInRepair());
        vehicleService.addVehicleInRepair(ecar);
        assertEquals(3, vehicleService.getNbOfVehiclesInRepair());
    }

    @Test
    void testMoveVehicleToRepair() {
        init();
        assertEquals(0, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(3, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToRepair(electricVehicle);
        assertEquals(1, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(2, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToRepair(ebike);
        assertEquals(2, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(1, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToRepair(ecar);
        assertEquals(3, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(0, vehicleService.getNbOfAvailableVehicles());
    }
    @Test
    void testMoveVehicleToRepairWhenNotPresent() {
        init();
        assertEquals(0, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(3, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToRepair(electricVehicle);
        assertEquals(1, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(2, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToRepair(electricVehicle);
        assertEquals(1, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(2, vehicleService.getNbOfAvailableVehicles());
    }

    @Test
    void testMoveVehicleToAvailable() {
        vehicleService.addVehicleInRepair(electricVehicle);
        vehicleService.addVehicleInRepair(ebike);
        vehicleService.addVehicleInRepair(ecar);
        assertEquals(3, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(0, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToAvailable(electricVehicle);
        assertEquals(2, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(1, vehicleService.getNbOfAvailableVehicles());
        vehicleService.moveVehicleToAvailable(ebike);
        assertEquals(1, vehicleService.getNbOfVehiclesInRepair());
        assertEquals(2, vehicleService.getNbOfAvailableVehicles());
    }

    @Test
    void testFindAvailableVehicleWithMaxDistance() {
        init();
        assertEquals(0, vehicleService.getNbOfVehiclesInRepair());
        ElectricVehicle ev = vehicleService.findAvailableVehicleWithMaxDistance();
        //No vehicle is charged
        assertNull(ev);

        initCharge(10);
        //bike max distance = 66.666
        ev=vehicleService.findAvailableVehicleWithMaxDistance();
        assertEquals(ebike,ev);

        initChargeFull();
        //ecar max distance 166
        ev=vehicleService.findAvailableVehicleWithMaxDistance();
        assertEquals(ecar,ev);

        vehicleService.moveVehicleToRepair(ev);
        //electricCar max distance 15O
        ev=vehicleService.findAvailableVehicleWithMaxDistance();
        assertEquals(electricVehicle,ev);

        vehicleService.moveVehicleToRepair(ev);
        ev=vehicleService.findAvailableVehicleWithMaxDistance();
        assertEquals(ebike,ev);

    }

    @Test
    void testFindVehicleWithMaxRangeInAvailableAndInRepair() {
        init();
        ElectricVehicle ev = vehicleService.findVehicleWithMaxRangeInAvailableAndInRepair();
        assertEquals(ecar,ev);

        vehicleService.moveVehicleToRepair(ev);
        ev = vehicleService.findVehicleWithMaxRangeInAvailableAndInRepair();
        assertEquals(ecar,ev);
    }

    @Test
    void testGetNbOfAvailableVehiclesInCharge() {
        init();
        int i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(0,i);

        ecar.connect();
        i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(1,i);

        vehicleService.moveVehicleToRepair(ecar);
        i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(0,i);

        ebike.connect();
        i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(1,i);
        electricVehicle.connect();
        i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(2,i);
        vehicleService.moveVehicleToAvailable(ecar);
        i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(3,i);

        ebike.disconnect();
        i = vehicleService.getNbOfAvailableVehiclesInCharge();
        assertEquals(2,i);

    }

}