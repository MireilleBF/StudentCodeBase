package fr.epu.tracking;

public interface Trackable {
    /**
     * @return the position of the trackable
     * @throws PositionNotAvailableException if the position is not available
     */
    public Position getPosition() ; //throws PositionNotAvailableException;
}
