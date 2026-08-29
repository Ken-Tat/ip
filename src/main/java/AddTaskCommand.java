/** Shared execution workflow for commands that add a task. */
public abstract class AddTaskCommand extends Command {
    protected AddTaskCommand(Parser parser) { super(parser); }

    @Override
    public final void execute(TaskList tasks, Ui ui, Storage storage) throws OreoException {
        Task task = createTask();
        tasks.add(task);
        storage.save(tasks);
        ui.showAdded(task, tasks.size());
    }

    /** Parses this command's arguments and creates its task. */
    protected abstract Task createTask() throws OreoException;
}
