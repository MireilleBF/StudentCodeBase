package mp3organizer.v1;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to hold details of audio files.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 * @modified by Mireille Blay-Fornarino
 */

public class MusicOrganizer {
    private MusicPlayer musicPlayer;

    // An ArrayList for storing the file names of music files.
    private final List<String> files;



    /** ********************************************
     * Create a MusicOrganizer
     ********************************************* */
    public MusicOrganizer() {
        this(new MusicPlayer());
    }
    public MusicOrganizer(MusicPlayer musicPlayer) {
        this(musicPlayer, new ArrayList<>());
    }
    public MusicOrganizer(MusicPlayer musicPlayer, List<String> files) {
        this.musicPlayer = musicPlayer;
        this.files = new ArrayList<>();
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
                 this.files.add(filename);
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
     * Add a file to the collection.
     * @param filename The file name to be added.
     * @throws IllegalArgumentException if the file does not exist or is not a file.
     */
    public void addFile(String filename) {
        checkMusicFile(filename);
        files.add(filename);
    }


    /**
     * Remove a file from the collection.
     * @param i The index of the file to be removed from the end-user point of view.
     *          The index of the file to be removed from the implementation point of view is i-1.
     */
    public boolean removeTrack(int i) {
        if (isIndexValid(i-1)){
            files.remove(i-1);
            return true;
        }
        return false;
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
        throw new NonAvailableTrackException("track number is not valid :" + index + " should be in [1.. " + files.size() + "]");

    }

    /**
     * Check that the given index is valid for the collection.
     * @param index The index to be checked from the implementation point of view.
     * @return true if the index is valid, false otherwise.
     */
    private boolean isIndexValid(int index) {
        // Set according to whether the index is valid or not.
        return index >= 0 && index < files.size();
    }

    /**
     * Play
     * @param i from end-user point of view
     * @throws NonAvailableTrackException
     */
    public void playTrack(int i) throws NonAvailableTrackException {
        if (isIndexValid(i-1)) {
            musicPlayer.startPlaying(listTracks()[i-1]);
        } else {
            nonValidIndex(i);
        }
    }

    /**
     * List of the tracks (filename) in the organizer
     */
    public String[] listTracks() {
        return files.toArray(new String[0]);
    }

        /**
         * Return the number of tracks in the organizer.
         * @return The number of tracks in the organizer.
         */
        public int getNumberOfTracks() {
            return files.size();
        }


        /**
         * List the names of files matching the given match string.
         * @param searchString The string to match.
         */
        public List<String> listMatching(String searchString) {
            List<String> matchingFiles = new ArrayList<>();
            for (String filename : files) {
                if (filename.contains(searchString)) {
                    // A match.
                    matchingFiles.add(filename);
                }
            }
            return matchingFiles;
        }


        /**
         * Find the index of the first file matching the given
         * match string.
         * @param searchString The string to match.
         * @return The index of the first occurrence, or -1 if
         *         no match is found.
         */
        private int findFirst(String searchString) {
            int index = 0;
            // Record that we will be searching until a match is found.
            boolean stillLooking = true;

            while (stillLooking && index < files.size()) {
                String filename = files.get(index);
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



        /**
         * Stop the player.
         */
        public void stopPlaying() {
            musicPlayer.stop();
        }



    /**
     * Start playing a file in the collection.
     * Use stopPlaying() to stop it playing.
     *
     * @param index The index of the file to be played.
     */
    public void startPlaying(int index) throws NonAvailableTrackException {
        if (isIndexValid(index-1)) {
            String filename = files.get(index-1);
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
            String filename = files.get(index-1);
            musicPlayer.playSample(filename);
        } else {
            nonValidIndex(index);
        }
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
        while (index < files.size()) {
            String filename = files.get(index);
            if (filename.contains(searchString)) {
                // A match.
                this.playAndWait(index+1);
            }
            // Move on.
            index++;
        }
        return true;
    }

    public int getTrackNumber(String track) {
        return files.indexOf(track);
    }
}

