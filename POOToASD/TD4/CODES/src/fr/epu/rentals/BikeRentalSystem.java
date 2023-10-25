package fr.epu.rentals;

import fr.epu.vehicles.ElectricBike;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BikeRentalSystem extends RentalSystem<ElectricBike>{

    public List<ElectricBike> findAvailableMatches(String description, LocalDate beginDate, LocalDate endDate, int minAssistLevel) {
        List<ElectricBike> matchingItems = super.findAvailableMatches(description, beginDate, endDate);
        List<ElectricBike> rentableItems = new ArrayList<>();
        for (ElectricBike bike : matchingItems) {
            if (bike.getNumberOfAvailableLevels() >= minAssistLevel) {
                rentableItems.add(bike);
            }
        }
        return rentableItems;
    }
}
