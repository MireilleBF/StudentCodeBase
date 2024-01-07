package mp3organizer.v1;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DemoTest {
    private final  String rootForFiles = System.getProperty("user.dir");


    @Test
    void testInterpretCommandSimpleAdd() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        String command = "a " + "/resources/audio/BlindBlake-EarlyMorningBlues.mp3";
        Demo.interpretCommand(command,mp,sc);
        assertEquals(1,mp.getNumberOfTracks());
        assertEquals(System.getProperty("user.dir") + "/resources/audio/BlindBlake-EarlyMorningBlues.mp3",mp.listTracks()[0]);
    }

    @Test
    void testInterpretCommandFailingAdd() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        String command = "a " + "/resources/audio/BlindBlake-Early.mp3";
        assertThrows(IllegalArgumentException.class, () ->Demo.interpretCommand(command,mp,sc));
        assertEquals(0,mp.getNumberOfTracks());
    }

    @Test
    void testInterpretCommandBad() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        String command = "b ";
        Demo.interpretCommand(command,mp,sc);
        assertEquals(0,mp.getNumberOfTracks());
    }

    /**
     * Test of the play command only to be sure there is no error
     * @throws NonAvailableTrackException
     * @throws IOException
     */
    @Test
    void testInterpretCommandPlayAndStop() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp2 = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        mp2.addFile(rootForFiles+"/resources/audio/BlindBlake-EarlyMorningBlues.mp3");
        String command = "p 1";
        Demo.interpretCommand(command,mp2,sc);
        assertEquals(1,mp2.getNumberOfTracks());
       // Demo.interpretCommand("s",mp2,sc);

    }

    /**
     * Test of the list command only to be sure there is no error
     * @throws NonAvailableTrackException
     * @throws IOException
     */
    @Test
    void testInterpretCommandList() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        Demo.interpretCommand("l",mp,sc);
        assertEquals(0,mp.getNumberOfTracks());
        mp.addFile(rootForFiles+"/resources/audio/BlindBlake-EarlyMorningBlues.mp3");
        Demo.interpretCommand("l",mp,sc);
        Demo.interpretCommand("q",mp,sc);
        assertEquals(1,mp.getNumberOfTracks());
    }

    /**
     * Test of the commands only to be sure there is no error
     * @throws NonAvailableTrackException
     * @throws IOException
     */
    @Test
    void testInterpretCommands() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        Demo.interpretCommand("a /resources/audio/Zaz-Jeveux.mp3",mp,sc);
        Demo.interpretCommand("l",mp,sc);
        Demo.interpretCommand("a /resources/audio/BigBillBroonzy-BabyPleaseDontGo1.mp3",mp,sc);
        Demo.interpretCommand("l",mp,sc);
        Demo.interpretCommand("a /resources/audio/Zaz-Decouleursvives.mp3",mp,sc);
        assertEquals(3,mp.getNumberOfTracks());
        Demo.interpretCommand("l",mp,sc);
        Demo.interpretCommand("m Zaz",mp,sc);
        Demo.interpretCommand("pm Zaz",mp,sc);
        assertEquals(3,mp.getNumberOfTracks());
    }
}