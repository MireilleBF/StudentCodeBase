package fr.epu.fleets;

import fr.epu.vehicles.ElectricVehicle;

public class VehicleService {

    private ElectricVehicle[] availableVehicles;
    private int availableVehiclesCount;

    private ElectricVehicle[] vehiclesInRepair;
    private int electricVehicleInRepairCount;

    public VehicleService(int maxSize){
        this.availableVehicles = new ElectricVehicle[maxSize];
        this.vehiclesInRepair = new ElectricVehicle[maxSize];
    }

    public void addAvailableVehicle(ElectricVehicle vehicle){
        if(availableVehiclesCount < availableVehicles.length){
            availableVehicles[availableVehiclesCount] = vehicle;
            availableVehiclesCount++;
        }
    }

    public void addElectricVehicleInRepair(ElectricVehicle vehicle){
        if(electricVehicleInRepairCount < vehiclesInRepair.length){
            vehiclesInRepair[electricVehicleInRepairCount] = vehicle;
            electricVehicleInRepairCount++;
        }
    }

    public void moveVehicleToRepair(ElectricVehicle vehicle){
        for(int i = 0; i < availableVehiclesCount; i++){
            if(availableVehicles[i] == vehicle){
                availableVehicles[i] = null;
                swapVehicle(availableVehicles, i, availableVehiclesCount - 1);
                availableVehiclesCount--;
                addElectricVehicleInRepair(vehicle);
            }
        }
    }

    public void moveVehicleToAvailable(ElectricVehicle vehicle){
        for(int i = 0; i < electricVehicleInRepairCount; i++){
            if(vehiclesInRepair[i] == vehicle){
                vehiclesInRepair[i] = null;
                swapVehicle(vehiclesInRepair, i, electricVehicleInRepairCount - 1);
                electricVehicleInRepairCount--;
                addAvailableVehicle(vehicle);
            }
        }
    }

    private void swapVehicle(ElectricVehicle[] vehicles, int i, int i1) {
        ElectricVehicle temp = vehicles[i];
        vehicles[i] = vehicles[i1];
        vehicles[i1] = temp;
    }


    public VehicleService(ElectricVehicle[] availableVehicles, ElectricVehicle[] vehiclesInRepair) {
        this.availableVehicles = availableVehicles;
        this.vehiclesInRepair = vehiclesInRepair;
    }


    //Je voudrais le vehicule disponible qui a la plus grande autonomie possible avec sa charge de batterie actuelle
    //Return the vehicle
    public ElectricVehicle findAvailableVehicleWithMaxDistance() {
        ElectricVehicle vehicleWithMaxRange = null;
        double maxRange = 0;
        for (int i = 0; i < availableVehiclesCount; i++) {
            double localMaxRange = availableVehicles[i].calculateMaxDistance();
            if (localMaxRange > maxRange) {
                maxRange = localMaxRange;
                vehicleWithMaxRange = availableVehicles[i];
            }
        }
        return vehicleWithMaxRange;
    }

    //Je voudrais le vehicule qui a la plus grande autonomie possible en fonction de la capacité de sa batterie
    //De préférence disponible
    public ElectricVehicle findVehicleWithMaxRangeInAvailableAndInRepair() {
        ElectricVehicle vehicleWithMaxRange = null;
        double maxRange = 0;
        for(int i = 0; i < availableVehiclesCount; i++){
            double localMaxRange = availableVehicles[i].calculateMaxRange();
            if(localMaxRange > maxRange){
                maxRange = localMaxRange;
                vehicleWithMaxRange = availableVehicles[i];
            }
        }
        for(int i = 0; i < electricVehicleInRepairCount; i++){
            double localMaxRange = vehiclesInRepair[i].calculateMaxRange();
            if(localMaxRange > maxRange){
                maxRange = localMaxRange;
                vehicleWithMaxRange = vehiclesInRepair[i];
            }
        }
        return vehicleWithMaxRange;
    }

    public int getNbOfVehiclesInRepair() {
        return electricVehicleInRepairCount;
    }

    public int getNbOfAvailableVehiclesInCharge() {
        int nbOfVehiclesInCharge = 0;
        for (int i = 0; i < availableVehiclesCount; i++) {
            if (availableVehicles[i].isConnected()) {
                nbOfVehiclesInCharge++;
            }
        }
        return nbOfVehiclesInCharge;

    }

    public int getNbOfAvailableVehicles() {
        return availableVehiclesCount;
    }
}
