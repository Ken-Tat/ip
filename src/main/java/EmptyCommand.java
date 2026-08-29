/** Command representing an empty user input. */
public class EmptyCommand extends Command {
    public EmptyCommand(Parser parser) { super(parser); }

    @Override
    public void execute(AppContext context) throws OreoException {
        throw new OreoException("Please enter a command.");
    }
}
