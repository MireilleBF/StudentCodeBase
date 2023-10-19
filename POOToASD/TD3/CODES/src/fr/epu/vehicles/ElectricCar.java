package fr.epu.vehicles;

import fr.epu.rentals.RentableItem;

public class ElectricCar extends ElectricVehicle implements RentableItem {

    //Impact of COOLING SYSTEM on energy consumption
    protected static final double COOLING_SYSTEM_FACTOR = 1.2;
    private boolean coolingSystemActive;

    private String licensePlate;
    private String model;

    private boolean isAvailable;


    /* ********** */
    /* CONSTRUCTOR */
    /* ********** */

    public ElectricCar(double batteryCapacity, String licensePlate) {
        this(batteryCapacity, licensePlate, "Not defined");
    }

    public ElectricCar(double batteryCapacity, String licensePlate, String model) {
        super(batteryCapacity);
        this.model = model;
        this.coolingSystemActive = false;
        this.licensePlate = licensePlate;
        isAvailable = true;
    }

    /* ********** */
    /* Accessors    */
    /* ********** */
    public void turnOnCoolingSystem() {
        coolingSystemActive = true;
    }

    public void turnOffCoolingSystem() {
        coolingSystemActive = false;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isOnCoolingSystem() {
        return coolingSystemActive;
    }


    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getModel() {
        return model;
    }

    /*********** */
    /* Methods   */
    /*********** */

    /**
     * Returns the energy consumption per kilometer of the vehicle (in kilowatt-hours per kilometer).
     * If the cooling system is active, the energy consumption is increased by 20%.
     * @return the energy consumption per kilometer of the vehicle (in kilowatt-hours per kilometer)
     */
    @Override
    public double getEnergyConsumptionPerKilometer() {
        double currentEnergyConsumption = super.getEnergyConsumptionPerKilometer();
        if (coolingSystemActive) {
            currentEnergyConsumption = currentEnergyConsumption*COOLING_SYSTEM_FACTOR; // Exemple d'augmentation de la consommation avec le refroidissement.
        }
        return currentEnergyConsumption;
    }


   /* ********** */
    /* RentableItem    */
    /* ********** */
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String getName() {
        return getLicensePlate();
    }

    @Override
    public boolean match(String description) {
        return getModel().contains(description) || getLicensePlate().contains(description);
    }


}
