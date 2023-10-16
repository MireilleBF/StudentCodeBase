package fr.epu.rentals;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;


/**
 * The class which manages the rental system.
 */
public class RentalSystem {

    //tous les items à la location sont stockés dans une map
    //la clé est le nom de l'item
    //la valeur est l'item
    private Map<String,RentableItem> items;

    //les locations sont stockées dans une map avec comme clé le nom de l'item
    //et comme valeur une liste de locations pour cet item ordonnées par date de début
    private Map<String,List<Rental>> rentalsByItem;


    //le coût par défaut d'une location par jour
    private static final double DEFAULT_COST_PER_DAY = 10.0;
    private double defaultCostPerDay;

    /* *************************************************************************************************************
        * The following methods allow to create RentalSystem objects
        * ************************************************************************************************************
     */
        public RentalSystem() {
            this(DEFAULT_COST_PER_DAY);

        }
        public RentalSystem(double costPerDay){
            items = new HashMap<>();
            rentalsByItem = new HashMap<>();
            defaultCostPerDay = costPerDay;
    }

        /* *************************************************************************************************************
         * The following methods allow to add and remove items
         * ************************************************************************************************************
         */

        public void addItem(RentableItem item) {
            if (items.containsKey(item.getName())) {
                throw new IllegalArgumentException("An item with the same name already exists");
            }

            items.put(item.getName(), item);
            rentalsByItem.put(item.getName(), new ArrayList<>());
        }

        public void removeItem(RentableItem item) {
            items.remove(item.getName());
        }

        /* *************************************************************************************************************
         * The following methods allow to check if an item is rentable
         * ************************************************************************************************************
         */
        public boolean isRentable(RentableItem item, LocalDate beginDate, LocalDate endDate) {
            if (!item.isAvailable()) {
                return false;
            }
            final List<Rental> rentals = rentalsByItem.get(item.getName());

            for (Rental rental : rentals) {
                //if the existing rental starts before the end date and ends after the begin date, the item is not available
                if (rental.getStartDate().isBefore(endDate) && rental.getEndDate().isAfter(beginDate)) {
                    return false;
                }
            }

            return true;

        }

/* *************************************************************************************************************
    * The following methods allow to search for items
    * ************************************************************************************************************
 */

    /**
     * Returns all the items in the rental system (in no particular order)
     * @return all the items in the rental system
     */
    public List<RentableItem> getItems() {
        return new ArrayList<>(items.values());
    }
    public List<RentableItem> searchItems(String description) {
        final List<RentableItem> matchingItems = new ArrayList<>();

        for (RentableItem item : items.values()) {
            if (item.match(description)) {
                matchingItems.add(item);
            }
        }

        return matchingItems;
    }
        public List<RentableItem> findAvailableMatches(String description, LocalDate beginDate, LocalDate endDate) {
            List<RentableItem> matchingItems = searchItems(description);
            List<RentableItem> rentableItems = new ArrayList<>();

            for (RentableItem item : matchingItems) {
                if (isRentable(item, beginDate, endDate)) {
                    rentableItems.add(item);
                }
            }
            return rentableItems;
        }

        /* *************************************************************************************************************
         * The following methods allow to rent an item
         * *************************************************************************************************************/
        public boolean rentItem(RentableItem item, LocalDate beginDate, LocalDate endDate) {
            return rentItem(item, beginDate, endDate, computeCost(beginDate, endDate));
        }

    private double computeCost(LocalDate beginDate, LocalDate endDate) {
        Period period = Period.between(beginDate, endDate);
        int days = period.getDays();
        return defaultCostPerDay * days;
    }

    public boolean rentItem(RentableItem item, LocalDate beginDate, LocalDate endDate, double cost) {
            if (!isRentable(item, beginDate, endDate)) {
                return false;
            }
            Rental rental = new Rental(item, beginDate, endDate, cost);

             List<Rental> rentalsOnItem = rentalsByItem.get(item.getName());
             rentalsOnItem.add(rental);

             rentalsOnItem.sort(Comparator.comparing(Rental::getStartDate));

            return true;
        }

        // Only for testing purposes But I can't avoid public to test it in another package
        // Returns all the rentals in the rental system (in no particular order)
        public List<Rental> getRentals() {
            return rentalsByItem.values().stream().flatMap(List::stream).toList();
        }
}

