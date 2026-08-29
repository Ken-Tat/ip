/** Command that removes one task from the list and persists the result. */
public class DeleteCommand extends Command {
    private final String taskNumber;

    /** Creates a delete command for the supplied one-based task number. */
    public DeleteCommand(String taskNumber, Parser parser) {
        super(parser);
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OreoException {
        Task task = tasks.get(parser.taskIndex(taskNumber, tasks.size()));
        tasks.remove(task);
        storage.save(tasks);
        ui.showDeleted(task, tasks.size());
    }
}
