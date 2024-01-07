package mp3organizer.v2;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;

import java.io.File;
import java.util.*;

/**
 * A class to hold details of audio tracks.
 * This version can play the tracks.
 *
 * @author David J. Barnes and Michael Kölling
 * @author MBF
 * @version 2016.02.29
 */
public class MusicOrganizer {
    // An ArrayList for storing the tracks.
    private final List<Track> tracks;
    // A player for the music tracks.
    private final MusicPlayer musicPlayer;

    // A reader that can read music files and load them as tracks.
    private TrackReader reader ;

    private boolean playing = false;



    static final String NOT_PRESENT = "Not Found";

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer() {
        this(new TrackReader());
    }

    /**
     * Create a MusicOrganizer
     * Through the passage of the TrackReader we could manage different configurations.
     */
    public MusicOrganizer(TrackReader reader) {
        this(new MusicPlayer(), reader);
    }

    public MusicOrganizer(MusicPlayer musicPlayer) {
        this(musicPlayer, new TrackReader());
    }

    //To reuse our tests in v1
    public MusicOrganizer(MusicPlayer musicPlayer, List<String> files) {
        this(musicPlayer, new TrackReader(), files);
    }

    public MusicOrganizer(MusicPlayer musicPlayer, TrackReader reader) {
        this(musicPlayer, reader, new ArrayList<>());
    }
    public MusicOrganizer(MusicPlayer musicPlayer, TrackReader reader, List<String> files) {
        this.musicPlayer = musicPlayer;
        this.tracks = new ArrayList<>();
        this.reader = reader;
        List<String> errors = addTracks(files);
        if (! errors.isEmpty()) {
            throw new IllegalArgumentException("errors reading files" + errors);
        }
    }

    /**
     * Check that the given list of files is a list of music files.
     * It adds the files to the collection if they are music files
     * @param files
     * @return a String with the list of errors
     */
    public List<String> addTracks(List<String> files) {
        List<String> errors = new ArrayList<>();
        for (String filename : files) {
            try {
                checkMusicFile(filename);
                addFile(filename);
            } catch (IllegalArgumentException e) {
                errors.add(e.getMessage());
            }
        }
        return errors;
    }

    /**
     * Check that the given file is a music file.
     * It throws an exception if the file is not a music file.
     * @param filename
     */
    private void checkMusicFile(String filename) {
        if (filename == null || filename.equals("")) {
            throw new IllegalArgumentException("The file name " + filename + " is not valid.");
        }
        File musicFile = new File(filename);
        if (!musicFile.exists()) {
            throw new IllegalArgumentException("The file " + filename + " does not exist.");
        }
        if (!musicFile.isFile()) {
            throw new IllegalArgumentException("The file " + filename + " is not a file.");
        }
        if (!musicFile.canRead()) {
            throw new IllegalArgumentException("The file " + filename + " cannot be read.");
        }
        if (!filename.endsWith(".mp3")) {
            throw new IllegalArgumentException("The file " + filename + " is not a mp3 file.");
        }
    }


    /**
     * Remove a file from the collection.
     * @param i The index of the file to be removed from the end-user point of view.
     *          The index of the file to be removed from the implementation point of view is i-1.
     */
    public boolean removeTrack(int i) {
        if (isIndexValid(i-1)){
            tracks.remove(i-1);
            return true;
        }
        return false;
    }



    /**
     * Check that the given index is valid for the collection.
     * @param index The index to be checked from the implementation point of view.
     * @return true if the index is valid, false otherwise.
     */
    private boolean isIndexValid(int index) {
        // Set according to whether the index is valid or not.
        return index >= 0 && index < tracks.size();
    }


    /**
     * Play
     * @param i from end-user point of view
     * @throws NonAvailableTrackException
     */
    public void playTrack(int i) throws NonAvailableTrackException {
        if (isIndexValid(i-1)) {
            playAndWait(tracks.get(i-1));
        } else {
            nonValidIndex(i);
        }
    }





    /**
     * List the names of files matching the given match string.
     * @param searchString The string to match.
     */
    public List<String> listMatching(String searchString) {
        List<String> matchingFiles = new ArrayList<>();
        String[] filenames = listTracks();
        for (String filename : filenames) {
            if (filename.contains(searchString)) {
                // A match.
                matchingFiles.add(filename);
            }
        }
        return matchingFiles;
    }

