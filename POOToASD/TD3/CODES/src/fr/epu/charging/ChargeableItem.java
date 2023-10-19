package fr.epu.charging;

public interface ChargeableItem {
    double chargeToFull();

    boolean connect();

    void disconnect();

    boolean isConnected();
}
