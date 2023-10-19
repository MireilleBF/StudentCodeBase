package fr.epu.rentals;


/**
 * Interface which defines the methods that a rentable item must implement.
 */
public interface RentableItem {

    /**
     * An item is available if it can be rented
     * For example, a damaged item is not available
     * @return true if the item is available, false otherwise
     */
    boolean isAvailable();

    /**
     * The  name of the item (for example, "Bike 1")
     * @return the name of the item
     */
    String getName();

    /**
     * The description of the item (for example, "A red bike") which can be used to search for items
     * @param description the description to match
     * @return true if the item matches the description, false otherwise
     */
    boolean match(String description);
}