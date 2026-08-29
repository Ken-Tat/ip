package oreo.command;

import oreo.core.OreoException;
/** Command that adds an event task. */
public class EventCommand extends AddTaskCommand {
    private final String command;

    /** Creates an event command. */
    public EventCommand(String command, Parser parser) {
        super(parser);
        this.command = command;
    }

    @Override
    protected Task createTask() throws OreoException {
        String[] parts = parser.eventParts(command);
        try {
            return new Event(parts[0], parts[1], parts[2]);
        } catch (IllegalArgumentException e) {
            throw new OreoException(e.getMessage());
        }
    }
}

