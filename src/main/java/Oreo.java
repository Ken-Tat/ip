import java.util.Scanner;

public class Oreo {
    private static final String NAME = "Oreo";

    public static void main(String[] args) {
        String banner = "  OOO   RRRR   EEEEE  OOO  \n"
                + " O   O  R   R  E     O   O \n"
                + " O   O  RRRR   EEEE  O   O \n"
                + " O   O  R R    E     O   O \n"
                + "  OOO   R  RR  EEEEE  OOO  \n";

        String greeting = "____________________________________________ \n"
                + banner + "\n"
                + "Hello! I'm " + NAME + ". \n"
                + "Let's get started shall we? \n"
                + "____________________________________________";

        String goodbye = "____________________________________________ \n"
                + "Good work. See you nexttime! \n"
                + "____________________________________________ \n";

        System.out.println(greeting);

        // take in user input
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("bye")) {
            userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                System.out.println(goodbye);
            } else {
                System.out.println("____________________________________________ \n"
                            + userInput + " \n"
                            + "____________________________________________ \n");
            }
        }
    }
}
