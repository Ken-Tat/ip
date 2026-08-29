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
}
