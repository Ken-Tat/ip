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
                    Task task = getTask(tasks, PARSER.argument(userInput, "mark"));
                    task.markAsDone();
                    STORAGE.save(tasks);
                    UI.showSuccess("Nice! I've marked this task as done:", task);
                } else if (commandType == CommandType.UNMARK) {
                    Task task = getTask(tasks, PARSER.argument(userInput, "unmark"));
                    task.markAsNotDone();
                    STORAGE.save(tasks);
                    UI.showSuccess("OK, I've marked this task as not done yet:", task);
                } else if (commandType == CommandType.DELETE) {
                    deleteTask(tasks, PARSER.argument(userInput, "delete"));
                } else if (commandType == CommandType.EMPTY) {
                    throw new OreoException("Please enter a command.");
                } else if (commandType == CommandType.DEADLINE) {
                    addDeadline(tasks, PARSER.argument(userInput, "deadline"));
                } else if (commandType == CommandType.EVENT) {
                    addEvent(tasks, PARSER.argument(userInput, "event"));
                } else if (commandType == CommandType.TODO) {
                addTodo(tasks, PARSER.argument(userInput, "todo"));
                } else if (commandType == CommandType.ON_DATE) {
                    listTasksOnDate(tasks, PARSER.argument(userInput, "on"));
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
        return tasks.get(PARSER.taskIndex(taskNumberText, tasks.size()));
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
        addTask(tasks, new Todo(PARSER.todoDescription(description)));
    }

    /** Parses and adds a deadline in the form {@code description /by date}. */
    private static void addDeadline(TaskList tasks, String command) throws OreoException {
        String[] parts = PARSER.deadlineParts(command);
        try {
            addTask(tasks, new Deadline(parts[0], parts[1]));
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
    }

    /** Parses and adds an event in the form {@code description /from start /to end}. */
    private static void addEvent(TaskList tasks, String command) throws OreoException {
        String[] parts = PARSER.eventParts(command);
        try {
            addTask(tasks, new Event(parts[0], parts[1], parts[2]));
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
