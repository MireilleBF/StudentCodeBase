package mp3organizer.v2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrackReaderTest {


    TrackReader trackReader = new TrackReader("-");


    @Test
    void testBuildTracks() {
        ArrayList<Track> tracks = trackReader.buildTracks(    Files4Tests.RESOURCES_AUDIO,"mp3");
        System.out.println(tracks);
        assertEquals(8,tracks.size());
        assertContains(tracks,"BlindBlake-EarlyMorningBlues.mp3");
        assertContains(tracks,"BigBillBroonzy-BabyPleaseDontGo1.mp3");
        assertContains(tracks,"BlindLemonJefferson-matchBoxBlues.mp3");
        assertContains(tracks,"BlindLemonJefferson-OneDimeBlues.mp3");
        assertContains(tracks,"Zaz-Decouleursvives.mp3");
        assertContains(tracks,"Zaz_Jeveux.mp3");
        assertContains(tracks,"Zaz-Jeveux.mp3");
    }

    private void assertContains(ArrayList<Track> tracks, String s) {
        for (Track track : tracks) {
            if (track.getFilename().equals(Files4Tests.RESOURCES_AUDIO + s)) {
                return;
            }
        }
        fail("The track " + s + " is not in the list");
    }


    @Test
    void testDecodeDetails() {
        Track track = trackReader.decodeDetails("BlindBlake-EarlyMorningBlues.mp3");
        assertEquals("BlindBlake",track.getArtist());
        assertEquals("EarlyMorningBlues",track.getTitle());
    }


    @Test
    void testDecodeDetailsWith_() {
        trackReader = new TrackReader("_");
        Track track = trackReader.decodeDetails("BlindBlake_EarlyMorningBlues.mp3");
        assertEquals("BlindBlake",track.getArtist());
        assertEquals("EarlyMorningBlues",track.getTitle());
    }

    @Test
    void testDecodeDetailsWithPath() {
        Track track = trackReader.decodeDetails(Files4Tests.RESOURCES_AUDIO +"BlindBlake-EarlyMorningBlues.mp3");
        assertEquals("BlindBlake",track.getArtist());
        assertEquals("EarlyMorningBlues",track.getTitle());
    }

    @Test
    void testDecodeDetailsBad() {
        Track track = trackReader.decodeDetails(Files4Tests.RESOURCES_AUDIO +"BlindBlake_EarlyMorningBlues.mp3");
        assertEquals(TrackReader.UNKNOWN,track.getArtist());
        assertEquals(TrackReader.UNKNOWN,track.getTitle());
    }

    @Test
    void testDecodeDetailsWithoutExtension() {
        Track track = trackReader.decodeDetails(Files4Tests.RESOURCES_AUDIO +"BlindBlake-EarlyMorningBlues");
        assertEquals("BlindBlake",track.getArtist());
        assertEquals("EarlyMorningBlues",track.getTitle());
    }

    //TODO test decodeDetails with a file on another exploitation system

}