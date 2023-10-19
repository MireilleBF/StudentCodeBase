package fr.epu.vehicles;

import fr.epu.rentals.RentableItem;

import java.util.Arrays;

public class ElectricBike extends ElectricVehicle implements RentableItem {

    private int pedalAssistLevel;
    private double[] energyConsumptionLevels;


    private boolean isAvailable;

    private static final  String PREFIX = "EB-";


    private static int nextIdentifier = 1;

    /**
     * Resets the identifier counter to 1.
     * This method is useful for testing.
     */
     protected static void resetIdentifier() {
        nextIdentifier = 1;
    }

    private String identifier;

    /* ********** */
    /* CONSTRUCTOR */
    /* ********** */
    public ElectricBike(double batteryCapacity, double[] energyConsumptionLevels) {
        super(batteryCapacity);
        this.energyConsumptionLevels = energyConsumptionLevels;
        this.identifier = PREFIX + nextIdentifier;
        nextIdentifier++;
        isAvailable = true;
    }

    /* ********** */
    /* Accessors    */
    /* ********** */
    public int getPedalAssistLevel() {
        return pedalAssistLevel;
    }

    public void setPedalAssistLevel(int pedalAssistLevel) {
        if (pedalAssistLevel < 0) {
            pedalAssistLevel = 0;
        } else if (pedalAssistLevel >= energyConsumptionLevels.length) {
            pedalAssistLevel = energyConsumptionLevels.length - 1;
        }
        this.pedalAssistLevel = pedalAssistLevel;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /* ********** */
    /* Methods   */
    /* ********** */

    @Override
    public double calculateMaxRange() {
        // Calculez l'autonomie maximale en fonction de la capacité de la batterie
        // et du niveau d'assistance au pédalage.
        double energyConsumptionPerKilometer = getEnergyConsumptionForAssistLevel(pedalAssistLevel);
        return  (getBatteryCapacity() / energyConsumptionPerKilometer);
    }

    // Ajoutez d'autres méthodes ou attributs spécifiques aux vélos électriques ici

    double getEnergyConsumptionForAssistLevel(int level) {
        // Obtenez la consommation d'énergie en fonction du niveau d'assistance.
        // Utilisez le tableau energyConsumptionLevels pour associer les niveaux d'assistance à la consommation d'énergie.
        // Assurez-vous que le tableau a suffisamment d'éléments pour tous les niveaux d'assistance possibles.
        if (level >= 0 && level < energyConsumptionLevels.length) {
            return energyConsumptionLevels[level];
        } else {
            // Gestion d'erreur : retournez une valeur par défaut si le niveau d'assistance est hors limites.
            return 0.1; // Exemple de consommation de base (en kilowatt-heures par kilomètre)
        }
    }

    @Override
    public double getEnergyConsumptionPerKilometer() {
        return getEnergyConsumptionForAssistLevel(pedalAssistLevel);
    }

    public int getNumberOfAvailableLevels() {
        return energyConsumptionLevels.length;
    }

    @Override
    public String toString() {
        return "ElectricBike{" +
                super.toString() +
                "pedalAssistLevel=" + pedalAssistLevel +
                ", energyConsumptionLevels=" + Arrays.toString(energyConsumptionLevels) +
                "} " + super.toString();
    }
    @Override
    public String getName() {
        return identifier;
    }

    /**
     * Returns true if the description contains the identifier or the description contains the number of energy consumption levels.
     * @param description
     * @return true if the description contains the identifier or the description contains the number of energy consumption levels.
     */
    @Override
    public boolean match(String description) {
        return description.contains(identifier) ||
                description.contains(String.valueOf(this.energyConsumptionLevels.length ) ) ||
                description.contains("*");
    }

    public static void main(String[] args) {
        double[] energyConsumptionLevels = {0.1, 0.2, 0.3, 0.4};
        ElectricBike bike = new ElectricBike(10, energyConsumptionLevels);
        bike.pedalAssistLevel = 2;
        System.out.println(bike.calculateMaxRange());
        bike.pedalAssistLevel = 5;
        System.out.println(bike.calculateMaxRange());
        bike.pedalAssistLevel = 0;
        System.out.println(bike.calculateMaxRange());
    }
}