    /**
     * List the names of files matching the given match string for the artist.
     * @param searchString
     * @return the list of the matching files
     * @todo improve the search
     */
    public List<String> listMatchingArtist(String searchString) {
        List<String> matchingFiles = new ArrayList<>();
        for (Track track : tracks) {
            if (track.getArtist().contains(searchString)) {
                // A match.
                matchingFiles.add(track.getFilename());
            }
        }
        return matchingFiles;
    }

    /**
     * List the names of files matching the given match string for the title.
     * @param searchString
     * @return the list of the matching files
     * @todo improve the search
     */
    public List<String> listMatchingTitle(String searchString) {
        List<String> matchingFiles = new ArrayList<>();
        for (Track track : tracks) {
            if (track.getTitle().contains(searchString)) {
                // A match.
                matchingFiles.add(track.getFilename());
            }
        }
        return matchingFiles;
    }














    /**
     * Play tracks whose name of tracks matches the given search string.
     * @param searchString The string to match against.
     *                     complexity : too bad using listMatching!
     * @return true if at least one track has been played, false otherwise.
     */
    public boolean playMatchingTracks(String searchString) throws NonAvailableTrackException {
        int index = findFirst(searchString);
        if (index == -1)
            return false;
        while (index < tracks.size()) {
            String filename = tracks.get(index).getFilename();
            if (filename.contains(searchString)) {
                // A match.
                this.playAndWait(index+1);
            }
            // Move on.
            index++;
        }
        return true;
    }



    public int getTrackNumber(String filename) {
        Track track = new Track(filename);
        return tracks.indexOf(track);
    }

    //Only for tests?
    protected Track getTrack(int index){
        return tracks.get(index);
    }


    /**
     * Add a file to the collection.
     * according to the trackReader
     * @param filename The file to be added.
     * @param trackReader
     * @throws IllegalArgumentException if the file does not exist or is not a file.
     */
    public Track addFile(String filename, TrackReader trackReader) {
        checkMusicFile(filename);
        Track track = trackReader.decodeDetails(filename);
        tracks.add(track);
        return track;
    }


    /**
     * Add a file to the collection.
     * @param filename The file name to be added.
     * @throws IllegalArgumentException if the file does not exist or is not a file.
     */
    public Track addFile(String filename) {
        return addFile(filename, reader);
    }







    /**
     * Return the number of tracks in the collection.
     *
     * @return The number of tracks in the collection.
     */
    int getNumberOfTracks() {
        return tracks.size();
    }





    /**
     * Start playing a track in the collection.
     * Use stopPlaying() to stop it playing.
     *
     * @param index The index of the file to be played.
     */
    public void startPlaying(int index) throws NonAvailableTrackException {
        if (isIndexValid(index-1)) {
            String filename = tracks.get(index-1).getFilename();
            musicPlayer.startPlaying(filename);
        } else {
            nonValidIndex(index);
        }
    }



    /**
     * Play a file in the collection. Only return once playing has finished.
     *
     * @param index The index of the file to be played.
     */
    public void playAndWait(int index) throws NonAvailableTrackException {
        if (isIndexValid(index-1)) {
            Track track = tracks.get(index - 1);
            //musicPlayer.playSample(filename);
            playAndWait(track);
        } else {
            nonValidIndex(index);
        }
    }

    private void playAndWait(Track track) throws NonAvailableTrackException {
        if (playing) {
            stopPlaying();
        }
        musicPlayer.playSample(track.getFilename());
        track.listen();
    }

    /**
     * On choisit de ne pas retourner que IndexOutOfBoundsException
     * pour masquer notre implementation
     * Nous choisissons de lever une exception dédiée.
     * @param index from the end-user point of view.
     *              The index of the file to be accessed from the implementation point of view is index-1.
     * @throws NonAvailableTrackException
     */
    private void nonValidIndex(int index) throws NonAvailableTrackException {
        throw new NonAvailableTrackException("track number is not valid :" + index + " should be in [1.. " + tracks.size() + "]");
    }

    /**
     * Stop the player.
     */
    void stopPlaying() {
        musicPlayer.stop();
        playing = false;
    }

    @Override
    public String toString() {
        return "MusicOrganizer{" +
                "tracks=" + tracks +
                '}';
    }

    /**
     * List of the tracks (filename) in the organizer
     */
    public String[] listTracks() {
        return tracks.stream().map(Track::getFilename).toArray(String[]::new);
    }

