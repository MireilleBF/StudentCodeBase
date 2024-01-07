package mp3organizer.v1;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Command {
    ADD_TRACK("Add a track", "a", "filename"),
    REMOVE_TRACK("Remove track", "r", "index"),
    PLAY_TRACK ("Play a track", "p", "index"),
    STOP_PLAYING("Stop playing", "s", ""),
    LIST_TRACKS ("List tracks", "l", ""),
    MATCH_TRACKS ("Match tracks", "m", "search string"),
    PLAY_MATCHING_TRACKS ("Play matching tracks", "pm", "search string"),
    QUIT ("Quit", "q", "");

    private final String commandDescription;
    private final String abbreviation;
    private final String argsDescription;

    private static final Map<String,Command> BY_PROMPT = new ConcurrentHashMap<>();

    Command(String commandDescription, String abbreviation, String argsDescription) {
        this.commandDescription = commandDescription;
        this.abbreviation = abbreviation;
        this.argsDescription = argsDescription;
        // BY_PROMPT.put(abbreviation, this); not possible because static are initialized after enum values
    }

    static {
        for (Command e: values()) {
            BY_PROMPT.put(e.abbreviation, e);
        }
    }

    @Override
    public String toString() {
        return commandDescription + " : \t" + abbreviation + " " + argsDescription + " ";
    }

    public String getByAbbreviation() {
        return abbreviation;
    }

    public static Command getByAbbreviation(String prompt) {
        return BY_PROMPT.get(getStripped(prompt));
    }

    private static String getStripped(String prompt) {
        return prompt.toLowerCase(Locale.US).strip();
    }

    public static boolean quit(String command) {
        return QUIT.getByAbbreviation().equals(getStripped(command));
    }



}
