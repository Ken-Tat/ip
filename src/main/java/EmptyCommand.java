/** Command representing an empty user input. */
public class EmptyCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OreoException {
        throw new OreoException("Please enter a command.");
    }
}