    public int numberOfFiles() {
        assert tracks != null;
        return tracks.size();
    }



    /**
     * List the names of tracks matching the given search string.
     * @param searchString The string to match.
     */
    List<String> listByArtist(String searchString) {
        List<String> matching = new ArrayList<>();
        for (Track track : tracks) {
            if (track.getArtist().contains(searchString)) {
                // A match.
                matching.add(track.getFilename());
            }
        }
        return matching;
    }






    /**
     * Find the index of the first file matching the given
     * match string.
     * @param searchString The string to match.
     * @return The index of the first occurrence, or -1 if
     *         no match is found.
     */
    int findFirst(String searchString) {
        int index = 0;
        // Record that we will be searching until a match is found.
        boolean stillLooking = true;

        while (stillLooking && index < tracks.size()) {
            String filename = tracks.get(index).getFilename();
            if (filename.contains(searchString)) {
                // A match. We can stop searching.
                stillLooking = false;
            } else {
                // Move on.
                index++;
            }
        }
        if (stillLooking) {
            // We didn't find it.
            return -1;
        } else {
            // Return where it was found.
            return index;
        }
    }


    private Random random = new Random();

    /**
     * randomly play one track
     * @return the index of the track played
     */
    public int randomPlaying() throws NonAvailableTrackException {
        int nexTrack = random.nextInt(0,tracks.size());
        assert nexTrack > -1;
        assert nexTrack <tracks.size();
        playAndWait(nexTrack);
        return nexTrack;
    }

    /**
     * randomly play tracks with more than numberOfListen listens
     * @param numberOfListen
     * @return the index of the tracks played
     */
    public int[] randomPlayingPreferedTracks(int numberOfListen) throws NonAvailableTrackException {
        List<Track> preferedTracks = extractPreferedTracks(numberOfListen);
        int[] playedTrack = new int[preferedTracks.size()];
        Collections.shuffle(preferedTracks);
        for (int i=0; i<preferedTracks.size();i++){
            playAndWait(preferedTracks.get(i));
            playedTrack[i] = tracks.indexOf(preferedTracks.get(i));
        }
        return playedTrack;
    }

    /**
     * We could use Lambda...
     * This method extract the tracks with more than numberOfListen listens
     * @param numberOfListen the number of listen to be considered as preferred
     * @return the list of the preferred Tracks
     */
    private List<Track> extractPreferedTracks(int numberOfListen) {
         List<Track> preferedTracks = new ArrayList<>();
         for (Track t : tracks){
             if (t.getNumberOflistens()>numberOfListen){
                preferedTracks.add(t);
             }
         }
         return preferedTracks;
    }

    public List<Track> extractPreferedTracks() {
        int iVal = (int) Math.round(median());
        return extractPreferedTracks(iVal);
    }


    void removeMatchingFiles(String searchString) {
        for (Iterator<Track> i = tracks.iterator(); i.hasNext();) {
            Track track = i.next();
            if (track.getFilename().contains(searchString) ) {
                i.remove();
            }
        }
    }

//-------------------
    //median-average listening preferences
    private double median() {
        List<Track> sorted = sortByListen();
        int size = sorted.size();
        int middle = size/2;
        if (size%2 == 1) {
            return sorted.get(middle).getNumberOflistens();
        } else {
            return (sorted.get(middle-1).getNumberOflistens()
                    + sorted.get(middle).getNumberOflistens() )/ 2.0;
        }
    }

    private double meanListening(){
        double sum = 0;
        for (Track track : tracks) {
            sum += track.getNumberOflistens();
        }
        return sum / tracks.size();
    }


    //---------------------------------- Sorting ------------------------------------
    /**
     * Sorting by artist, title, number of listens
     * @return a sorted list of tracks
     *
     */
    public List<Track>  sortByArtist(){
        return sortTracks(Comparator.comparing(Track::getArtist));
    }
    public List<Track>  sortByTitle(){
        return sortTracks(Comparator.comparing(Track::getTitle));
    }
    public List<Track>  sortByListen(){
        return sortTracks(Comparator.comparing(Track::getNumberOflistens));
    }

    private List<Track> sortTracks(Comparator<Track> comparator){
        List<Track> sortedList = new ArrayList<>(tracks);
        sortedList.sort(comparator);
        return sortedList;
    }
}
