package oreo.command;

import oreo.core.*;
/** A user command that can be executed against the task application state. */
public abstract class Command {
    /** Shared parser used by commands that need argument validation. */
    protected final Parser parser;

    /** Creates a command using the supplied parser dependency. */
    protected Command(Parser parser) {
        this.parser = parser;
    }

    /** Executes this command. */
    public abstract void execute(AppContext context) throws OreoException;

    /** Returns whether this command ends the application. */
    public boolean isExit() {
        return false;
    }
}
