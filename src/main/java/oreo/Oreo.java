package oreo;

import oreo.command.Command;
import oreo.core.AppContext;
import oreo.core.CommandFactory;
import oreo.core.OreoException;
import oreo.core.Parser;
import oreo.model.TaskList;
import oreo.storage.Storage;
import oreo.ui.Ui;
import java.nio.file.Path;

/**
 * A simple command-line chatbot that stores and displays user-entered tasks.
 */
public class Oreo {
    private final Storage storage;
    private final Parser parser;
    private final Ui ui;
    private final TaskList tasks;
    private final AppContext context;

    /** Creates an Oreo application using the default task file. */
    public Oreo() {
        storage = new Storage(Path.of("data", "oreo.txt"));
        parser = new Parser(new CommandFactory());
        ui = new Ui();
        tasks = new TaskList(storage.load());
        context = new AppContext(tasks, ui, storage);
    }

    /** Runs the command loop until an exit command is received. */
    public void run() {
        ui.showGreeting();

        // Reads commands from standard input.
        String userInput = "";
        boolean isExit = false;

        while (!isExit && ui.hasNextCommand()) {
            userInput = ui.readCommand();

            try {
                Command command = parser.parse(userInput);
                command.execute(context);
                isExit = command.isExit();
            } catch (OreoException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /** Starts the command-line application. */
    public static void main(String[] args) {
        new Oreo().run();
    }

}
