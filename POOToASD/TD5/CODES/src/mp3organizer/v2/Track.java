package mp3organizer.v2;

import java.util.Objects;

/**
 * Store the details of a music track,
 * such as the artist, title, and file name.
 * 
 * @author David J. Barnes and Michael Kölling
 * @author Mireille Blay-Fornarino
 * @version 2016.02.29
 */
public class Track {
    // The artist.
    private  String artist;
    // The track's title.
    private  String title;
    // Where the track is stored.
    private  String filename;


    //Number of times the track has been listened to
    private int numberOflistens = 0;

    public int getNumberOflistens() {
        return numberOflistens;
    }

    protected void listen() {
        numberOflistens += 1;
    }


    /**
     * Constructor for objects of class Track.
     * @param artist The track's artist.
     * @param title The track's title.
     * @param filename The track file. 
     */
    Track(String artist, String title, String filename) {
        setDetails(artist, title, filename);
    }
    
    /**
     * Constructor for objects of class Track.
     * It is assumed that the file name cannot be
     * decoded to extract artist and title details.
     * @param filename The track file. 
     */
    Track(String filename) {
        this("unknown", "unknown", filename);
    }
    
    /**
     * Return the artist.
     * @return The artist.
     */
    String getArtist() {
        return artist;
    }
    
    /**
     * Return the title.
     * @return The title.
     */
    String getTitle() {
        return title;
    }
    
    /**
     * Return the file name.
     * @return The file name.
     */
    String getFilename() {
        return filename;
    }

    
    /**
     * Set details of the track.
     * @param artist The track's artist.
     * @param title The track's title.
     * @param filename The track file. 
     */
    private void setDetails(String artist, String title, String filename) {
        this.artist = artist;
        this.title = title;
        this.filename = filename;
    }


    //Two tracks are equals if they refer to the same file
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(filename, track.filename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filename);
    }

    @Override
    public String toString() {
        return "Track{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", filename='" + filename + '\'' +
                ", numberOflistens=" + numberOflistens +
                '}';
    }
}

