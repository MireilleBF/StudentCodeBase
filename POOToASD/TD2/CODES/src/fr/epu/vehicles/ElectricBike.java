package fr.epu.vehicles;

import java.util.Arrays;

public class ElectricBike extends ElectricVehicle {


    private int pedalAssistLevel;
    private double[] energyConsumptionLevels;

    public ElectricBike(double batteryCapacity, double[] energyConsumptionLevels) {
        super(batteryCapacity);
        this.energyConsumptionLevels = energyConsumptionLevels;
    }



    /**
     * This method is not public, because it is only used internally by the public method calculateMaxRange().
     * We keep it package-private, because it is only used by the ElectricBike class, and we want to test it.
     * @param level
     * @return the energy consumption for the given assist level
     */
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
    public String toString() {
        return "ElectricBike{" +
                super.toString() +
                "pedalAssistLevel=" + pedalAssistLevel +
                ", energyConsumptionLevels=" + Arrays.toString(energyConsumptionLevels) +
                "} " + super.toString();
    }

    /**
     * Testez votre code ici, mais ce n'est pas la meilleure façon de tester votre code.
     * Nous préférerions que vous utilisiez des tests unitaires qui vérifient que votre code fonctionne correctement,
     * sans avoir à le tester manuellement.
     * @param args
     */
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
