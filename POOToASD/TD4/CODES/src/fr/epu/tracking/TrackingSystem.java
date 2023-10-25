package fr.epu.tracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TrackingSystem {
        private List<Trackable> trackedObjects;

        public TrackingSystem() {
            trackedObjects = new ArrayList<>();
        }

        // Méthode pour ajouter un objet trackable au système de suivi
        public void addTrackableObject(Trackable object) {
            trackedObjects.add(object);
        }

        // Méthode pour obtenir la position actuelle d'un objet trackable par son indice dans la liste
        public Optional<Position> getTrackableObjectPosition(int index) { //throws PositionNotAvailableException {
            if (index >= 0 && index < trackedObjects.size()) {
                Trackable object = trackedObjects.get(index);
                return Optional.of(object.getPosition());
            }
            return Optional.empty(); // Si l'indice est hors limites ou si l'objet n'a pas de position.
        }

        // Méthode pour obtenir la position actuelle de tous les objets trackables dans le système
        public List<Position> getAllTrackablePositions() {
            List<Position> positions = new ArrayList<>();
            for (Trackable object : trackedObjects) {
                Position position = object.getPosition();
                if (position != null) {
                    positions.add(position);
                }
            }
            return positions;
        }

        public int getNumberOfTrackedObjects() {
            return trackedObjects.size();
        }

    }

