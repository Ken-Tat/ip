/** A user command that can be executed against the task application state. */
public abstract class Command {
    /** Executes this command. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws OreoException;

    /** Returns whether this command ends the application. */
    public boolean isExit() {
        return false;
    }
}
