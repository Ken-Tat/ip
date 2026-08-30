package oreo.command;

import oreo.core.AppContext;
import oreo.core.OreoException;
import oreo.core.Parser;
import oreo.model.Task;
/** Shared execution workflow for commands that add a task. */
public abstract class AddTaskCommand extends Command {
    protected AddTaskCommand(Parser parser) { super(parser); }

    /** Creates, stores, and displays the task produced by this command. */
    @Override
    public final void execute(AppContext context) throws OreoException {
        Task task = createTask();
        context.getTasks().add(task);
        context.getStorage().save(context.getTasks());
        context.getUi().showAdded(task, context.getTasks().size());
    }

    /** Parses this command's arguments and creates its task. */
    protected abstract Task createTask() throws OreoException;
}
