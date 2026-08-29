/** The application collaborators available while executing a command. */
public class AppContext {
    /** The current task collection. */
    public final TaskList tasks;
    /** The user interaction handler. */
    public final Ui ui;
    /** The task persistence handler. */
    public final Storage storage;

    /** Creates an execution context from the application collaborators. */
    public AppContext(TaskList tasks, Ui ui, Storage storage) {
        this.tasks = tasks;
        this.ui = ui;
        this.storage = storage;
    }
}
