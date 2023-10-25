package fr.epu.charging;

public class EnergyProvider {
        private String providerName;
        private String energySource; // Ex: Solar, Wind, Grid, etc.

        public EnergyProvider(String providerName, String energySource) {
            this.providerName = providerName;
            this.energySource = energySource;
        }

        public String getProviderName() {
            return providerName;
        }
        public String getEnergySource() {
            return energySource;
        }
    }

