package mp3player;

public class NonAvailableTrackException extends Exception {
    public NonAvailableTrackException(String filename, Throwable e) {
        super ("Error reading file " + filename, e);
    }
    public NonAvailableTrackException(String message) {
        super (message);
    }
}
