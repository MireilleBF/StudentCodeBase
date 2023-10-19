package fr.epu.rentals;

import fr.epu.rentals.costumes.Costume;
import fr.epu.rentals.costumes.CostumeSize;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalSystemTest {

    RentalSystem<Costume> costumeRentalSystem;

    Costume costumeBM = new Costume("Batman Adult", "Batman", CostumeSize.XLARGE);
    Costume costumeSQ= new Costume("SnowQueen", "Reine des neiges, snow Queen, for childs");
    Costume costumeBMC = new Costume("Batman Child ", "Batman for childs");

    Costume costumeW = new Costume("Witch", "Witch for childs");

    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    LocalDate nextWeek = today.plusWeeks(1);

    @BeforeEach
    void setUp() {
        costumeRentalSystem = new RentalSystem<>();
        costumeW.setUnavailable();
    }

    void initRentalSystem() {
        costumeRentalSystem.addItem(costumeBM);
        costumeRentalSystem.addItem(costumeSQ);
        costumeRentalSystem.addItem(costumeBMC);
        costumeRentalSystem.addItem(costumeW);
    }

    @org.junit.jupiter.api.Test
    void testAddItem() {
        assertEquals(0, costumeRentalSystem.getItems().size());
        costumeRentalSystem.addItem(costumeBM);
        assertEquals(1, costumeRentalSystem.getItems().size());
        costumeRentalSystem.addItem(costumeSQ);
        assertEquals(2, costumeRentalSystem.getItems().size());
        costumeRentalSystem.addItem(costumeW);
        assertEquals(3, costumeRentalSystem.getItems().size());
    }

    @org.junit.jupiter.api.Test
    void testRemoveItem() {
        initRentalSystem();
        assertEquals(4, costumeRentalSystem.getItems().size());
        costumeRentalSystem.removeItem(costumeBM);
        assertEquals(3, costumeRentalSystem.getItems().size());
        costumeRentalSystem.removeItem(costumeW);
        assertEquals(2, costumeRentalSystem.getItems().size());
        costumeRentalSystem.removeItem(costumeSQ);
        assertEquals(1, costumeRentalSystem.getItems().size());
        costumeRentalSystem.removeItem(costumeBMC);
        assertEquals(0, costumeRentalSystem.getItems().size());
    }

    @org.junit.jupiter.api.Test
    void testSearchItems() {
        initRentalSystem();
        assertEquals(2, costumeRentalSystem.searchItems("Batman").size());
        assertEquals(1, costumeRentalSystem.searchItems("Snow").size());
        assertEquals(0, costumeRentalSystem.searchItems("Spiderman").size());
        assertEquals(3, costumeRentalSystem.searchItems(CostumeSize.ONE_SIZE.name()).size());

    }

    @org.junit.jupiter.api.Test
    void testIsRentable() throws NoSuchItemException {
        initRentalSystem();
        //All items are rentable today
        assertTrue(costumeRentalSystem.isRentable(costumeBM, today, tomorrow));
        assertTrue(costumeRentalSystem.isRentable(costumeSQ, today, nextWeek));

        //A costume that is not available cannot be rented
        assertFalse(costumeRentalSystem.isRentable(costumeW, today, tomorrow));
        //Renting an item makes it unavailable for the same period
        costumeRentalSystem.rentItem(costumeBM, today, tomorrow);
        assertFalse(costumeRentalSystem.isRentable(costumeBM, today, nextWeek));
        assertTrue(costumeRentalSystem.isRentable(costumeSQ, today, nextWeek));
        assertTrue(costumeRentalSystem.isRentable(costumeBM, tomorrow, nextWeek));

        //Renting an item makes it unavailable for the same period (1 jour la semaine prochaine)
        costumeRentalSystem.rentItem(costumeBM, nextWeek, nextWeek.plusDays(1));
        assertTrue(costumeRentalSystem.isRentable(costumeBM, tomorrow, nextWeek));
        assertFalse(costumeRentalSystem.isRentable(costumeBM, tomorrow, nextWeek.plusWeeks(1)));
        assertTrue(costumeRentalSystem.isRentable(costumeSQ, nextWeek.plusDays(2), nextWeek.plusWeeks(1)));
    }


    @org.junit.jupiter.api.Test
    void testRentItem() throws NoSuchItemException {
        initRentalSystem();
        costumeRentalSystem.rentItem(costumeBM, today, tomorrow);
        assertEquals(1, costumeRentalSystem.getRentals().size());
        costumeRentalSystem.rentItem(costumeBM, nextWeek, nextWeek.plusDays(1));
        assertEquals(2, costumeRentalSystem.getRentals().size());
        costumeRentalSystem.rentItem(costumeSQ, nextWeek.plusDays(2), nextWeek.plusWeeks(1));
        assertEquals(3, costumeRentalSystem.getRentals().size());
    }


    @org.junit.jupiter.api.Test
    void findAvailableMatches() throws NoSuchItemException {
        initRentalSystem();
        assertEquals(2, costumeRentalSystem.findAvailableMatches("Batman", today, tomorrow).size());
        assertEquals(1, costumeRentalSystem.findAvailableMatches("Snow", today, nextWeek).size());
        assertEquals(0, costumeRentalSystem.findAvailableMatches("Spiderman", today, nextWeek).size());
        assertEquals(0, costumeRentalSystem.findAvailableMatches("Witch", today, nextWeek).size());
        assertEquals(2, costumeRentalSystem.findAvailableMatches(CostumeSize.ONE_SIZE.name(), today, nextWeek).size());
        costumeRentalSystem.rentItem(costumeBM, today, tomorrow);
        assertEquals(1, costumeRentalSystem.findAvailableMatches("Batman", today, tomorrow).size());
        costumeRentalSystem.rentItem(costumeBMC, today, tomorrow);
        assertEquals(0, costumeRentalSystem.findAvailableMatches("Batman", today, nextWeek).size());
        assertEquals(2, costumeRentalSystem.findAvailableMatches("Batman", tomorrow, nextWeek).size());


    }

}