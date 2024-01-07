package mp3organizer.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void testGetCommand() {
        assertEquals(Command.QUIT,Command.getByAbbreviation("q"));
        assertEquals(Command.ADD_TRACK,Command.getByAbbreviation("a"));
        assertEquals(Command.ADD_TRACK,Command.getByAbbreviation("A"));
        assertEquals(Command.ADD_TRACK,Command.getByAbbreviation("a "));
        assertEquals(Command.ADD_TRACK,Command.getByAbbreviation(" a"));
        assertEquals(Command.REMOVE_TRACK,Command.getByAbbreviation("r"));
        assertEquals(Command.PLAY_TRACK,Command.getByAbbreviation("p"));
        assertEquals(Command.STOP_PLAYING,Command.getByAbbreviation("s"));
        assertEquals(Command.LIST_TRACKS,Command.getByAbbreviation("l"));
        assertEquals(Command.MATCH_TRACKS,Command.getByAbbreviation("m"));
        assertEquals(Command.PLAY_MATCHING_TRACKS,Command.getByAbbreviation("pm"));
    }

    @Test
    void testValue() {
        assertEquals(Command.QUIT,Command.valueOf("QUIT"));
        assertEquals(Command.ADD_TRACK,Command.valueOf("ADD_TRACK"));
        assertEquals(Command.REMOVE_TRACK,Command.valueOf("REMOVE_TRACK"));
        assertEquals(Command.PLAY_TRACK,Command.valueOf("PLAY_TRACK"));
        assertEquals(Command.STOP_PLAYING,Command.valueOf("STOP_PLAYING"));
        assertEquals(Command.LIST_TRACKS,Command.valueOf("LIST_TRACKS"));
        assertEquals(Command.MATCH_TRACKS,Command.valueOf("MATCH_TRACKS"));
        assertEquals(Command.PLAY_MATCHING_TRACKS,Command.valueOf("PLAY_MATCHING_TRACKS"));
    }

    @Test
    void quit() {
        assertTrue(Command.quit("q"));
        assertTrue(Command.quit("Q"));
        assertTrue(Command.quit(" q"));
        assertTrue(Command.quit("q "));
        assertTrue(Command.quit(" q "));
        assertFalse(Command.quit("a"));
        assertFalse(Command.quit("S"));
    }


    @Test
    void testValueOf() {
        assertEquals(Command.QUIT,Command.valueOf("QUIT"));
        assertEquals(Command.ADD_TRACK,Command.valueOf("ADD_TRACK"));
        assertEquals(Command.REMOVE_TRACK,Command.valueOf("REMOVE_TRACK"));
        assertEquals(Command.PLAY_TRACK,Command.valueOf("PLAY_TRACK"));
        assertEquals(Command.STOP_PLAYING,Command.valueOf("STOP_PLAYING"));
        assertEquals(Command.LIST_TRACKS,Command.valueOf("LIST_TRACKS"));
        assertEquals(Command.MATCH_TRACKS,Command.valueOf("MATCH_TRACKS"));
        assertEquals(Command.PLAY_MATCHING_TRACKS,Command.valueOf("PLAY_MATCHING_TRACKS"));

    }
}