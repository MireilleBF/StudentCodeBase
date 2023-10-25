package fr.epu.rentals;

public class NoSuchItemException extends Exception {
    public  NoSuchItemException(RentableItem item) {
        super(item.getName() + " is not available for rental");
    }
}
