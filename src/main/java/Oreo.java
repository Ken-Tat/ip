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

        List<Task> tasks = new ArrayList<>();

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
                        Task task = tasks.get(taskIndex);
                        task.markAsDone();
                        System.out.println("____________________________________________\n"
                                + "Nice! I've marked this task as done:\n"
                                + "  " + task + "\n"
                                + "____________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________\n"
                            + "Please provide a valid task number.\n"
                            + "____________________________________________");
                }
            } else if (userInput.startsWith("unmark ")) {
                String taskNumberText = userInput.substring("unmark ".length()).trim();

                try {
                    int taskIndex = Integer.parseInt(taskNumberText) - 1;
                    if (taskIndex < 0 || taskIndex >= tasks.size()) {
                        System.out.println("____________________________________________\n"
                                + "That task number is not in the list.\n"
                                + "____________________________________________");
                    } else {
                        Task task = tasks.get(taskIndex);
                        task.markAsNotDone();
                        System.out.println("____________________________________________\n"
                                + "OK, I've marked this task as not done yet:\n"
                                + "  " + task + "\n"
                                + "____________________________________________");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________\n"
                            + "Please provide a valid task number.\n"
                            + "____________________________________________");
                }
            } else if (userInput.startsWith("todo ")) {
                addTodo(tasks, userInput.substring("todo ".length()).trim());
            } else if (userInput.startsWith("deadline ")) {
                addDeadline(tasks, userInput.substring("deadline ".length()).trim());
            } else if (userInput.startsWith("event ")) {
                addEvent(tasks, userInput.substring("event ".length()).trim());
            } else {
                printError("I don't understand that command.");
            }
        }
    }

    /** Adds a to-do when the user supplied a non-empty description. */
    private static void addTodo(List<Task> tasks, String description) {
        if (description.isEmpty()) {
            printError("Please provide a description for the to-do.");
            return;
        }
        addTask(tasks, Task.todo(description));
    }

    /** Parses and adds a deadline in the form {@code description /by date}. */
    private static void addDeadline(List<Task> tasks, String command) {
        int byMarker = command.indexOf(" /by ");
        if (byMarker <= 0 || byMarker + " /by ".length() >= command.length()) {
            printError("Use: deadline DESCRIPTION /by DATE");
            return;
        }
        String description = command.substring(0, byMarker).trim();
        String by = command.substring(byMarker + " /by ".length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            printError("Use: deadline DESCRIPTION /by DATE");
            return;
        }
        addTask(tasks, Task.deadline(description, by));
    }

    /** Parses and adds an event in the form {@code description /from start /to end}. */
    private static void addEvent(List<Task> tasks, String command) {
        int fromMarker = command.indexOf(" /from ");
        int toMarker = command.indexOf(" /to ");
        if (fromMarker <= 0 || toMarker <= fromMarker + " /from ".length()
                || toMarker + " /to ".length() >= command.length()) {
            printError("Use: event DESCRIPTION /from START /to END");
            return;
        }
        String description = command.substring(0, fromMarker).trim();
        String from = command.substring(fromMarker + " /from ".length(), toMarker).trim();
        String to = command.substring(toMarker + " /to ".length()).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            printError("Use: event DESCRIPTION /from START /to END");
            return;
        }
        addTask(tasks, Task.event(description, from, to));
    }

    /** Prints the confirmation after adding a task. */
    private static void addTask(List<Task> tasks, Task task) {
        tasks.add(task);
        System.out.println("____________________________________________\n"
                + "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n"
                + "____________________________________________");
    }

    /** Prints a consistently formatted message for invalid commands. */
    private static void printError(String message) {
        System.out.println("____________________________________________\n"
                + message + "\n"
                + "____________________________________________");
    }
}
