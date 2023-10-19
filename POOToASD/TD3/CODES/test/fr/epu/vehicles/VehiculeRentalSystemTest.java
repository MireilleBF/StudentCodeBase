package fr.epu.vehicles;

import fr.epu.rentals.RentalSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VehiculeRentalSystemTest {

    RentalSystem vehiculeRentalSystem;
    ElectricBike bike1;
    ElectricBike bike2;
    ElectricBike bike3 ;
    ElectricCar carTS ;
    ElectricCar carT3 ;
    ElectricCar carBM ;

    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    LocalDate nextWeek = today.plusWeeks(1);

    @BeforeEach
    void setUp() {
        vehiculeRentalSystem = new RentalSystem();
        ElectricBike.resetIdentifier();
        bike1 = new ElectricBike(30, new double[]{0.2, 0.5, 0.8});
        bike2 = new ElectricBike(30, new double[]{0.2, 0.6, 0.9, 1.2});
        bike3 = new ElectricBike(30, new double[]{0.2, 0.6, 0.9});
        carTS = new ElectricCar(30, "12 BG 06", "Tesla Model S");
        carT3 = new ElectricCar(30, "AB-123-CD", "Tesla Model 3");
        carBM = new ElectricCar(50, "ZW-124-CD", "BMW i3");
        carT3.setAvailable(false);
    }

    void initRentalSystem() {
        vehiculeRentalSystem.addItem(carBM);
        vehiculeRentalSystem.addItem(bike2);
        vehiculeRentalSystem.addItem(carTS);
        vehiculeRentalSystem.addItem(carT3);
        vehiculeRentalSystem.addItem(bike1);
        vehiculeRentalSystem.addItem(bike3);
    }

    @Test
    void testInitRentalSystem() {
        initRentalSystem();
        assertEquals(6, vehiculeRentalSystem.getItems().size());
        assertTrue(vehiculeRentalSystem.getItems().contains(carBM));
        assertEquals("EB-2", bike2.getName());
    }

    @Test
    void testAddItem() {
        assertEquals(0, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.addItem(carBM);
        assertEquals(1, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.addItem(bike2);
        assertEquals(2, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.addItem(carT3);
        assertEquals(3, vehiculeRentalSystem.getItems().size());

    }


    @Test
    void testRemoveItem() {
        initRentalSystem();
        assertEquals(6, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.removeItem(carBM);
        assertEquals(5, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.removeItem(carT3);
        assertEquals(4, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.removeItem(bike2);
        assertEquals(3, vehiculeRentalSystem.getItems().size());
        vehiculeRentalSystem.removeItem(carTS);
        assertEquals(2, vehiculeRentalSystem.getItems().size());
    }



    @org.junit.jupiter.api.Test
    void testSearchItems() {
        initRentalSystem();
        assertEquals(2, vehiculeRentalSystem.searchItems("Tesla").size());
        assertEquals(1, vehiculeRentalSystem.searchItems("BM").size());
        assertEquals(1, vehiculeRentalSystem.searchItems("EB-1").size());
        assertEquals(0, vehiculeRentalSystem.searchItems("Nissan").size());
        assertEquals(2, vehiculeRentalSystem.searchItems("3 levels").size());
        assertEquals(0, vehiculeRentalSystem.searchItems("EB").size());
    }

    @org.junit.jupiter.api.Test
    void testIsRentable() {
        initRentalSystem();
        //All items are rentable today
        assertTrue(vehiculeRentalSystem.isRentable(carBM, today, tomorrow));
        assertTrue(vehiculeRentalSystem.isRentable(bike2, today, nextWeek));

        //A costume that is not available cannot be rented
        assertFalse(vehiculeRentalSystem.isRentable(carT3, today, tomorrow));
        //Renting an item makes it unavailable for the same period
        vehiculeRentalSystem.rentItem(carBM, today, tomorrow);
        assertFalse(vehiculeRentalSystem.isRentable(carBM, today, nextWeek));
        assertTrue(vehiculeRentalSystem.isRentable(bike2, today, nextWeek));
        assertTrue(vehiculeRentalSystem.isRentable(carBM, tomorrow, nextWeek));

        //Renting an item makes it unavailable for the same period (1 jour la semaine prochaine)
        vehiculeRentalSystem.rentItem(carBM, nextWeek, nextWeek.plusDays(1));
        assertTrue(vehiculeRentalSystem.isRentable(carBM, tomorrow, nextWeek));
        assertFalse(vehiculeRentalSystem.isRentable(carBM, tomorrow, nextWeek.plusWeeks(1)));
        assertTrue(vehiculeRentalSystem.isRentable(bike2, nextWeek.plusDays(2), nextWeek.plusWeeks(1)));
    }


    @org.junit.jupiter.api.Test
    void testRentItem() {
        initRentalSystem();
        vehiculeRentalSystem.rentItem(carBM, today, tomorrow);
        assertEquals(1, vehiculeRentalSystem.getRentals().size());
        vehiculeRentalSystem.rentItem(carBM, nextWeek, nextWeek.plusDays(1));
        assertEquals(2, vehiculeRentalSystem.getRentals().size());
        vehiculeRentalSystem.rentItem(bike2, nextWeek.plusDays(2), nextWeek.plusWeeks(1));
        assertEquals(3, vehiculeRentalSystem.getRentals().size());
    }


    @org.junit.jupiter.api.Test
    void findAvailableMatches() {
        initRentalSystem();
        //One tesla and one bmw available today; one Tesla is not available
        assertEquals(1, vehiculeRentalSystem.findAvailableMatches("Tesla", today, tomorrow).size());
        assertEquals(1, vehiculeRentalSystem.findAvailableMatches("BM", today, nextWeek).size());
        assertEquals(0, vehiculeRentalSystem.findAvailableMatches("Spiderman", today, nextWeek).size());
        assertEquals(0, vehiculeRentalSystem.findAvailableMatches("Nissan", today, nextWeek).size());
        //Two bikes with 3 levels of assistance available today
        assertEquals(2, vehiculeRentalSystem.findAvailableMatches("3 levels", today, nextWeek).size());
        vehiculeRentalSystem.rentItem(carBM, today, tomorrow);
        assertEquals(1, vehiculeRentalSystem.findAvailableMatches("EB-1", today, tomorrow).size());
        vehiculeRentalSystem.rentItem(carTS, today, tomorrow);
        assertEquals(0, vehiculeRentalSystem.findAvailableMatches("Tesla", today, nextWeek).size());


    }

}