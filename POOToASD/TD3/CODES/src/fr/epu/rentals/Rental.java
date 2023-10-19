package fr.epu.rentals;

    import java.time.LocalDate;



/**
 * A rental is a contract
 * we only register the start and end dates of the rental; the cost and the rented item
 * In a real system, we would also register the customer who rents the item
 *
  */
public class Rental {
        private RentableItem item; // L'objet loué
        private LocalDate startDate; // Date de début de la location
        private LocalDate endDate; // Date de fin de la location
        private double cost; // Coût de la location

        public Rental(RentableItem item, LocalDate startDate, LocalDate endDate, double cost) {
            this.item = item;
            this.startDate = startDate;
            this.endDate = endDate;
            this.cost = cost;
        }

        // Méthodes pour accéder aux informations de la location

        public RentableItem getItem() {
            return item;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "Location de " + item.getName() + " du " + startDate + " au " + endDate + " pour un coût de " + cost + " €.";
        }
    }

