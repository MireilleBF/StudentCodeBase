package mp3player;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Provide basic playing of MP3 files via the javazoom library.
 * See http://www.javazoom.net/
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class MusicPlayer {
    // The current player. It might be null.
    private AdvancedPlayer player;

    /**
     * Play a part of the given file.
     * The method returns once it has finished playing.
     *
     * @param filename The file to be played.
     */
    public void playSample(String filename) throws NonAvailableTrackException {
        try {
            setupPlayer(filename);
            player.play(700);
        } catch (JavaLayerException e) {
            throw new NonAvailableTrackException("Error playing Sample " + filename, e);
        } finally {
            killPlayer();
        }
    }

    /**
     * Start playing the given audio file.
     * The method returns once the playing has been started.
     *
     * @param filename The file to be played.
     */
    public void startPlaying(final String filename) throws NonAvailableTrackException {
        setupPlayer(filename);
        try {
            Thread playerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        player.play(5000);
                    } catch (JavaLayerException e) {
                    } finally {
                        killPlayer();
                    }
                }
            };
            playerThread.start();
        } catch (Throwable e){ // catch any error, bad practice but we don't want to rewrite the whole code
            throw new NonAvailableTrackException("Error playing " + filename, e);
        }
    }


    public void stop() {
        killPlayer();
    }

    /**
     * Set up the player ready to play the given file.
     *
     * @param filename The name of the file to play.
     */
    private void setupPlayer(String filename) throws NonAvailableTrackException {
        try {
            InputStream is = getInputStream(filename);
            player = new AdvancedPlayer(is, createAudioDevice());
        } catch (IOException|JavaLayerException e) {
            killPlayer();
            throw new NonAvailableTrackException(filename, e);
        }
    }

    /**
     * Return an InputStream for the given file.
     *
     * @param filename The file to be opened.
     * @return An input stream for the file.
     * @throws IOException If the file cannot be opened.
     */
    private InputStream getInputStream(String filename)
            throws IOException {
        return new BufferedInputStream(
                new FileInputStream(filename));
    }

    /**
     * Create an audio device.
     *
     * @return An audio device.
     * @throws JavaLayerException if the device cannot be created.
     */
    private AudioDevice createAudioDevice()
            throws JavaLayerException {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    /**
     * Terminate the player, if there is one.
     */
    private void killPlayer() {
        synchronized (this) {
            if (player != null) {
                player.stop();
                player = null;
            }
        }
    }



}
