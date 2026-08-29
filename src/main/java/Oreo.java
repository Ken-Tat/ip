import java.util.Scanner;
import java.nio.file.Path;

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
                    Command command = new EmptyCommand();
                    command.execute(tasks, UI, STORAGE);
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
                    Command command = new OnDateCommand(PARSER.argument(userInput, "on"));
                    command.execute(tasks, UI, STORAGE);
                } else {
                    Command command = new UnknownCommand();
                    command.execute(tasks, UI, STORAGE);
                }
            } catch (OreoException e) {
                UI.showError(e.getMessage());
            }
        }
    }

}
