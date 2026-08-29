package oreo.command;

import oreo.core.AppContext;
/** Command that removes one task from the list and persists the result. */
public class DeleteCommand extends Command {
    private final String taskNumber;

    /** Creates a delete command for the supplied one-based task number. */
    public DeleteCommand(String taskNumber, Parser parser) {
        super(parser);
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(AppContext context) throws OreoException {
        Task task = context.tasks.get(parser.taskIndex(taskNumber, context.tasks.size()));
        context.tasks.remove(task);
        context.storage.save(context.tasks);
        context.ui.showDeleted(task, context.tasks.size());
    }
}

