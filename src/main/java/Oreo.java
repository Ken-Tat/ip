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
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                }

                System.out.println("____________________________________________");
            } else if (userInput.startsWith("mark ")) {
                String taskNumberText = userInput.substring("mark ".length()).trim();

                try {
                    int taskIndex = Integer.parseInt(taskNumberText) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        System.out.println("____________________________________________\n"
                                + "That task number is not in the list.\n"
                                + "____________________________________________");
                    } else {
                        String task = tasks.get(taskIndex);
                        String completedTask = "[X]" + task.substring(3);
                        tasks.set(taskIndex, completedTask);
                        System.out.println("____________________________________________\n"
                                + "Nice! I've marked this task as done:\n"
                                + "  " + completedTask + "\n"
                                + "____________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________\n"
                            + "Please provide a valid task number.\n"
                            + "____________________________________________");
                }
            } else {
                System.out.println("____________________________________________ \n"
                            + "added: " + userInput + " \n"
                            + "____________________________________________ \n");
                tasks.add("[ ] " + userInput);
            }
        }
    }
}
