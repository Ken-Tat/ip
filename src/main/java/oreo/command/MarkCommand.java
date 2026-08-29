package oreo.command;

import oreo.core.Parser;
/** Command that marks one task as done. */
public class MarkCommand extends TaskStatusCommand {
    /** Creates a mark command for the supplied one-based task number. */
    public MarkCommand(String taskNumber, Parser parser) {
        super(taskNumber, parser);
    }

    @Override
    protected void update(Task task) {
        task.markAsDone();
    }

    @Override
    protected String message() {
        return "Nice! I've marked this task as done:";
    }
}

