package oreo.command;

import oreo.core.*;
import oreo.model.Deadline;
import oreo.model.Task;
/** Command that adds a deadline task. */
public class DeadlineCommand extends AddTaskCommand {
    private final String command;

    /** Creates a deadline command. */
    public DeadlineCommand(String command, Parser parser) {
        super(parser);
        this.command = command;
    }

    @Override
    protected Task createTask() throws OreoException {
        String[] parts = parser.deadlineParts(command);
        try {
            return new Deadline(parts[0], parts[1]);
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
    }
}
