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
        boolean isExit = false;

        while (!isExit && scanner.hasNextLine()) {
            userInput = scanner.nextLine().trim();

            try {
                CommandType commandType = PARSER.parse(userInput);
                if (commandType == CommandType.BYE) {
                    Command command = new ExitCommand();
                    command.execute(tasks, UI, STORAGE);
                    isExit = command.isExit();
                } else if (commandType == CommandType.LIST) {
                    Command command = new ListCommand();
                    command.execute(tasks, UI, STORAGE);
                } else if (commandType == CommandType.MARK) {
                    Command command = new MarkCommand(PARSER.argument(userInput, "mark"));
                    command.execute(tasks, UI, STORAGE);
                } else if (commandType == CommandType.UNMARK) {
                    Command command = new UnmarkCommand(PARSER.argument(userInput, "unmark"));
                    command.execute(tasks, UI, STORAGE);
                } else if (commandType == CommandType.DELETE) {
                    Command command = new DeleteCommand(PARSER.argument(userInput, "delete"));
                    command.execute(tasks, UI, STORAGE);
                } else if (commandType == CommandType.EMPTY) {
                    throw new OreoException("Please enter a command.");
                } else if (commandType == CommandType.DEADLINE) {
                    Command command = new DeadlineCommand(PARSER.argument(userInput, "deadline"));
                    command.execute(tasks, UI, STORAGE);
                } else if (commandType == CommandType.EVENT) {
                    Command command = new EventCommand(PARSER.argument(userInput, "event"));
                    command.execute(tasks, UI, STORAGE);
                } else if (commandType == CommandType.TODO) {
                    Command command = new TodoCommand(PARSER.argument(userInput, "todo"));
                    command.execute(tasks, UI, STORAGE);
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
