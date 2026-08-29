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
        String userInput = "";
        boolean isExit = false;

        while (!isExit && UI.hasNextCommand()) {
            userInput = UI.readCommand();

            try {
                Command command = PARSER.parseCommand(userInput);
                command.execute(tasks, UI, STORAGE);
                isExit = command.isExit();
            } catch (OreoException e) {
                UI.showError(e.getMessage());
            }
        }
    }

}
