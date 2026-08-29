/** Command representing input that is not recognized. */
public class UnknownCommand extends Command {
    public UnknownCommand(Parser parser) { super(parser); }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws OreoException {
        throw new OreoException("I cannot comprehend your English.");
    }
}
