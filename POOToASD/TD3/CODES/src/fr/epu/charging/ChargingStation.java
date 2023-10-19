package fr.epu.charging;

public class ChargingStation {
        private String stationName;
        private int availableChargingPoints;

        private EnergyProvider energyProvider;

        public ChargingStation(EnergyProvider energyProvider,String stationName, int availableChargingPoints) {
            this.energyProvider = energyProvider;
            this.stationName = stationName;
            this.availableChargingPoints = availableChargingPoints;
        }
        public ChargingStation(String stationName, int availableChargingPoints) {
            this(new EnergyProvider("EDF","Solar"), stationName, availableChargingPoints);
        }
        // Getters and setters for attributes
        public String getStationName() {
            return stationName;
        }
        public int getAvailableChargingPoints() {
            return availableChargingPoints;
        }

    /**
     * Connects a vehicle to a charging point and charges it to full.
     * @param vehicle the vehicle to charge
     * @return the amount of energy charged to the vehicle
     */
    public double connectToChargingPoint(ChargeableItem vehicle) {
        double charge = 0;
        if ( (availableChargingPoints > 0) && (vehicle.connect()) ) {
                availableChargingPoints--;
                charge = vehicle.chargeToFull();
            }
        return charge;
    }

    /**
     * Disconnects a vehicle from a charging point.
     */
    public void disconnectFromChargingPoint(ChargeableItem vehicle) {
    	availableChargingPoints++;
        vehicle.disconnect();
    }

    public EnergyProvider getEnergyProvider() {
        return energyProvider;
    }
}

