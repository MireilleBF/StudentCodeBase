package fr.epu.rentals;

import fr.epu.vehicles.ElectricBike;
import fr.epu.vehicles.ElectricVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BikeRentalSystemTest {

    BikeRentalSystem bikeRentalSystem;
    ElectricBike electricBike3;
    ElectricBike electricBike1;
    ElectricBike electricBike2;
    ElectricBike electricBike4;
    double energyConsumptionPerKilometer = 0.2;

    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    LocalDate nextWeek = today.plusWeeks(1);
    @BeforeEach
    void setUp() {
        bikeRentalSystem = new BikeRentalSystem();
        electricBike1 = new ElectricBike(10, new double[]{energyConsumptionPerKilometer, 0.5});
        electricBike1.setAvailable(true);
        bikeRentalSystem.addItem(electricBike1);
        electricBike2 = new ElectricBike(10, new double[]{energyConsumptionPerKilometer, 0.5, 0.8});
        electricBike2.setAvailable(true);
        bikeRentalSystem.addItem(electricBike2);
        electricBike3 = new ElectricBike(10, new double[]{energyConsumptionPerKilometer, 0.5, 0.8,0.9});
        electricBike3.setAvailable(true);
        bikeRentalSystem.addItem(electricBike3);
        electricBike4 = new ElectricBike(10, new double[]{energyConsumptionPerKilometer, 0.5, 0.8,0.9,1.2});
        electricBike4.setAvailable(true);
        electricBike4.setPedalAssistLevel(4);
        bikeRentalSystem.addItem(electricBike4);


    }

    @Test
    void findAvailableMatches() {
        assertEquals(2,electricBike1.getNumberOfAvailableLevels());
        assertEquals(3,electricBike2.getNumberOfAvailableLevels());
        assertEquals(4,electricBike3.getNumberOfAvailableLevels());
        assertEquals(5,electricBike4.getNumberOfAvailableLevels());
        assertEquals(4, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,0).size());
        assertEquals(4, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,1).size());
        assertEquals(4, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,2).size());
        assertEquals(3, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,3).size());
        assertEquals(2, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,4).size());
        assertEquals(1, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,5).size());
        assertEquals(0, bikeRentalSystem.findAvailableMatches("*", today, tomorrow,6).size());
    }
}