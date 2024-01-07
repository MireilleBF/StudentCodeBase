package mp3organizer.v2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackTest {

    Track track1 = new Track("file1");
    Track track2 = new Track("./resources/audio/v2/file2");
    @Test
    void testGetFile() {
        assertEquals("file1",track1.getFilename());
        assertEquals("./resources/audio/v2/file2",track2.getFilename());
    }

    @Test
    void testInit() {
        assertEquals("file1",track1.getFilename());
        assertEquals("unknown",track1.getArtist());
        assertEquals("unknown",track1.getTitle());
    }

    @Test
    void testEquals() {
        Track track1bis = new Track("file1");
        Track track2bis = new Track("./resources/audio/v2/file2");
        assertEquals(track1,track1bis);
        assertEquals(track2,track2bis);
        assertNotEquals(track1,track2);
    }
}