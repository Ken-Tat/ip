import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/** Handles messages that greet the user and close the application. */
public class Ui {
    private static final String NAME = "Oreo";
    private final Scanner scanner;

    /** Creates a UI that reads commands from standard input. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /** Returns whether another command is available. */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /** Reads and trims the next user command. */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

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

    /** Displays a confirmation for a task status change. */
    public void showSuccess(String message, Task task) {
        System.out.println("____________________________________________\n" + message + "\n"
                + "  " + task + "\n____________________________________________");
    }

    /** Displays a consistently formatted command error. */
    public void showError(String message) {
        System.out.println("____________________________________________\n"
                + "  Oh My God! " + message + "\n____________________________________________");
    }

    /** Displays all tasks, or an empty-list message. */
    public void showTaskList(TaskList tasks) {
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
    }

    /** Displays confirmation after adding a task. */
    public void showAdded(Task task, int taskCount) {
        System.out.println("____________________________________________\n"
                + "Got it. I've added this task:\n" + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.\n"
                + "____________________________________________");
    }

    /** Displays confirmation after deleting a task. */
    public void showDeleted(Task task, int taskCount) {
        System.out.println("____________________________________________\n"
                + "Noted. I've removed this task:\n  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.\n"
                + "____________________________________________");
    }

    /** Displays deadlines and events occurring on the supplied date. */
    public void showTasksOnDate(TaskList tasks, LocalDate date) {
        System.out.println("____________________________________________");
        System.out.println("Tasks occurring on "
                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        int count = 0;
        for (Task task : tasks) {
            boolean occurs = task instanceof Deadline deadline && deadline.occursOn(date)
                    || task instanceof Event event && event.occursOn(date);
            if (occurs) {
                System.out.println((++count) + "." + task);
            }
        }
        if (count == 0) System.out.println("No deadlines or events on this date.");
        System.out.println("____________________________________________");
    }
}
