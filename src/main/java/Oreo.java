import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple command-line chatbot that stores and displays user-entered tasks.
 */
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
                + "Good work. See you next time! \n"
                + "____________________________________________ \n";

        System.out.println(greeting);

        List<String> tasks = new ArrayList<>();

        // Reads commands from standard input.
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("bye")) {
            userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                System.out.println(goodbye);
            } else if (userInput.equals("list")) {
                System.out.println("____________________________________________");

                if (tasks.isEmpty()) {
                    System.out.println("No tasks in the list.");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                }

                System.out.println("____________________________________________");
            } else {
                System.out.println("____________________________________________ \n"
                            + "added: " + userInput + " \n"
                            + "____________________________________________ \n");
                tasks.add(userInput);
            }
        }
    }
}
