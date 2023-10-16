package fr.epu.fleets;

import fr.epu.vehicles.ElectricVehicle;

public class VehicleService {

    private ElectricVehicle[] availableVehicles;
    private int availableVehiclesCount;

    private ElectricVehicle[] vehiclesInRepair;
    private int vehicleInRepairCount;

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

    public void addVehicleInRepair(ElectricVehicle vehicle){
        if(vehicleInRepairCount < vehiclesInRepair.length){
            vehiclesInRepair[vehicleInRepairCount] = vehicle;
            vehicleInRepairCount++;
        }
    }

    public void moveVehicleToRepair(ElectricVehicle vehicle){
        for(int i = 0; i < availableVehiclesCount; i++){
            if(availableVehicles[i] == vehicle){
                availableVehicles[i] = null;
                swapVehicles(availableVehicles, i, availableVehiclesCount - 1);
                availableVehiclesCount--;
                addVehicleInRepair(vehicle);
            }
        }
    }

    public void moveVehicleToAvailable(ElectricVehicle vehicle){
        for(int i = 0; i < vehicleInRepairCount; i++){
            if(vehiclesInRepair[i] == vehicle){
                vehiclesInRepair[i] = null;
                swapVehicles(vehiclesInRepair, i, vehicleInRepairCount - 1);
                vehicleInRepairCount--;
                addAvailableVehicle(vehicle);
            }
        }
    }

    private void swapVehicles(ElectricVehicle[] vehicles, int i, int i1) {
        ElectricVehicle temp = vehicles[i];
        vehicles[i] = vehicles[i1];
        vehicles[i1] = temp;
    }


    public VehicleService(ElectricVehicle[] availableVehicles, ElectricVehicle[] electricVehicles) {
        this.availableVehicles = availableVehicles;
        this.vehiclesInRepair = electricVehicles;
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
        for(int i = 0; i < vehicleInRepairCount; i++){
            double localMaxRange = vehiclesInRepair[i].calculateMaxRange();
            if(localMaxRange > maxRange){
                maxRange = localMaxRange;
                vehicleWithMaxRange = vehiclesInRepair[i];
            }
        }
        return vehicleWithMaxRange;
    }

    public int getNbOfVehiclesInRepair() {
        return vehicleInRepairCount;
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
