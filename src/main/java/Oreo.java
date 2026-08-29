import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
        System.out.println("____________________________________________\n"
                + "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n"
                + "____________________________________________");
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
        System.out.println("____________________________________________\n"
                + "Got it. I've added this task:\n"
                + task + "\n"
                + "Now you have " + tasks.size() + " tasks in the list.\n"
                + "____________________________________________");
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
        System.out.println("____________________________________________");
        System.out.println("Tasks occurring on " + date.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
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

    /** Saves the current task list without leaving a partially written file behind. */
    private static void saveTasks(TaskList tasks) {
        Path parent = TASK_FILE.getParent();
        Path temporaryFile = null;
        try {
            if (Files.exists(parent) && !Files.isDirectory(parent)) {
                throw new IOException("The data path is not a directory.");
            }
            Files.createDirectories(parent);
            temporaryFile = Files.createTempFile(parent, "oreo", ".tmp");
            List<String> lines = tasks.stream().map(Oreo::formatTask).toList();
            Files.write(temporaryFile, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.TRUNCATE_EXISTING);
            try {
                Files.move(temporaryFile, TASK_FILE, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException | UnsupportedOperationException e) {
                Files.move(temporaryFile, TASK_FILE, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | SecurityException e) {
            System.err.println("Warning: unable to save tasks: " + e.getMessage());
        } finally {
            if (temporaryFile != null) {
                try {
                    Files.deleteIfExists(temporaryFile);
                } catch (IOException ignored) {
                    // The temporary file is harmless and can be cleaned up later.
                }
            }
        }
    }

    /** Encodes task fields so separators and unusual user text cannot corrupt the file. */
    private static String formatTask(Task task) {
        String type = task.getTaskType().getMarker();
        String status = task.getStatusIcon().equals("X") ? "1" : "0";
        StringBuilder line = new StringBuilder(type).append('|').append(status).append('|')
                .append(encode(task.description));
        if (task instanceof Deadline deadline) {
            line.append('|').append(encode(deadline.getBy()));
        } else if (task instanceof Event event) {
            line.append('|').append(encode(event.getFrom())).append('|')
                    .append(encode(event.getTo()));
        }
        return line.toString();
    }

    private static String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    /** Loads previously saved tasks, ignoring malformed lines and filesystem failures. */
    private static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.isRegularFile(TASK_FILE)) {
            return tasks;
        }
        try {
            for (String line : Files.readAllLines(TASK_FILE)) {
                Task task = parseStoredTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException | SecurityException e) {
            System.err.println("Warning: unable to load tasks: " + e.getMessage());
        }
        return tasks;
    }

    private static Task parseStoredTask(String line) {
        String[] fields = line.split("\\|", -1);
        try {
            if (fields.length < 3 || !fields[1].matches("[01]")) {
                return parseLegacyTask(line);
            }
            String description = new String(Base64.getDecoder().decode(fields[2]), StandardCharsets.UTF_8);
            Task task;
            if (fields[0].equals("T") && fields.length == 3) {
                task = new Todo(description);
            } else if (fields[0].equals("D") && fields.length == 4) {
                task = new Deadline(description, decode(fields[3]));
            } else if (fields[0].equals("E") && fields.length == 5) {
                task = new Event(description, decode(fields[3]), decode(fields[4]));
            } else {
                return null;
            }
            if (fields[1].equals("1")) {
                task.markAsDone();
            }
            return task;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }

    /** Reconstructs one task from the display format written by {@link #saveTasks(List)}. */
    private static Task parseLegacyTask(String line) {
        if (line.length() < 7 || line.charAt(0) != '[' || line.charAt(2) != ']'
                || line.charAt(3) != '[') {
            return null;
        }
        boolean done = line.startsWith("[T][X]") || line.startsWith("[D][X]")
                || line.startsWith("[E][X]");
        String content = line.substring(6).trim();
        try {
            Task task;
            if (line.startsWith("[T]")) {
                task = new Todo(content);
            } else if (line.startsWith("[D]")) {
                int marker = content.lastIndexOf(" (by: ");
                if (marker < 1 || !content.endsWith(")")) {
                    return null;
                }
                task = new Deadline(content.substring(0, marker),
                        content.substring(marker + 6, content.length() - 1));
            } else if (line.startsWith("[E]")) {
                int marker = content.lastIndexOf(" (from: ");
                int separator = content.lastIndexOf(" to: ");
                if (marker < 1 || separator < marker || !content.endsWith(")")) {
                    return null;
                }
                task = new Event(content.substring(0, marker),
                        content.substring(marker + 8, separator),
                        content.substring(separator + 5, content.length() - 1));
            } else {
                return null;
            }
            if (done) {
                task.markAsDone();
            }
            return task;
        } catch (RuntimeException e) {
            return null;
        }
    }
}
