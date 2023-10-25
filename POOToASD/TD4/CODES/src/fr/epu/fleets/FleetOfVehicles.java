package fr.epu.fleets;

import fr.epu.vehicles.ElectricCar;
import fr.epu.vehicles.ElectricVehicle;

import java.util.ArrayList;
import java.util.List;

public class FleetOfVehicles<T extends ElectricVehicle>  extends ArrayList<T> {

    public FleetOfVehicles() {
        super();
    }

    public FleetOfVehicles(int initialCapacity) {
        super(initialCapacity);
    }

    //Question Part IV : Calculer l'autonomie maximum totale de la flotte sur leur configuration courante,
    public double calculateTotalMaxRange() {
        double totalMaxRange = 0;
        for (T vehicle : this) {
            totalMaxRange += vehicle.calculateMaxRange();
        }
        return totalMaxRange;
    }
    //Question Part IV : Calculer l'autonomie moyenne des véhicules de la flotte
    public double calculateAverageMaxRange() {
        if (size() == 0) {
            return 0;
        }
        double totalMaxRange = calculateTotalMaxRange();
        return totalMaxRange / size();
    }

    //Question Part IV : Calculer la distance la plus petite que peut parcourir un véhicule de la flotte à pleine charge

    public double calculateMinDistance() {
        if (size() == 0) {
            return 0;
        }
        double minDistance = Double.MAX_VALUE;
        for (T vehicle : this) {
            double maxDistance = vehicle.calculateMaxRange();
            if (maxDistance < minDistance) {
                minDistance = maxDistance;
            }
        }
        return minDistance;
    }

    //Question Part IV : Calculer la distance la plus grande que peut parcourir un véhicule de la flotte à pleine charge
    public double calculateMaxDistance() {
        double maxDistance = 0;
        for (T vehicle : this) {
            double localMaxDistance = vehicle.calculateMaxRange();
            if (localMaxDistance > maxDistance) {
                maxDistance = localMaxDistance;
            }
        }
        return maxDistance;
    }

    //Question Part IV : Recharger tous les vehicules à fond
    public void chargeFullAllVehicles() {
        for (T vehicle : this) {
            vehicle.chargeToFull();
        }
    }
    //Question Part IV : Recharger les véhicules dont la batterie est chargée à moins d'un pourcentage donné
    //charge vehicles whose battery is below a given percentage
    public void chargeVehiclesBelowPercentage(double percentage) {
        for (T vehicle : this) {
            if (vehicle.chargePercentage()<percentage) {
                vehicle.chargeToFull();
            }
        }
    }
    //Question Part IV : Conduire certains véhicules de la flotte sur une certaine distance et retourner le nombre de kilomètres parcourus.
    public double driveVehicles(double distance) {
        double totalDistance = 0;
        for (T vehicle : this) {
            if (vehicle.drive(distance)) {
                totalDistance += distance;
            }
        }
        return totalDistance;
    }

    //Question to prepare genericity
    //Quel vehicule a la plus grande autonomie
    public T findVehicleWithMaxRange() {
        T vehicleWithMaxRange = null;
        double maxRange = 0;
        for (T vehicle : this) {
            double localMaxRange = vehicle.calculateMaxRange();
            if (localMaxRange > maxRange) {
                maxRange = localMaxRange;
                vehicleWithMaxRange = vehicle;
            }
        }
        return vehicleWithMaxRange;
    }



    //Mais si on veut faire pareil mais en mettant le système de climatisation en mode On
    // on a besoin de savoir si c'est une voiture
    //Ce code montre que c'est bien plus compliqué sans genericité
    public T findVehicleWithMaxRangeInAnySituation() {
        List<T> vehiclesWithMaxRange = new ArrayList<>();
        double maxRange = 0;
        for (T vehicle : this) {
            double localMaxRange = vehicle.calculateMaxRange();
            if (localMaxRange > maxRange) {
                maxRange = localMaxRange;
                vehiclesWithMaxRange = new ArrayList<>();
                vehiclesWithMaxRange.add(vehicle);
            } else if (localMaxRange == maxRange) {
                vehiclesWithMaxRange.add(vehicle);
            }
        }
        //Too bad, we have to iterate again
        T vehicleWithMaxRange = null;
        maxRange = 0;
        for (T vehicle : vehiclesWithMaxRange) {
            //Oh No !! We have to cast to ElectricCar to turn on the cooling system
            //Only to remember the probleme ==> to remove
            if (vehicle instanceof ElectricCar electricCar) {
                electricCar.turnOnCoolingSystem();
                double localMaxRange = vehicle.calculateMaxRange();
                if (localMaxRange > maxRange) {
                    maxRange = localMaxRange;
                    vehicleWithMaxRange = vehicle;
                }
                ((ElectricCar)vehicle).turnOffCoolingSystem();
            }
        }
        return vehicleWithMaxRange;
    }

}
