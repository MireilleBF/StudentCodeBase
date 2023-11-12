package mp3player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicPlayerTest {

    final String filename = System.getProperty("user.dir") + "/resources/audio/" + "BigBillBroonzy-BabyPleaseDontGo1.mp3";

    @Test
    void playSampleKnownTest() throws NonAvailableTrackException {
        MusicPlayer mp = new MusicPlayer();
        //Not easy to test it in an automatic way !
        assertDoesNotThrow(()-> mp.playSample(filename));
    }
    @Test
    void playSampleUnknownFileTest() {
        MusicPlayer mp = new MusicPlayer();
        assertThrows(NonAvailableTrackException.class, ()-> mp.playSample("unknown.mp3"));
        assertThrows(NonAvailableTrackException.class, ()-> mp.playSample("unknown"));
    }

    @Test
    void startPlayingUnknownFileTest() {
        MusicPlayer mp = new MusicPlayer();
        assertThrows(NonAvailableTrackException.class, ()-> mp.startPlaying("unknown.mp3"));
    }

}