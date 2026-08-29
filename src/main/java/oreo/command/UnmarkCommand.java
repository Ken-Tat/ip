package oreo.command;

import oreo.core.Parser;
import oreo.model.Task;
/** Command that marks one task as not done. */
public class UnmarkCommand extends TaskStatusCommand {
    /** Creates an unmark command for the supplied one-based task number. */
    public UnmarkCommand(String taskNumber, Parser parser) {
        super(taskNumber, parser);
    }

    @Override
    protected void update(Task task) {
        task.markAsNotDone();
    }

    @Override
    protected String message() {
        return "OK, I've marked this task as not done yet:";
    }
}
