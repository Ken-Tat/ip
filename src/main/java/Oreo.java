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

        while (!userInput.equals("bye") && scanner.hasNextLine()) {
            userInput = scanner.nextLine().trim();

            try {
                CommandType commandType = CommandType.fromInput(userInput);
                if (commandType == CommandType.BYE) {
                    System.out.println(goodbye);
                } else if (commandType == CommandType.LIST) {
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
                } else if (commandType == CommandType.MARK) {
                    Task task = getTask(tasks, userInput.substring("mark".length()).trim());
                    task.markAsDone();
                    printSuccess("Nice! I've marked this task as done:", task);
                } else if (commandType == CommandType.UNMARK) {
                    Task task = getTask(tasks, userInput.substring("unmark".length()).trim());
                    task.markAsNotDone();
                    printSuccess("OK, I've marked this task as not done yet:", task);
                } else if (commandType == CommandType.DELETE) {
                    deleteTask(tasks, userInput.substring("delete".length()).trim());
                } else if (commandType == CommandType.EMPTY) {
                    throw new OreoException("Please enter a command.");
                } else if (commandType == CommandType.DEADLINE) {
                    addDeadline(tasks, userInput.length() == "deadline".length()
                            ? "" : userInput.substring("deadline ".length()).trim());
                } else if (commandType == CommandType.EVENT) {
                    addEvent(tasks, userInput.length() == "event".length()
                            ? "" : userInput.substring("event ".length()).trim());
                } else if (commandType == CommandType.TODO) {
                addTodo(tasks, userInput.length() == "todo".length()
                        ? "" : userInput.substring("todo ".length()).trim());
                } else {
                    throw new OreoException("I cannot comprehend your English.");
                }
            } catch (OreoException e) {
                printError(e.getMessage());
            }
        }
    }

    /** Finds a task or throws an input error without changing the task list. */
    private static Task getTask(List<Task> tasks, String taskNumberText) throws OreoException {
        if (taskNumberText.isEmpty()) {
            throw new OreoException("Sooo which task is it?");
        }
        try {
            int taskIndex = Integer.parseInt(taskNumberText) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new OreoException("I can't find that task number.");
            }
            return tasks.get(taskIndex);
        } catch (NumberFormatException e) {
            throw new OreoException("That is not a valid task number.");
        }
    }

    /** Prints a formatted confirmation after changing a task's completion state. */
    private static void printSuccess(String message, Task task) {
        System.out.println("____________________________________________\n"
                + message + "\n"
                + "  " + task + "\n"
                + "____________________________________________");
    }

    /** Removes the selected task and reports the remaining number of tasks. */
    private static void deleteTask(List<Task> tasks, String taskNumberText) throws OreoException {
        Task task = getTask(tasks, taskNumberText);
        tasks.remove(task);
        System.out.println("____________________________________________\n"
                + "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n"
                + "____________________________________________");
    }

    /** Adds a to-do when the user supplied a non-empty description. */
    private static void addTodo(List<Task> tasks, String description) throws OreoException {
        if (description.isEmpty()) {
            throw new OreoException("To do what task exactly?.");
        }
        addTask(tasks, new Todo(description));
    }

    /** Parses and adds a deadline in the form {@code description /by date}. */
    private static void addDeadline(List<Task> tasks, String command) throws OreoException {
        int byMarker = command.indexOf(" /by ");
        if (byMarker <= 0 || byMarker + " /by ".length() >= command.length()) {
            throw new OreoException("Use: deadline DESCRIPTION /by DATE");
        }
        String description = command.substring(0, byMarker).trim();
        String by = command.substring(byMarker + " /by ".length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new OreoException("Use: deadline DESCRIPTION /by DATE");
        }
        addTask(tasks, new Deadline(description, by));
    }

    /** Parses and adds an event in the form {@code description /from start /to end}. */
    private static void addEvent(List<Task> tasks, String command) throws OreoException {
        int fromMarker = command.indexOf(" /from ");
        int toMarker = command.indexOf(" /to ");
        if (fromMarker <= 0 || toMarker <= fromMarker + " /from ".length()
                || toMarker + " /to ".length() >= command.length()) {
            throw new OreoException("Use: event DESCRIPTION /from START /to END");
        }
        String description = command.substring(0, fromMarker).trim();
        String from = command.substring(fromMarker + " /from ".length(), toMarker).trim();
        String to = command.substring(toMarker + " /to ".length()).trim();
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new OreoException("Use: event DESCRIPTION /from START /to END");
        }
        addTask(tasks, new Event(description, from, to));
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
                + "  Oh My God! " + message + "\n"
                + "____________________________________________");
    }
}
