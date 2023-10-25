package fr.epu.vehicles;


import fr.epu.charging.ChargeableItem;

/**
 * A vehicle that runs on electricity.
 * The battery can be charged with a given amount of energy.
 * The vehicle can drive a given distance, consuming energy.
 * The vehicle can report its current charge level.
 * A vehicle has a default energy consumption per kilometer (in kilowatt-hours per kilometer).
 * At creation, the batteryCapacity must be given.
 * At creation, the defaultEnergyConsumptionPerKilometer can be given.
 * The default value of defaultEnergyConsumptionPerKilometer is 0.2 kilowatt-hours per kilometer.
 * The battery is initially not charged.
 */
public class ElectricVehicle implements ChargeableItem {


    private double batteryCapacity;  // Capacity of the battery in kilowatt-hours
    private double currentCharge;   // Current charge level of the battery

    public double getBatteryCapacity() {
        return batteryCapacity;
    }
        // Default energy consumption per kilometer (in kilowatt-hours per kilometer)
       private static final double DEFAULT_ENERGY_CONSUMPTION_PER_KILOMETER = 0.2;
       private double defaultEnergyConsumptionPerKilometer = DEFAULT_ENERGY_CONSUMPTION_PER_KILOMETER;


        public ElectricVehicle(double batteryCapacity) {
           this(batteryCapacity, DEFAULT_ENERGY_CONSUMPTION_PER_KILOMETER);
        }
        public ElectricVehicle(double batteryCapacity, double energyConsumptionPerKilometer) {
            this.batteryCapacity = batteryCapacity;
            this.currentCharge = 0.0;  // Initially not charged
            this.defaultEnergyConsumptionPerKilometer = energyConsumptionPerKilometer;
        }


    public double getCurrentCharge() {
        return currentCharge;
    }
    /**
     * Charge the battery with the given amount of energy.
     * @param chargeAmount
     * @return true if the battery was charged, false otherwise
     */
    public boolean charge(double chargeAmount) {
            boolean success = false;
            if (chargeAmount <= 0 ) {
                throw new IllegalArgumentException("Charge amount must be positive");
            }
            if (currentCharge + chargeAmount <= batteryCapacity) {
                currentCharge += chargeAmount;
                success = true;
            } else {
                success = false;
            }
            return success;
    }


        protected double getEnergyConsumptionPerKilometer() {
            // Calculate and return the energy consumption per kilometer
            // based on the vehicle's characteristics.
            // This is just a placeholder method.
            return defaultEnergyConsumptionPerKilometer;  // Example value (in kilowatt-hours per kilometer)
        }

        public double chargePercentage() {
        	return currentCharge / batteryCapacity * 100;
        }

    /**
     * Charge the battery to full capacity.
     * @return the amount of energy that was added to the battery
     */
    @Override
    public double chargeToFull() {
        double chargeAmount = batteryCapacity - currentCharge;
        charge(chargeAmount);
        return chargeAmount;
    }

    /*************************************************************************
     * The following methods are for the exercise in TD1 (part V).
     *************************************************************************/
    private boolean isConnected = false;

    @Override
    public boolean connect() {
        if (isConnected) {
            return false;
        }
        isConnected = true;
        return true;
    }

    @Override
    public void disconnect() {
        isConnected = false;
    }

    @Override
    public boolean isConnected() {
        return isConnected;
    }

/* ***********************************************************************
    * The following methods are for the exercise in TD1 (part VIII).
 ************************************************************************* */

    /**
     * Calcule la distance maximum que le véhicule peut parcourir avec la charge actuelle.
     * @return la distance maximum que le véhicule peut parcourir avec la charge actuelle
     */
    public double calculateMaxDistance(){
        return currentCharge / getEnergyConsumptionPerKilometer();
    }

    /**
     * Calcule la distance maximum que le véhicule peut parcourir en fonction de la capacité de la batterie et de la consommation d'énergie urbaine
     * @return la distance maximum que le véhicule pourra parcourir en pleine charge
     */
    public double calculateMaxRange(){
        return getBatteryCapacity() / getEnergyConsumptionPerKilometer();
    }


    /**
     * TD1 - Part VIII
     * Drive the vehicle for the given distance.
     * The vehicle consumes energy during the drive.
     *
     * @param distance the distance to drive
     * @return true if the vehicle was able to drive the distance, false otherwise
     * if the vehicle was able to drive the distance, the current charge level is updated
     * if the vehicle was not able to drive the distance, the current charge level is not updated
     */
    public boolean drive(double distance) {
        double energyConsumed = distance * getEnergyConsumptionPerKilometer();
        if (energyConsumed <= currentCharge) {
            currentCharge -= energyConsumed;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "ElectricVehicle{" +
                "batteryCapacity=" + batteryCapacity +
                ", currentCharge=" + currentCharge +
                ", defaultEnergyConsumptionPerKilometer=" + defaultEnergyConsumptionPerKilometer +
                '}';
    }
}

