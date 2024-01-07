package mp3organizer.v1;

import mp3player.MusicPlayer;
import mp3player.NonAvailableTrackException;

import java.util.List;
import java.util.Scanner;


public class Demo {

    private static final String ROOT_FOR_FILES = System.getProperty("user.dir");

    Demo() {
    }

    private static String askMainCommands() {
        return "What do you want to do ?\n" +
                Command.ADD_TRACK + "\n" +
                Command.REMOVE_TRACK + "\n" +
                Command.PLAY_TRACK + "\n" +
                Command.STOP_PLAYING + "\n" +
                Command.LIST_TRACKS + " \n" +
                Command.MATCH_TRACKS + " \n" +
                Command.PLAY_MATCHING_TRACKS + " \n" +
                Command.QUIT + " \n" +
                "Your choice : ";
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
                index = askArgValue(end, "What track do you want to play ?", sc);
                mo.playTrack(Integer.parseInt(index));
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
            case PLAY_MATCHING_TRACKS:
                searchString = askArgValue(end, "Search string ?", sc);
                mo.playMatchingTracks(searchString);
                break;
            case QUIT:
                System.out.println("Bye");
                break;
            default:
                System.out.println("Unknown command : " + command);
        }
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
    }

    private static void writeMsg(String message) {
        System.out.println(message);
    }
}

/******
 *  a /resources/audio/Zaz-Jeveux.mp3
 *  l
 *  p 1
 *  s
 *  a /resources/audio/BigBillBroonzy-BabyPleaseDontGo1.mp3
 * a /resources/audio/Zaz-Decouleursvives.mp3
 * l
 * m Zaz
 * pm Zaz
 */