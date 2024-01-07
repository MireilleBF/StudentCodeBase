package mp3organizer.v2;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * author: Mireille Blay-Fornarino
 * date: 2023-11-12
 * TODO: add better tests
 *
 */
class Demo4MusicOrganizerTest {


    @Test
    void testInterpretCommands() throws NonAvailableTrackException, IOException {
        MusicOrganizer mp = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        testCommand(Command.ADD_TRACK,Command.ADD_TRACK.getByAbbreviation() + " /resources/audio/BlindBlake-EarlyMorningBlues.mp3", mp, sc,"1 track");

        assertEquals(1,mp.getNumberOfTracks());
        testCommand(Command.NUMBER_COMMAND,Command.NUMBER_COMMAND.getByAbbreviation(), mp, sc,"1 track");

        assertEquals(1,mp.listTracks().length);
        testCommand(Command.LIST_TRACKS,Command.LIST_TRACKS.getByAbbreviation(), mp, sc,"1 track");

        testCommand(Command.ADD_TRACK,Command.ADD_TRACK.getByAbbreviation() + " /resources/audio/BigBillBroonzy-BabyPleaseDontGo1.mp3", mp, sc,"2 tracks");
        assertEquals(2,mp.getNumberOfTracks());

        testCommand(Command.ADD_TRACK,Command.ADD_TRACK.getByAbbreviation() + " /resources/audio/Zaz-Decouleursvives.mp3", mp, sc,"3 tracks");
        assertEquals(3,mp.getNumberOfTracks());
        testCommand(Command.LIST_TRACKS,Command.LIST_TRACKS.getByAbbreviation(), mp, sc,"3 tracks");
        testCommand(Command.LISTEN_MATCHING_TRACKS,Command.LISTEN_MATCHING_TRACKS.getByAbbreviation() + " Zaz", mp, sc,"1 track");

        testCommand(Command.PLAY_TRACK,Command.PLAY_TRACK.getByAbbreviation() + " 1", mp, sc,"Playing track 1");
        assertEquals(1,mp.getTrack(0).getNumberOflistens());
        testCommand(Command.STOP_PLAYING,Command.STOP_PLAYING.getByAbbreviation(), mp, sc,"Stopped playing track 1");

        testCommand(Command.MATCH_TRACKS,Command.MATCH_TRACKS.getByAbbreviation() + " Zaz", mp, sc,"1 matching tracks");
        testCommand(Command.ADD_TRACK,Command.ADD_TRACK.getByAbbreviation() + " /resources/audio/Zaz-Jeveux.mp3", mp, sc,"4 tracks");
        assertEquals(4,mp.getNumberOfTracks());
        testCommand(Command.MATCH_TRACKS,Command.MATCH_TRACKS.getByAbbreviation() + " Zaz", mp, sc,"2 matching tracks");

        testCommand(Command.LISTEN_MATCHING_TRACKS,Command.LISTEN_MATCHING_TRACKS.getByAbbreviation() + " Zaz", mp, sc,"2 tracks");
        testCommand(Command.LISTEN_RANDOMLY_PREFERED_TRACKS,Command.LISTEN_RANDOMLY_PREFERED_TRACKS.getByAbbreviation(), mp, sc,"2 tracks");

        testCommand(Command.SORT_TRACKS,Command.SORT_TRACKS.getByAbbreviation() + " a", mp, sc,"Sorted tracks");
        testCommand(Command.SORT_TRACKS,Command.SORT_TRACKS.getByAbbreviation() + " l", mp, sc,"Sorted tracks");
        testCommand(Command.SORT_TRACKS,Command.SORT_TRACKS.getByAbbreviation() + " t", mp, sc,"Sorted tracks");


    }

    private static void testCommand(Command c, String commandToTest, MusicOrganizer mp, Scanner sc, String expectedValue) throws NonAvailableTrackException, IOException{
        System.out.println("Test " + c.name() + " with " + commandToTest +"\t: " +  mp);
        Demo4MusicOrganizer.interpretCommand(commandToTest, mp, sc);
        System.out.println("Expected " + expectedValue + " : " +  mp+"\n");
    }

}