/** Handles messages that greet the user and close the application. */
public class Ui {
    private static final String NAME = "Oreo";

    /** Displays the application's greeting banner. */
    public void showGreeting() {
        String banner = "  OOO   RRRR   EEEEE  OOO  \n"
                + " O   O  R   R  E     O   O \n"
                + " O   O  RRRR   EEEE  O   O \n"
                + " O   O  R R    E     O   O \n"
                + "  OOO   R  RR  EEEEE  OOO  \n";
        System.out.println("____________________________________________ \n"
                + banner + "\n"
                + "Hello! I'm " + NAME + ". \n"
                + "Let's get started shall we? \n"
                + "____________________________________________");
    }

    /** Displays the application's goodbye message. */
    public void showGoodbye() {
        System.out.println("____________________________________________ \n"
                + "Good work. See you next time! \n"
                + "____________________________________________ \n");
    }
}
