package mp3player;

import java.util.Scanner;

/**
 * @author Mireille Blay-Fornarino
 */
public class MusicPlayerDemo {

    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
        Scanner sc = new Scanner(System.in);
        System.out.println("Placez vos fichiers audio sous : " + System.getProperty("user.dir"));
        boolean goOn = true;


        while (goOn) {
            String command = getCommand("What file do you want to listen to (to stop type 's')?",sc);
            goOn = !"s".equals(command);
            if (goOn) {
                try {
                    mp.playSample(System.getProperty("user.dir") + command);
                } catch (NonAvailableTrackException e) {
                    System.out.println("The file cannot be listen : " + e);

                    command = getCommand("What file do you want to listen to (to stop type 's')?", sc);
                    goOn = !"s".equals(command);
                }
            }
        }

        assert !goOn;

        String command = getCommand("What file do you want to listen to for a long time (to stop type 's')?",sc);
        goOn = !"s".equals(command);
        while (goOn) {
            try {
                mp.startPlaying(System.getProperty("user.dir") + command);
                command = getCommand("To stop to listen type (s) or a new file name ", sc);
                mp.stop();
                goOn = !"s".equals(command);
            } catch (NonAvailableTrackException e) {
                System.out.println("The file cannot be listen : " + e);
                command = getCommand("What file do you want to listen to for a long time (to stop type 's')?", sc);
                goOn = !"s".equals(command);
            }
        }


    }

    private static String getCommand(String s, Scanner sc) {
        String command;
        System.out.print(s);
        command = sc.nextLine();
        return command;
    }
}

