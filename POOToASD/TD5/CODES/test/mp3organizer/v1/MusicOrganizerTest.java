package mp3organizer.v1;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MusicOrganizerTest {

    MusicOrganizer musicOrganizer;
    List<String> files = new ArrayList<>();
    protected String file1 = "./resources/audio/BlindBlake-EarlyMorningBlues.mp3";
    protected String file2 = "./resources/audio/BigBillBroonzy-BabyPleaseDontGo1.mp3";
    String file3 = "./resources/audio/BlindLemonJefferson-matchBoxBlues.mp3";
    String file4 = "./resources/audio/BlindLemonJefferson-OneDimeBlues.mp3";

    String file5 = "./resources/audio/Zaz-Decouleursvives.mp3";
    String file6 = "./resources/audio/Zaz-Jeveux.mp3";
    @BeforeEach
    protected void setUp() {
        musicOrganizer = new MusicOrganizer();
        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);
    }

    @Test
    void testAddOneTrack() {
        musicOrganizer.addFile(file1);
        assertEquals(1,musicOrganizer.getNumberOfTracks());
        assertEquals(file1,musicOrganizer.listTracks()[0]);
    }

    @Test
    void testAddOneTrackInErrorNotMP3() {
        String fileMP4 = "./resources/audio/DV7nmRa.mp4";
        //To be sure that an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> musicOrganizer.addFile(fileMP4));
        try {
            musicOrganizer.addFile(fileMP4);
        } catch (IllegalArgumentException e) {
            assertEquals("The file " + fileMP4 + " is not a mp3 file.", e.getMessage());
        }
    }
    @Test
    void testAddOneTrackInErrorNotFile() {
        String fileNotExisting = "./resources/audio/TinaTurner-ProudMary.mp3";
        //To be sure that an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> musicOrganizer.addFile(fileNotExisting));
        try {
            musicOrganizer.addFile(fileNotExisting);
        } catch (IllegalArgumentException e) {
            assertEquals("The file " + fileNotExisting + " does not exist.", e.getMessage());
        }
    }

    @Test
    void testCreateMusicOrganizerWithOneTrackInError() {
        String fileNotExisting = "./resources/audio/TinaTurner-ProudMary.mp3";
        MusicPlayer musicPlayer = new MusicPlayer();
        ArrayList<String> files = new ArrayList();
        //Better than new ArrayList<String>(){{add(file1);add(fileNotExisting);add(file3);add(file4);}};
        files.add(file1);
        files.add(fileNotExisting);
        files.add(file3);
        files.add(file4);

        //To be sure that an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> new MusicOrganizer(musicPlayer,files));

        try {
            new MusicOrganizer(musicPlayer,
                    new ArrayList<>(){{
                        add(file1);
                        add(fileNotExisting);
                        add(file3);
                        add(file4);
                    }});
        } catch (IllegalArgumentException e) {
            assertEquals("errors reading files[The file " + fileNotExisting +" does not exist.]",e.getMessage());
        }
    }

    @Test
    void testAddOneTrackInErrorNotReadableFile() {
        String filename = "./resources/audio/GPS_LOST.mp3";
        File f = new File(filename);
        f.setReadable(false);
        assertFalse(f.canRead());
        //To be sure that an exception is thrown
        assertThrows(IllegalArgumentException.class, () -> musicOrganizer.addFile(filename));
        try {
            musicOrganizer.addFile(filename);
        } catch (IllegalArgumentException e) {
            assertEquals("The file " + filename + " cannot be read.", e.getMessage());
        }


    }


    @Test
    void testMultipleAddTracks() {
        musicOrganizer.addFile(file1);
        musicOrganizer.addFile(file2);
        assertEquals(2,musicOrganizer.getNumberOfTracks());
        assertEquals(file1,musicOrganizer.listTracks()[0]);
        assertEquals(file2,musicOrganizer.listTracks()[1]);
    }

    @Test
    void testAddTracks() {
        musicOrganizer.addTracks(files);
        assertEquals(4,musicOrganizer.getNumberOfTracks());
        assertEquals(file1,musicOrganizer.listTracks()[0]);
        assertEquals(file2,musicOrganizer.listTracks()[1]);
    }

    @Test
    void testAddTracksWithErrors() {
        String filenameUR = "./resources/audio/GPS_LOST.mp3";
        new File(filenameUR).setReadable(false);
        String fileNotExisting = "./resources/audio/TinaTurner-ProudMary.mp3";
        String fileMP4 = "./resources/audio/DV7nmRa.mp4";
        files = new ArrayList<>();
        files.add(filenameUR);
        files.add(file1);
        files.add(fileNotExisting);
        files.add(file2);
        files.add(fileMP4);
        files.add(file3);
        files.add(file4);
        List<String> errors = musicOrganizer.addTracks(files);
        assertEquals(4,musicOrganizer.getNumberOfTracks());
        assertEquals(file1,musicOrganizer.listTracks()[0]);
        assertEquals(file2,musicOrganizer.listTracks()[1]);
        assertEquals(file3,musicOrganizer.listTracks()[2]);
        assertEquals(3, errors.size());
        assertEquals("The file "+ fileNotExisting +" does not exist.",errors.get(1));
        assertEquals("The file "+ fileMP4 +" is not a mp3 file.",errors.get(2));
        assertEquals("The file "+ filenameUR +" cannot be read.",errors.get(0));
    }

    @Test
    void testInitMusicOrganizer() {
        musicOrganizer = new MusicOrganizer(new MusicPlayer(),files);
        assertEquals(4,musicOrganizer.getNumberOfTracks());
        assertEquals(file1,musicOrganizer.listTracks()[0]);
        assertEquals(file2,musicOrganizer.listTracks()[1]);
        assertEquals(file3,musicOrganizer.listTracks()[2]);
    }

    @Test
    void testRemoveTrack() {
        musicOrganizer = new MusicOrganizer(new MusicPlayer());
        musicOrganizer.addFile(file1);
        musicOrganizer.removeTrack(1);
        assertEquals(0,musicOrganizer.getNumberOfTracks());
        musicOrganizer = new MusicOrganizer(new MusicPlayer(),files);
        musicOrganizer.removeTrack(1);//0 from implementation point of view
        assertEquals(file2,musicOrganizer.listTracks()[0]);
        assertEquals(file3,musicOrganizer.listTracks()[1]);
        musicOrganizer.removeTrack(2);//1 from implementation point of view
        assertEquals(file4,musicOrganizer.listTracks()[1]);
    }

    /**
     * Test of the play command only to be sure there is no error
     * @throws NonAvailableTrackException
     */
    @Test
    void testPlayTrack() throws NonAvailableTrackException {
        musicOrganizer = new MusicOrganizer(new MusicPlayer(),files);
        assertDoesNotThrow(()-> musicOrganizer.playTrack(1));
        assertDoesNotThrow(()-> musicOrganizer.playTrack(2));
        assertDoesNotThrow(()-> musicOrganizer.playTrack(3));
        assertDoesNotThrow(()-> musicOrganizer.playTrack(4));

    }
    @Test
    void testPlayTrackInError() throws NonAvailableTrackException {
        musicOrganizer = new MusicOrganizer(new MusicPlayer());
        List<String> errors = musicOrganizer.addTracks(files);
        assertTrue(errors.isEmpty());
        assertThrows(NonAvailableTrackException.class, () -> musicOrganizer.playTrack(5));
        assertThrows(NonAvailableTrackException.class, () -> musicOrganizer.playTrack(-1));
    }

    @Test
    void testListTracks() {
        musicOrganizer = new MusicOrganizer(new MusicPlayer(),files);
        String[] tracks = musicOrganizer.listTracks();
        assertEquals(4,tracks.length);
        assertEquals(file1,tracks[0]);
        assertEquals(file2,tracks[1]);
        assertEquals(file3,tracks[2]);
        assertEquals(file4,tracks[3]);
    }


    @Test
    void testListTracksWithEmptyMusicOrganizer() {
        musicOrganizer = new MusicOrganizer(new MusicPlayer());
        String[] tracks = musicOrganizer.listTracks();
        assertEquals(0,tracks.length);
    }

    @Test
    void testMatchingTracks() {
        musicOrganizer = new MusicOrganizer(new MusicPlayer(),files);
        musicOrganizer.addFile(file5);
        musicOrganizer.addFile(file6);

        List<String> tracks = musicOrganizer.listMatching("Blind");
        assertEquals(3,tracks.size());
        assertEquals(file1,tracks.get(0));
        assertEquals(file3,tracks.get(1));
        assertEquals(file4,tracks.get(2));
        tracks = musicOrganizer.listMatching("Zaz");
        assertEquals(2,tracks.size());
        assertEquals(file5,tracks.get(0));
        assertEquals(file6,tracks.get(1));
        tracks = musicOrganizer.listMatching("Gims");
        assertEquals(0,tracks.size());
    }

    @Test
    void testPlayMatchingTracks() throws NonAvailableTrackException {
        musicOrganizer = new MusicOrganizer(new MusicPlayer(),files);
        musicOrganizer.addFile(file5);
        musicOrganizer.addFile(file6);
        assertFalse(musicOrganizer.playMatchingTracks("Vianney"));
        assertTrue(musicOrganizer.playMatchingTracks("Blake"));
        assertTrue(musicOrganizer.playMatchingTracks("couleurs"));
        assertTrue(musicOrganizer.playMatchingTracks("Zaz"));
    }



}