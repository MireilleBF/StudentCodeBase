package mp3organizer.v2;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Demo4MusicOrganizer {

    private static final String ROOT_FOR_FILES = System.getProperty("user.dir");

    // This class is not intended to be instantiated, so prevent
    Demo4MusicOrganizer() {
        super();
    }


    static String askMainCommands() {
        StringBuilder commands = new StringBuilder("What do you want to do ?\n");
        for (Command command : Command.values()) {
            commands.append(command + "\n");
        }
        commands.append("Your choice : ");
        return commands.toString();
    }

    protected static String getCommand(String s, Scanner sc) {
        String command;
        System.out.print(s);
        command = sc.nextLine();
        return command;
    }

    public static void main(String[] args) {
        MusicOrganizer musicOrganizer = new MusicOrganizer(new MusicPlayer());
        Scanner sc = new Scanner(System.in);
        System.out.println("Placez vos fichiers audio sous : " + System.getProperty("user.dir"));
        boolean goOn;

        String command = getCommand(askMainCommands(), sc);
        goOn = !Command.quit(command);

        while (goOn) {
            try {
                interpretCommand(command, musicOrganizer, sc);
            } catch (NonAvailableTrackException e) {
                System.out.println("The file cannot be listen : " + e);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } finally {
                command = getCommand(askMainCommands(), sc);
                goOn = !Command.quit(command);
            }
        }


        assert !goOn;
    }

    protected static void interpretCommand(String prompt, MusicOrganizer mo, Scanner sc) throws NonAvailableTrackException {
        String[] line = prompt.trim().split("\\s+");
        if (line.length == 0) {
            writeMsg("empty command " + prompt);
            return;
        }
        String begin = line[0];
        String end = null;
        if (line.length == 2) {
            end = line[1];
        }
        if (line.length > 2) {
            writeMsg("Too many arguments :" + prompt);
            return;
        }

        Command command = Command.getByAbbreviation(begin);
        if (command == null) {
            writeMsg("Unknown command " + prompt);
            return;
        }
        applyCommand(mo, sc, command, end);
    }

    private static void applyCommand(MusicOrganizer mo, Scanner sc, Command command, String end) throws NonAvailableTrackException {
        System.out.println("Command to run: " + command);
        switch (command) {
            case ADD_TRACK:
                String filename;
                filename = askArgValue(end, "What file do you want to add ?", sc);
                mo.addFile(ROOT_FOR_FILES + filename);
                break;
            case REMOVE_TRACK:
                String index;
                index = askArgValue(end, "What track do you want to remove ?", sc);
                mo.removeTrack(Integer.parseInt(index));
                writeMsg("Track removed");
                break;
            case PLAY_TRACK:
                playTrack(mo, sc, end);
                break;
            case LIST_TRACKS:
                String[] tracks = mo.listTracks();
                displayTracks(mo, tracks);
                break;
            case STOP_PLAYING:
                mo.stopPlaying();
                break;
            case MATCH_TRACKS:
                String searchString;
                searchString = askArgValue(end, "Search string ?", sc);
                List<String> machingList = mo.listMatching(searchString);
                displayTracks(mo, machingList.toArray(new String[0]));
                break;
            case MATCH_TRACKS_BY_ARTIST:
                searchString = askArgValue(end, "Search string ?", sc);
                machingList = mo.listMatchingArtist(searchString);
                displayTracks(mo, machingList.toArray(new String[0]));
                break;
            case LISTEN_MATCHING_TRACKS:
                searchString = askArgValue(end, "Search string ?", sc);
                mo.playMatchingTracks(searchString);
                break;
            case LISTEN_RANDOMLY_PREFERED_TRACKS:
                listenRandomlyPreferedTracks(mo);
                break;
            case SORT_TRACKS:
                String sortType;
                sortType = askArgValue(end, "Sort type ? a = artist, t = title, l = number of listens", sc);
                sortTracks(mo,sortType);
                break;
            case NUMBER_COMMAND:
                writeMsg("Number of tracks : " + mo.numberOfFiles());
                break;

            case QUIT:
                System.out.println("Bye");
                break;
            default:
                System.out.println("Unknown command : " + command);
        }
    }

    private static int[]  listenRandomlyPreferedTracks(MusicOrganizer mo) throws NonAvailableTrackException {
        int[] played = mo.randomPlayingPreferedTracks(0);
        writeMsg("Played tracks : " + Arrays.toString(played));
        return played;
    }

    private static void playTrack(MusicOrganizer mo, Scanner sc, String end) throws NonAvailableTrackException {
        System.out.println("Play track");
        String index;
        index = askArgValue(end, "What track do you want to play ?", sc);
        mo.playTrack(Integer.parseInt(index));
        System.out.println("Stop Playing track " + index);
    }

    private static void sortTracks(MusicOrganizer mo, String sortType) {
        List<Track> tracks = new ArrayList<>();
        if (sortType.equals("a")) {
             tracks = mo.sortByArtist();
        } else if (sortType.equals("t")) {
            tracks = mo.sortByTitle();
        } else if (sortType.equals("l")) {
            tracks = mo.sortByListen();
        } else {
            throw new IllegalArgumentException("Unknown sort type : " + sortType);
        }
        displayTracks(mo, tracks);
    }

    private static String askArgValue(String end, String message, Scanner sc) {
        String arg;
        if (end != null && !(end.equals(""))) {
            arg = end;
        } else {
            arg = getCommand(message, sc);
        }
        return arg;
    }

    private static void displayTracks(MusicOrganizer mp, String... tracks) {
        System.out.println("Tracks ("+ tracks.length+ ") : \n");
        if (tracks.length == 0) {
            writeMsg("No track");
            return;
        }
        for (String track : tracks) {
            int trackNumber = mp.getTrackNumber(track);
            if (trackNumber == -1) {
                System.out.println("Error : " + track + " not found");
            } else {
                System.out.println((trackNumber + 1) + "\t :" + track);
            }
        }
        System.out.println("--- \n");
    }

    private static void displayTracks(MusicOrganizer mp, List<Track> tracks) {
        System.out.println("Tracks ("+ tracks.size()+ ") : \n");
        if (tracks.isEmpty()) {
            writeMsg("No track");
            return;
        }
        for (Track track : tracks) {
            int trackNumber = mp.getTrackNumber(track.getFilename());
            if (trackNumber == -1) {
                System.out.println("Error : " + track + " not found");
            } else {
                System.out.println((trackNumber + 1) + "\t :" + track);
            }
        }
        System.out.println("--- \n");
    }

    private static void writeMsg(String message) {
        System.out.println(message);
    }
}

/******
 *  a /resources/audio/Zaz-Jeveux.mp3
 *  l
 *  so a
 *  p 1
 *  s
 *  a /resources/audio/BigBillBroonzy-BabyPleaseDontGo1.mp3
 * a /resources/audio/Zaz-Decouleursvives.mp3
 * a /resources/audio/BlindBlake-EarlyMorningBlues.mp3
 * a /resources/audio/BlindLemonJefferson-matchBoxBlues.mp3
 * l
 * so a
 * so t
 * so l
 * m Zaz
 * pm Zaz
 * s a
 * s t
 * s l
 * p
 */