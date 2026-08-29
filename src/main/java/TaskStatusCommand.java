/** Shared workflow for commands that change one task's completion status. */
public abstract class TaskStatusCommand extends Command {
    private final String taskNumber;

    /** Creates a status command for the supplied one-based task number. */
    protected TaskStatusCommand(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    /** Applies the requested status to the task. */
    protected abstract void update(Task task);

    /** Returns the confirmation text for the status change. */
    protected abstract String message();

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OreoException {
        Parser parser = new Parser();
        Task task = tasks.get(parser.taskIndex(taskNumber, tasks.size()));
        update(task);
        storage.save(tasks);
        ui.showSuccess(message(), task);
    }
}
