package fr.epu.rentals;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;


/**
 * The class which manages the rental system.
 */
public class RentalSystem<T extends RentableItem > {

    //tous les items à la location sont stockés dans une map
    //la clé est le nom de l'item
    //la valeur est l'item
    private Map<String,T> items;

    //les locations sont stockées dans une map avec comme clé le nom de l'item
    //et comme valeur une liste de locations pour cet item ordonnées par date de début
    private Map<String,List<Rental>> rentalsByItem;


    private double defaultCostPerDay;

    /* *************************************************************************************************************
        * The following methods allow to create RentalSystem objects
        * ************************************************************************************************************
     */
        public RentalSystem() {
            this(10);

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

        public void addItem(T item) {
            if (items.containsKey(item.getName())) {
                throw new IllegalArgumentException("An item with the same name already exists");
            }

            items.put(item.getName(), item);
            rentalsByItem.put(item.getName(), new ArrayList<>());
        }

        public void removeItem(T item) {
            items.remove(item.getName());
        }

        /* *************************************************************************************************************
         * The following methods allow to check if an item is rentable
         * ************************************************************************************************************
         */
        public boolean isRentable(T item, LocalDate beginDate, LocalDate endDate) throws NoSuchItemException {
            if (!item.isAvailable()) {
                return false;
            }
            if (! (items.containsKey(item.getName()) )) {
                throw new NoSuchItemException(item);
            }
            List<Rental> rentals = rentalsByItem.get(item.getName());

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
    public List<T> getItems() {
        return new ArrayList<>(items.values());
    }
    public List<T> searchItems(String description) {
        List<T> matchingItems = new ArrayList<>();

        for (T item : items.values()) {
            if (item.match(description)) {
                matchingItems.add(item);
            }
        }

        return matchingItems;
    }
        public List<T> findAvailableMatches(String description, LocalDate beginDate, LocalDate endDate) {
            List<T> matchingItems = searchItems(description);
            List<T> rentableItems = new ArrayList<>();
            try {
                for (T item : matchingItems) {
                    if (isRentable(item, beginDate, endDate)) {
                        rentableItems.add(item);
                    }
                }
                return rentableItems;
            } catch (NoSuchItemException e) {
                throw new RuntimeException("This should not happen",e);
            }
        }

        /* *************************************************************************************************************
         * The following methods allow to rent an item
         * *************************************************************************************************************/
        public boolean rentItem(T item, LocalDate beginDate, LocalDate endDate) throws NoSuchItemException {
            return rentItem(item, beginDate, endDate, computeCost(beginDate, endDate));
        }

    private double computeCost(LocalDate beginDate, LocalDate endDate) {
        Period period = Period.between(beginDate, endDate);
        int days = period.getDays();
        return defaultCostPerDay * days;
    }

    public boolean rentItem(T item, LocalDate beginDate, LocalDate endDate, double cost) throws NoSuchItemException {
            if (!isRentable(item, beginDate, endDate)) {
                return false;
            }
            Rental rental = new Rental(item, beginDate, endDate, cost);

             List<Rental> rentalsOnItem = rentalsByItem.get(item.getName());
             rentalsOnItem.add(rental);

             Collections.sort(rentalsOnItem, new Comparator<Rental>() {
                    @Override
                    public int compare(Rental o1, Rental o2) {
                        return o1.getStartDate().compareTo(o2.getStartDate());
                    }
                });
            return true;
        }

        // Only for testing purposes But I can't avoid public to test it in another package
        // Returns all the rentals in the rental system (in no particular order)
        public List<Rental> getRentals() {
            return rentalsByItem.values().stream().flatMap(List::stream).toList();
        }
}

