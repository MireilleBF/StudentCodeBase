package fr.epu.vehicles;

public class ElectricCar extends ElectricVehicle {

    //Impact of COOLING SYSTEM on energy consumption
    protected static final double COOLING_SYSTEM_FACTOR = 1.2;
    private boolean coolingSystemActive;

    private String licensePlate;



    private String model;


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





    // Ajoutez une méthode pour calculer la consommation d'énergie en fonction de la vitesse.
    /* public double calculateEnergyConsumption(double speed) {
        // Plus la vitesse est élevée, plus la consommation d'énergie augmente.
        // Vous pouvez utiliser une formule qui modélise cette relation.
        double baseConsumption = getEnergyConsumptionPerKilometer();
        double speedFactor = 1.0 + (speed / 100.0); // Exemple de facteur de vitesse
        return baseConsumption * speedFactor;
    }
     */

}
