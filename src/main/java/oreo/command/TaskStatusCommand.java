package oreo.command;

import oreo.core.AppContext;
/** Shared workflow for commands that change one task's completion status. */
public abstract class TaskStatusCommand extends Command {
    private final String taskNumber;

    /** Creates a status command for the supplied one-based task number. */
    protected TaskStatusCommand(String taskNumber, Parser parser) {
        super(parser);
        this.taskNumber = taskNumber;
    }

    /** Applies the requested status to the task. */
    protected abstract void update(Task task);

    /** Returns the confirmation text for the status change. */
    protected abstract String message();

    @Override
    public void execute(AppContext context) throws OreoException {
        Task task = context.tasks.get(parser.taskIndex(taskNumber, context.tasks.size()));
        update(task);
        context.storage.save(context.tasks);
        context.ui.showSuccess(message(), task);
    }
}

