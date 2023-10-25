package fr.epu.fleets;

import fr.epu.tracking.Position;
import fr.epu.tracking.TrackingSystem;
import fr.epu.vehicles.Drone;

import java.util.List;
import java.util.Optional;

public class DroneFleet extends FleetOfVehicles<Drone> {
    public DroneFleet(int maxSize) {
        super(maxSize);
    }

    TrackingSystem trackingSystem = new TrackingSystem();

    public void addDrone(Drone drone) {
        super.add(drone);
        trackingSystem.addTrackableObject(drone);
    }

    //Délégation : DroneFleet délègue à FleetOfVehicles
    public List<Position> getAllDronePositions() {
        return trackingSystem.getAllTrackablePositions();

    }
    //Trouver la position
    //EN PLUS PAS ICI...
    //ZUT RANGE au lieu de distance ... faut changer ca na pas de sens et meme le faire avec le min pour le récuperer...
    public Optional<Position> findPositionOfDroneWithLongestDistance(){
        Drone drone = findVehicleWithMaxRange();
        if (drone == null) {
            return Optional.empty();
        }
        else return Optional.of(drone.getPosition());
    }

    //Faire décoller tous les drones qui ont la capacité de voler plus loin que la distance donnée (sans retour)
    //Retourne le nombre de drones qui ont décollé
    public int takeOffDronesWithRange(double range) {
        int count = 0;
        for (Drone drone : this) {
            if (drone.calculateMaxDistance() > range) {
                drone.takeOff();
                count++;
            }
        }
        return count;
    }


    //Faire revenir les drones qui sont loin et n'ont plus assez de batterie pour parcourir la distance donnée
    //On suppose qu'on a mis une distance suffisante pour qu'ils puissent rentrer !
    public void returnDronesWithLowBattery(double returnDistance) {
        for (Drone drone : this) {
            if (drone.calculateMaxDistance() < returnDistance) {
                drone.returnToBase();
            }
        }

    }

    //Implémenter un décorateur qui fait revenir le drone des que la distance qu'il peut encore parcourir
    // est inférieure à la distance donnée (GoHomeDroneDecorator)


}
