package oreo.command;

import oreo.core.*;
import oreo.model.Task;
/** Shared execution workflow for commands that add a task. */
public abstract class AddTaskCommand extends Command {
    protected AddTaskCommand(Parser parser) { super(parser); }

    @Override
    public final void execute(AppContext context) throws OreoException {
        Task task = createTask();
        context.tasks.add(task);
        context.storage.save(context.tasks);
        context.ui.showAdded(task, context.tasks.size());
    }

    /** Parses this command's arguments and creates its task. */
    protected abstract Task createTask() throws OreoException;
}
