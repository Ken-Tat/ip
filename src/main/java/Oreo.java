import java.util.Scanner;
import java.nio.file.Path;
import java.time.LocalDate;

/**
 * A simple command-line chatbot that stores and displays user-entered tasks.
 */
public class Oreo {
    private static final Path TASK_FILE = Path.of("data", "oreo.txt");
    private static final Storage STORAGE = new Storage(TASK_FILE);
    private static final Parser PARSER = new Parser();
    private static final Ui UI = new Ui();

    public static void main(String[] args) {
        TaskList tasks = new TaskList(STORAGE.load());
        UI.showGreeting();

        // Reads commands from standard input.
        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("bye") && scanner.hasNextLine()) {
            userInput = scanner.nextLine().trim();

            try {
                CommandType commandType = PARSER.parse(userInput);
                if (commandType == CommandType.BYE) {
                    UI.showGoodbye();
                } else if (commandType == CommandType.LIST) {
                UI.showTaskList(tasks);
                } else if (commandType == CommandType.MARK) {
                    Task task = getTask(tasks, userInput.substring("mark".length()).trim());
                    task.markAsDone();
                    STORAGE.save(tasks);
                    UI.showSuccess("Nice! I've marked this task as done:", task);
                } else if (commandType == CommandType.UNMARK) {
                    Task task = getTask(tasks, userInput.substring("unmark".length()).trim());
                    task.markAsNotDone();
                    STORAGE.save(tasks);
                    UI.showSuccess("OK, I've marked this task as not done yet:", task);
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
                } else if (commandType == CommandType.ON_DATE) {
                    listTasksOnDate(tasks, userInput.length() == "on".length()
                            ? "" : userInput.substring("on ".length()).trim());
                } else {
                    throw new OreoException("I cannot comprehend your English.");
                }
            } catch (OreoException e) {
                UI.showError(e.getMessage());
            }
        }
    }

    /** Finds a task or throws an input error without changing the task list. */
    private static Task getTask(TaskList tasks, String taskNumberText) throws OreoException {
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

    /** Removes the selected task and reports the remaining number of tasks. */
    private static void deleteTask(TaskList tasks, String taskNumberText) throws OreoException {
        Task task = getTask(tasks, taskNumberText);
        tasks.remove(task);
        STORAGE.save(tasks);
        UI.showDeleted(task, tasks.size());
    }

    /** Adds a to-do when the user supplied a non-empty description. */
    private static void addTodo(TaskList tasks, String description) throws OreoException {
        if (description.isEmpty()) {
            throw new OreoException("To do what task exactly?.");
        }
        addTask(tasks, new Todo(description));
    }

    /** Parses and adds a deadline in the form {@code description /by date}. */
    private static void addDeadline(TaskList tasks, String command) throws OreoException {
        int byMarker = command.indexOf(" /by ");
        if (byMarker <= 0 || byMarker + " /by ".length() >= command.length()) {
            throw new OreoException("Use: deadline DESCRIPTION /by DATE");
        }
        String description = command.substring(0, byMarker).trim();
        String by = command.substring(byMarker + " /by ".length()).trim();
        if (description.isEmpty() || by.isEmpty()) {
            throw new OreoException("Use: deadline DESCRIPTION /by DATE");
        }
        try {
            addTask(tasks, new Deadline(description, by));
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
    }

    /** Parses and adds an event in the form {@code description /from start /to end}. */
    private static void addEvent(TaskList tasks, String command) throws OreoException {
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
        try {
            addTask(tasks, new Event(description, from, to));
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
    }

    /** Prints the confirmation after adding a task. */
    private static void addTask(TaskList tasks, Task task) {
        tasks.add(task);
        STORAGE.save(tasks);
        UI.showAdded(task, tasks.size());
    }

    /** Lists parsed deadlines and events occurring on an ISO date. */
    private static void listTasksOnDate(TaskList tasks, String dateText) throws OreoException {
        if (dateText.isEmpty()) {
            throw new OreoException("Use: on YYYY-MM-DD");
        }
        final LocalDate date;
        try {
            date = DateTimeParser.parseDate(dateText);
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
        UI.showTasksOnDate(tasks, date);
    }

}
